package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.interfaces.CreditsEntity;

public class StudyModule extends Module implements CreditsEntity {
    @SerializedName("targetCredits")
    private Credits credits;

    public StudyModule(String id, String groupId, LanguageStringTuple name, Credits credits) {
        super(id, groupId, name);
        this.credits = credits;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }
}
