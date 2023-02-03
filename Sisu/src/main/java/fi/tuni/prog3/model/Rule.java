package fi.tuni.prog3.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.exception.InvalidDataException;
import fi.tuni.prog3.parser.CourseUnitParser;
import fi.tuni.prog3.parser.ModuleParser;

public class Rule {
    @SerializedName("rule")
    private Rule singleRule;
    @SerializedName("rules")
    private ArrayList<Rule> rules;
    @SerializedName("type")
    private String type;
    @SerializedName("courseUnitGroupId")
    private String courseUnitGroupId;
    @SerializedName("moduleGroupId")
    private String moduleGroupId;

    public Rule getSingleRule() {
        return singleRule;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ArrayList<CourseUnit> getCourses(boolean isOffline) throws IOException, InvalidDataException {
        ArrayList<CourseUnit> courses = new ArrayList<>();
        if (singleRule != null) {
            courses.addAll(singleRule.getCourses(isOffline));
        } else if (rules != null) {
            for (Rule moduleRule : rules) {
                courses.addAll(moduleRule.getCourses(isOffline));
            }
        } else {
            if (type.equals("AnyCourseUnitRule")) {
                return courses;
            }
            if (type.equals("CourseUnitRule")) {
                try {
                    addCoursesInside(courses, isOffline);
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return new ArrayList<>(courses.stream().distinct().collect(Collectors.toList()));
    }

    public ArrayList<Module> getModules(boolean isOffline) throws IOException, InvalidDataException {
        ArrayList<Module> modules = new ArrayList<>();
        if (singleRule != null) {
            modules.addAll(singleRule.getModules(isOffline));
        } else if (rules != null) {
            for (Rule moduleRule : rules) {
                modules.addAll(moduleRule.getModules(isOffline));
            }
        } else {
            if (type.equals("AnyModuleRule")) {
                return modules;
            }
            if (type.equals("ModuleRule")) {
                try {
                    addModulesInside(modules, isOffline);
                } catch (InvalidDataException | IOException e) {
                    throw e;
                }
            }
        }
        return modules;
    }

    private void addModulesInside(ArrayList<Module> modules, boolean isOffline)
            throws IOException, InvalidDataException {
        try {
            Module module = getModuleJSONData(isOffline);
            modules.add(module);
        } catch (InvalidDataException | IOException e) {
            throw e;
        }
    }

    private void addCoursesInside(ArrayList<CourseUnit> courses, boolean isOffline)
            throws IOException {
        try {
            CourseUnit courseUnit = getCourseJSONData(isOffline);
            courses.add(courseUnit);
        } catch (IOException e) {
            throw e;
        }
    }

    private Module getModuleJSONData(boolean isOffline) throws IOException, InvalidDataException {
        try {
            if (isOffline) {
                return ModuleParser.fromJSON("../json/modules/" + moduleGroupId + ".json");
            }
            return ModuleParser.fromURL(getModuleURL(moduleGroupId));
        } catch (InvalidDataException | IOException e) {
            throw e;
        }
    }

    private CourseUnit getCourseJSONData(boolean isOffline) throws IOException {
        try {
            if (isOffline) {
                return CourseUnitParser.fromJSON("../json/courseUnits/" + courseUnitGroupId + ".json");
            }
            return CourseUnitParser.fromURL(getCourseURL(courseUnitGroupId));
        } catch (IOException e) {
            throw e;
        }
    }

    private String getModuleURL(String groupID) {
        return "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=" + groupID
                + "&universityId=tuni-university-root-id";
    }

    private String getCourseURL(String groupID) {
        return "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=" + groupID
                + "&universityId=tuni-university-root-id";
    }
}
