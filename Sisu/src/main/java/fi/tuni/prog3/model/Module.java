package fi.tuni.prog3.model;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.exception.InvalidDataException;
import fi.tuni.prog3.interfaces.SisuEntity;

public abstract class Module implements SisuEntity {
    @SerializedName("type")
    private String typeName;
    @SerializedName("id")
    private String id;
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("name")
    private LanguageStringTuple name;
    @SerializedName("rule")
    protected Rule rule;
    @SerializedName("completedCredits")
    private int completedCredits;
    @SerializedName("registeredCredits")
    private int registeredCredits;
    @SerializedName("allModules")
    private ArrayList<Module> subModules;
    @SerializedName("allCourses")
    private ArrayList<CourseUnit> subCourses;

    protected Module(String id, String groupId, LanguageStringTuple name) {
        this.typeName = getClass().getName();
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.completedCredits = 0;
        this.registeredCredits = 0;
    }

    public int getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(int completedCredits) {
        this.completedCredits = completedCredits;
    }

    public int getRegisteredCredits() {
        return registeredCredits;
    }

    public void setRegisteredCredits(int registeredCredits) {
        this.registeredCredits = registeredCredits;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name.getString();
    }

    public void setName(LanguageStringTuple name) {
        this.name = name;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Module> getModules(boolean isOffline) throws IOException, InvalidDataException {
        if (subModules == null) {
            try {
                subModules = rule.getModules(isOffline);
            } catch (IOException | InvalidDataException e) {
                throw e;
            }
        }
        return subModules;
    }

    public Module getModuleByGroupId(String groupId, boolean isOffline)
            throws IOException, InvalidDataException, IllegalArgumentException {
        try {
            ArrayList<Module> modules = getModules(isOffline);
            for (Module module : modules) {
                if (module.getGroupId().equals(groupId)) {
                    return module;
                }
            }
            throw new IllegalArgumentException("No module with groupId " + groupId + " found.");
        } catch (IOException | InvalidDataException e) {
            throw e;
        }
    }

    public ArrayList<CourseUnit> getCourses(boolean isOffline) throws IOException, InvalidDataException {
        if (subCourses == null) {
            try {
                subCourses = rule.getCourses(isOffline);
            } catch (IOException | InvalidDataException e) {
                throw e;
            }
        }
        return subCourses;
    }

    public CourseUnit getCourseByGroupId(String groupId, boolean isOffline) throws IOException, InvalidDataException {
        ArrayList<CourseUnit> courses;
        try {
            courses = getCourses(isOffline);
        } catch (IOException | InvalidDataException e) {
            throw e;
        }
        for (CourseUnit course : courses) {
            if (course.getGroupId().equals(groupId)) {
                return course;
            }
        }
        throw new IllegalArgumentException("No course with groupId " + groupId + " found.");
    }
}
