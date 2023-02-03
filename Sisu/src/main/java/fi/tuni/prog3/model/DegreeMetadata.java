package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

public class DegreeMetadata {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
