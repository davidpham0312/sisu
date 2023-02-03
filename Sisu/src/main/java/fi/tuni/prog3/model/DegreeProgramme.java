package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.interfaces.CreditsEntity;

public class DegreeProgramme extends Module implements CreditsEntity {
    @SerializedName("code")
    private String code;
    @SerializedName("targetCredits")
    private Credits credits;
    public DegreeProgramme(String id, String groupId, LanguageStringTuple name, String code, Credits credits) {
        super(id, groupId, name);
        this.code = code;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Credits getCredits() {
        return this.credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

}
