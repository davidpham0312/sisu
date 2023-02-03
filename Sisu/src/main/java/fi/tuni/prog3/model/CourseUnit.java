package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.interfaces.CreditsEntity;
import fi.tuni.prog3.interfaces.SisuEntity;

public class CourseUnit implements SisuEntity, CreditsEntity {
    @SerializedName("code")
    private String code;
    @SerializedName("id")
    private String id;
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("credits")
    private Credits credits;
    @SerializedName("name")
    private LanguageStringTuple name;
    @SerializedName("outcomes")
    private LanguageStringTuple description;
    @SerializedName("isCompleted")
    private Boolean isCompleted = false;
    @SerializedName("isRegistered")
    private Boolean isRegistered = false;

    public CourseUnit(String code, LanguageStringTuple name, String id, String groupId, Credits credits,
            LanguageStringTuple description) {
        this.code = code;
        this.name = name;
        this.id = id;
        this.groupId = groupId;
        this.credits = credits;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        if (description == null) {
            return "Outcomes of this course were not provided.";
        }
        return description.getString();
    }

    public void setDescription(LanguageStringTuple description) {
        this.description = description;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public String getName() {
        return name.getString();
    }

    public void setName(LanguageStringTuple name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}
