package fi.tuni.prog3.model;

import com.google.gson.annotations.SerializedName;

public class LanguageStringTuple {
    @SerializedName("fi")
    public String fi;
    @SerializedName("en")
    public String en;

    public LanguageStringTuple(String fi, String en) {
        this.fi = fi;
        this.en = en;
    }

    public String getString() {
        if (en != null) {
            return en;
        }
        return fi;
    }
}
