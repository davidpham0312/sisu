package fi.tuni.prog3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import fi.tuni.prog3.App;
import fi.tuni.prog3.exception.InvalidDataException;
import fi.tuni.prog3.model.CourseUnit;
import fi.tuni.prog3.model.DegreeMetadata;
import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.model.GroupingModule;
import fi.tuni.prog3.model.Module;
import fi.tuni.prog3.model.StudyModule;
import fi.tuni.prog3.parser.ModuleParser;
import fi.tuni.prog3.components.StackProgressBar;
import fi.tuni.prog3.parser.ProgressParser;
import fi.tuni.prog3.utils.AlertTitle;
import fi.tuni.prog3.utils.JSONUtils;
import fi.tuni.prog3.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

public class DegreeWindowController {
    @FXML
    private Label creditsInfoLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private TextFlow legendTextFlow;
    @FXML
    private Label studentInfoLabel;
    @FXML
    private VBox degreeWindow;
    @FXML
    private ComboBox<String> degreeDropdown;
    @FXML
    private Accordion structureAccordion;
    @FXML
    private BorderPane borderPaneProgressBar;

    private String studentProgressJSONPath;

    private ArrayList<DegreeMetadata> allDegreesMetadata;

    private ArrayList<DegreeProgramme> loadedDegreeProgrammes = new ArrayList<>();
    private Integer selectedDegreeProgrammeIndex;
    private DegreeProgramme selectedDegreeProgramme;
    private boolean isSelectedDegreeProgrammeAvailableOffline = false;
    private HashMap<String, TitledPane> allModuleTitledPanes = new HashMap<>();
    private HashMap<String, Module> parentModuleMap = new HashMap<>();
    private StackProgressBar progressBar;

    @FXML
    private void logout() throws IOException {
        App.setRoot("primary");
        App.activeStudent = null;
    }

    @FXML
    private void saveButton() {
        try {
            JSONUtils.saveToJSONFile(loadedDegreeProgrammes, studentProgressJSONPath);
            UIUtils.showAlert(AlertType.INFORMATION, App.scene.getWindow(), AlertTitle.SAVE_SUCCESS,
                    "Your study progress has been updated.");
        } catch (IOException e) {
            UIUtils.showAlert(AlertType.ERROR, App.scene.getWindow(), AlertTitle.SAVE_ERROR, e.getMessage());
        }
    }

    public void initialize() {
        buildProgressIndicators();
        setupDegreeProgramDropdown();
        loadStudentInfo();
    }

    private void buildProgressIndicators() {
        progressLabel.setText("Degree progress\nCredits: Registered / Completed / Total");
        buildProgressBar();
        buildProgressBarLegend();
    }

    private void loadStudentInfo() {
        studentProgressJSONPath = "./saved_information/saved_progress/" +
                App.activeStudent.getStudentNumber() + "_alldegrees.json";
        String studentInfoText = "Student: " + App.activeStudent.getFirstName() + " " + App.activeStudent.getLastName()
                +
                " (Student number " + App.activeStudent.getStudentNumber() + "). " + "Academic years: "
                + App.activeStudent.getStartYear() + " - " + App.activeStudent.getEndYear() + ".";

        studentInfoLabel.setText(studentInfoText);
        if (JSONUtils.isFileExisting(studentProgressJSONPath)) {
            try {
                loadedDegreeProgrammes = ProgressParser.fromJSON(studentProgressJSONPath);
            } catch (Exception e) {
                UIUtils.showAlert(AlertType.ERROR, App.scene.getWindow(), AlertTitle.PARSE_ERROR,
                        e.getMessage());
            }
        }
    }

    private void setupDegreeProgramDropdown() {
        try {
            allDegreesMetadata = ModuleParser.getAllDegreeMetadata();
        } catch (IOException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.LOADING_ERROR, e.getMessage());
        } catch (InvalidDataException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                    AlertTitle.PARSE_ERROR, e.getMessage());
        }

        degreeDropdown.getItems().addAll(
                allDegreesMetadata.stream().map(DegreeMetadata::getName).collect(Collectors.toList()));
        degreeDropdown.setOnAction(event -> {
            try {
                selectedDegreeProgrammeIndex = degreeDropdown.getSelectionModel().getSelectedIndex();
                fetchSelectedDegreeProgramme();
                loadSelectedDegreeProgramme();
            } catch (IOException e) {
                UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                        AlertTitle.LOADING_ERROR, "Could not load the selected degree programme");
            } catch (InvalidDataException e) {
                UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(),
                        AlertTitle.PARSE_ERROR, "Selected degree programme's data contains invalid values");
            }
        });
    }

    private void fetchSelectedDegreeProgramme() throws IOException, InvalidDataException {
        selectedDegreeProgramme = loadedDegreeProgrammes.stream()
                .filter(degreeProgramme -> degreeProgramme.getId()
                        .equals(allDegreesMetadata.get(selectedDegreeProgrammeIndex).getId()))
                .findFirst().orElse(null);
        if (selectedDegreeProgramme == null) {
            selectedDegreeProgramme = (DegreeProgramme) ModuleParser
                    .fromURL("https://sis-tuni.funidata.fi/kori/api/modules/"
                            + allDegreesMetadata.get(selectedDegreeProgrammeIndex).getId());
            loadedDegreeProgrammes.add(selectedDegreeProgramme);
        }
    }

    private void loadSelectedDegreeProgramme() {
        clearStructureAccordion();
        ArrayList<TitledPane> allTitledPanes = newSubmoduleTitledPanes(selectedDegreeProgramme,
                isSelectedDegreeProgrammeAvailableOffline);
        for (TitledPane titledPane : allTitledPanes) {
            structureAccordion.getPanes().add(titledPane);
        }
        if (selectedDegreeProgramme.getCredits().getMinCredits() > 0) {
            double degreeCompletedCreditsPercent = (double) selectedDegreeProgramme.getCompletedCredits()
                    / selectedDegreeProgramme.getCredits().getMinCredits();

            double degreeRegisteredCreditsPercent = (double) selectedDegreeProgramme.getRegisteredCredits() /
                    selectedDegreeProgramme.getCredits().getMinCredits();
            progressBar.setProgress(degreeCompletedCreditsPercent, degreeRegisteredCreditsPercent);
        }

        updateCreditsInfoLabel();
    }

    private void clearStructureAccordion() {
        structureAccordion.getPanes().clear();
    }

    private ArrayList<TitledPane> newSubmoduleTitledPanes(Module module, Boolean isOffline) {
        ArrayList<TitledPane> submoduleTitledPanes = new ArrayList<>();
        ArrayList<Module> subModules = getSubModules(module, isOffline);
        if (!subModules.isEmpty()) {
            for (Module subModule : subModules) {
                VBox subModuleContainer = new VBox();
                TitledPane modulePane = buildSubModuleTitledPane(subModule, subModuleContainer);
                modulePane.setExpanded(false);
                registerTitledPaneListener(isOffline, subModule, subModuleContainer, modulePane);
                allModuleTitledPanes.put(subModule.getId(), modulePane);
                parentModuleMap.put(subModule.getId(), module);
                submoduleTitledPanes.add(modulePane);
            }
        }
        return submoduleTitledPanes;
    }

    private ArrayList<Module> getSubModules(Module module, Boolean isOffline) {
        ArrayList<Module> subModules = new ArrayList<>();
        try {
            subModules = module.getModules(isOffline);
        } catch (IOException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(), AlertTitle.LOADING_ERROR,
                    "Submodules data failed to be read");
        } catch (InvalidDataException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(), AlertTitle.PARSE_ERROR,
                    "Submodules data contains invalid values");
        }
        return subModules;
    }

    private ArrayList<CourseUnit> getSubCourses(Boolean isOffline, Module subModule) {
        ArrayList<CourseUnit> subCourses = new ArrayList<>();
        try {
            subCourses = subModule.getCourses(isOffline);
        } catch (IOException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(), AlertTitle.LOADING_ERROR,
                    "Subcourses data failed to be read");
        } catch (InvalidDataException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, App.scene.getWindow(), AlertTitle.PARSE_ERROR,
                    "Subcourses data contains invalid values");
        }
        return subCourses;
    }

    private void registerTitledPaneListener(Boolean isOffline, Module subModule, VBox subModuleContainer,
            TitledPane modulePane) {
        modulePane.expandedProperty().addListener((observableValue, wasExpanded, isNowExpanded) -> {
            ArrayList<Module> subSubmodules = getSubModules(subModule, isOffline);
            ArrayList<CourseUnit> subSubcourses = getSubCourses(isOffline, subModule);
            if (isNowExpanded) {
                subModuleContainer.getChildren().clear();
                if (!subSubmodules.isEmpty()) {
                    ArrayList<TitledPane> subSubmoduleTitledPanes = newSubmoduleTitledPanes(subModule,
                            isOffline);
                    subModuleContainer.getChildren().addAll(subSubmoduleTitledPanes);
                } else if (!subSubcourses.isEmpty()) {
                    ListView<CheckBox> subCoursesChecklist = newCourseListView(subModule, subSubcourses);
                    subModuleContainer.getChildren().add(subCoursesChecklist);
                }
            }
        });
    }

    private TitledPane buildSubModuleTitledPane(Module subModule, Region container) {
        return subModule instanceof GroupingModule
                ? new TitledPane(subModule.getName(), container)
                : new TitledPane(titleWithCredits(subModule), container);
    }

    private String titleWithCredits(Module subModule) {
        // Type casting required to get the required credits from the submodule.
        try {
            StudyModule subStudyModule = (StudyModule) subModule;
            return subStudyModule.getName() + " " + subStudyModule.getCompletedCredits() + "op/"
                    + subStudyModule.getCredits().getMinCredits() + "op";
        } catch (ClassCastException e) {
            return subModule.getName();
        }
    }

    private ListView<CheckBox> newCourseListView(Module subModule, ArrayList<CourseUnit> subModuleCourses) {
        ListView<CheckBox> courseListView = new ListView<>();
        for (CourseUnit course : subModuleCourses) {
            String courseLabel = course.getName() + " " + course.getCredits().getMaxCredits() + "op";
            CheckBox courseCheckBox = new CheckBox(courseLabel);
            courseCheckBox.setText(courseLabel);

            courseCheckBox.setAllowIndeterminate(true);

            if (course.getIsCompleted()) {
                courseCheckBox.setSelected(course.getIsCompleted());
            } else {
                if (course.getIsRegistered()) {
                    courseCheckBox.setIndeterminate(course.getIsRegistered());
                }
            }
            registerCourseCheckboxListener(subModule, course, courseCheckBox);
            courseCheckBox.setTooltip(buildTooltip(course.getDescription()));
            courseListView.getItems().add(courseCheckBox);
        }
        return courseListView;
    }

    private void registerCourseCheckboxListener(Module subModule, CourseUnit course, CheckBox courseCheckBox) {
        courseCheckBox.setOnAction(event -> {
            course.setIsCompleted(courseCheckBox.isSelected());
            course.setIsRegistered(courseCheckBox.isIndeterminate());
            updateDegreeProgress(subModule, course, courseCheckBox);

            if (courseCheckBox.isIndeterminate()) {
                courseCheckBox
                        .setText(course.getName() + " " + course.getCredits().getMaxCredits() + "op (Registered)");
            } else {
                updateModuleTitles(subModule);

                if (courseCheckBox.isSelected()) {
                    courseCheckBox.setText(
                            course.getName() + " " + course.getCredits().getMaxCredits() + "op (Completed)");
                } else {
                    courseCheckBox.setText(course.getName() + " " + course.getCredits().getMaxCredits() + "op");
                }
            }
        });
    }

    private void updateModuleTitles(Module subModule) {
        if (!(subModule instanceof GroupingModule)) {
            allModuleTitledPanes.get(subModule.getId()).setText(titleWithCredits(subModule));
        }
        Module parent = parentModuleMap.get(subModule.getId());
        while (!(parent instanceof GroupingModule) && parent != null) {
            if (allModuleTitledPanes.get(parent.getId()) != null) {
                allModuleTitledPanes.get(parent.getId()).setText(titleWithCredits(parent));
                parent = parentModuleMap.get(parent.getId());
            } else {
                break;
            }
        }
    }

    private void updateDegreeProgress(Module subModule, CourseUnit course, CheckBox courseCheckBox) {
        double proportion = (double) course.getCredits().getMaxCredits()
                / selectedDegreeProgramme.getCredits().getMinCredits();

        // Checkbox cycle order: not selected -> registered -> selected.
        if (selectedDegreeProgramme.getCredits().getMinCredits() > 0) {
            if (courseCheckBox.isSelected()) {
                progressBar.setProgress(progressBar.getCompletedPercent() + proportion,
                        progressBar.getRegisteredPercent() - proportion);

                increaseCompletedCredits(selectedDegreeProgramme, course);
                increaseCompletedCredits(subModule, course);

                decreaseRegisteredCredits(selectedDegreeProgramme, course);
                decreaseRegisteredCredits(subModule, course);
            } else {
                if (courseCheckBox.isIndeterminate()) {
                    progressBar.setProgress(progressBar.getCompletedPercent(),
                            progressBar.getRegisteredPercent() + proportion);

                    increaseRegisteredCredits(selectedDegreeProgramme, course);
                    increaseRegisteredCredits(subModule, course);
                } else {
                    progressBar.setProgress(progressBar.getCompletedPercent() - proportion,
                            progressBar.getRegisteredPercent());

                    decreaseCompletedCredits(selectedDegreeProgramme, course);
                    decreaseCompletedCredits(subModule, course);
                }
            }
        }

        updateCreditsInfoLabel();
    }

    private void increaseCompletedCredits(Module module, CourseUnit course) {
        module.setCompletedCredits(module.getCompletedCredits() + course.getCredits().getMinCredits());
        Module parentModule = parentModuleMap.get(module.getId());
        while (parentModule != null) {
            parentModule.setCompletedCredits(
                    parentModule.getCompletedCredits() + course.getCredits().getMinCredits());
            parentModule = parentModuleMap.get(parentModule.getId());
        }
    }

    private void decreaseCompletedCredits(Module module, CourseUnit course) {
        module.setCompletedCredits(module.getCompletedCredits() - course.getCredits().getMinCredits());
        Module parentModule = parentModuleMap.get(module.getId());
        while (parentModule != null) {
            parentModule.setCompletedCredits(
                    parentModule.getCompletedCredits() - course.getCredits().getMinCredits());
            parentModule = parentModuleMap.get(parentModule.getId());
        }
    }

    private void increaseRegisteredCredits(Module module, CourseUnit course) {
        module.setRegisteredCredits(module.getRegisteredCredits() + course.getCredits().getMinCredits());
        Module parentModule = parentModuleMap.get(module.getId());
        while (parentModule != null) {
            parentModule.setRegisteredCredits(
                    parentModule.getRegisteredCredits() + course.getCredits().getMinCredits());
            parentModule = parentModuleMap.get(parentModule.getId());
        }
    }

    private void decreaseRegisteredCredits(Module module, CourseUnit course) {
        module.setRegisteredCredits(module.getRegisteredCredits() - course.getCredits().getMinCredits());
        Module parentModule = parentModuleMap.get(module.getId());
        while (parentModule != null) {
            parentModule.setRegisteredCredits(
                    parentModule.getRegisteredCredits() - course.getCredits().getMinCredits());
            parentModule = parentModuleMap.get(parentModule.getId());
        }
    }

    private void updateCreditsInfoLabel() {
        String creditsInfoText;

        int degreeMinimumCredits = selectedDegreeProgramme.getCredits().getMinCredits();
        if (degreeMinimumCredits > 0) {
            double degreeRegisteredCreditsPercent = (double) selectedDegreeProgramme.getRegisteredCredits() /
                    degreeMinimumCredits;
            double degreeCompletedCreditsPercent = (double) selectedDegreeProgramme.getCompletedCredits() /
                    degreeMinimumCredits;

            creditsInfoText = String.format("%s / %s / %s - %d%% / %d%% / 100%%",
                    selectedDegreeProgramme.getRegisteredCredits(), selectedDegreeProgramme.getCompletedCredits(),
                    degreeMinimumCredits, (int) (degreeRegisteredCreditsPercent * 100),
                    (int) (degreeCompletedCreditsPercent * 100));
        } else {
            creditsInfoText = String.format("Degree minimum credits: %d op", degreeMinimumCredits);
        }
        creditsInfoLabel.setText(creditsInfoText);
    }

    private Tooltip buildTooltip(String tooltipStringContent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-background-color: white;");
        tooltip.setWrapText(true);
        tooltip.setPrefWidth(400);
        // Estimate of HTML rendered description height.
        tooltip.setPrefHeight(0.6 * tooltipStringContent.length());

        WebView fontWebView = new WebView();
        fontWebView.getEngine().loadContent(tooltipStringContent);
        fontWebView.getEngine().setUserStyleSheetLocation("data:,body { font: 14px System Regular; }");
        tooltip.setGraphic(fontWebView);

        return tooltip;
    }

    private void buildProgressBar() {
        progressBar = new StackProgressBar();
        borderPaneProgressBar.getChildren().add(progressBar);

        progressBar.widthProperty().bind(
                borderPaneProgressBar.widthProperty());
        progressBar.heightProperty().bind(
                borderPaneProgressBar.heightProperty());
    }

    private void buildProgressBarLegend() {
        double pictureWidth = 10;
        double pictureHeight = 10;
        int pictureTextSpacing = 5;
        int legendSpacing = 20;

        ImageView completedPicture = new ImageView(generatePictureLegend(progressBar.getCompletedBarColor()));
        ImageView registeredPicture = new ImageView(generatePictureLegend(progressBar.getRegisteredBarColor()));

        completedPicture.setFitWidth(pictureWidth);
        completedPicture.setFitHeight(pictureHeight);
        registeredPicture.setFitWidth(pictureWidth);
        registeredPicture.setFitHeight(pictureHeight);

        legendTextFlow.getChildren().addAll(
                completedPicture,
                new Text(" ".repeat(pictureTextSpacing)),
                new Text("Completed"),
                new Text(" ".repeat(legendSpacing)),
                registeredPicture,
                new Text(" ".repeat(pictureTextSpacing)),
                new Text("Registered"));
    }

    private Image generatePictureLegend(Color color) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();

        pw.setColor(0, 0, color);
        return img;
    }
}