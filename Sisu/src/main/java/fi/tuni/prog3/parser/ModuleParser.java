package fi.tuni.prog3.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import fi.tuni.prog3.model.Module;
import fi.tuni.prog3.model.GroupingModule;
import fi.tuni.prog3.model.StudyModule;
import fi.tuni.prog3.exception.InvalidDataException;
import fi.tuni.prog3.model.DegreeMetadata;
import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.utils.JSONUtils;

public class ModuleParser {
    static final String ALL_DEGREES_URL = "https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";

    private ModuleParser() {
        throw new IllegalStateException("Static class");
    }

    public static ArrayList<DegreeMetadata> getAllDegreeMetadata() throws IOException, InvalidDataException {
        try {
            final String jsonString = JSONUtils.fetchJSONfromURL(ALL_DEGREES_URL);
            return parseAllDegreeProgrammesMetadata(jsonString);
        } catch (IOException | InvalidDataException e) {
            throw e;
        }
    }

    public static Module fromJSON(String moduleFile) throws IOException, InvalidDataException {
        try {
            final String jsonString = JSONUtils.readJSONFromFile(moduleFile);
            return parseModuleJSONString(jsonString);
        } catch (InvalidDataException | IOException e) {
            throw e;
        }
    }

    public static Module fromURL(String url) throws IOException, InvalidDataException {
        try {
            final String jsonString = JSONUtils.fetchJSONfromURL(url);
            return parseModuleJSONString(jsonString);
        } catch (InvalidDataException | IOException e) {
            throw e;
        }
    }

    private static Module parseModuleJSONString(String jsonString) throws InvalidDataException {
        Gson gson = new Gson();

        if (jsonString.charAt(0) == '[') {
            jsonString = jsonString.substring(1, jsonString.length() - 1);
        }

        String moduleType = gson.fromJson(jsonString,
                JsonModuleType.class).getModuleType();
        if (moduleType.equals("DegreeProgramme")) {
            // regex match values of key "moduleGroupId" inside jsonString
            // and return them as an array
            return gson.fromJson(jsonString, DegreeProgramme.class);
        } else if (moduleType.equals("StudyModule")) {
            return gson.fromJson(jsonString, StudyModule.class);
        } else if (moduleType.equals("GroupingModule")) {
            return gson.fromJson(jsonString, GroupingModule.class);
        } else {
            throw new InvalidDataException("Unknown Module type");
        }
    }

    private static ArrayList<DegreeMetadata> parseAllDegreeProgrammesMetadata(String jsonString)
            throws InvalidDataException {
        Gson gson = new Gson();

        try {
            AllDegreesJsonType allDegreesMetadata = gson.fromJson(jsonString,
                    AllDegreesJsonType.class);
            return allDegreesMetadata.getDegreesMetadata();
        } catch (Exception e) {
            throw new InvalidDataException("Invalid all degrees JSON data");
        }
    }

    private static class JsonModuleType {
        @SerializedName("type")
        private String moduleType;

        public String getModuleType() {
            return moduleType;
        }
    }

    private static class AllDegreesJsonType {
        @SerializedName("searchResults")
        private DegreeMetadata[] degreesMetadata;

        public ArrayList<DegreeMetadata> getDegreesMetadata() {
            return new ArrayList<>(java.util.Arrays.asList(degreesMetadata));
        }
    }
}
