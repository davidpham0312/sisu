package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

public class Credits {
    @SerializedName("min")
    private int minCredits;
    @SerializedName("max")
    private int maxCredits;

    public Credits(int minCredits, int maxCredits) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
    }

    public Credits(int minCredits) {
        this.minCredits = minCredits;
    }

    public int getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(int minCredits) {
        this.minCredits = minCredits;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        this.maxCredits = maxCredits;
    }
}
