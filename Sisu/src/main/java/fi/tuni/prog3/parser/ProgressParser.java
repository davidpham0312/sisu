package fi.tuni.prog3.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import fi.tuni.prog3.model.Module;
import fi.tuni.prog3.model.DegreeProgramme;
import fi.tuni.prog3.utils.JSONUtils;

public class ProgressParser {
    private ProgressParser() {
        throw new IllegalStateException("Static class");
    }

    public static ArrayList<DegreeProgramme> fromJSON(String studentProgressJSONPath)
            throws IOException {
        ArrayList<DegreeProgramme> jsonDegreeArray = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Module.class, new JsonDeserializerWithInheritance<Module>());
        Gson gson = builder.create();
        try {
            String allDegreesJSON = JSONUtils.readJSONFromFile(studentProgressJSONPath);
            Module[] jsonArray = gson.fromJson(allDegreesJSON, Module[].class);
            for (Module module : jsonArray) {
                jsonDegreeArray.add((DegreeProgramme) module);
            }
        } catch (IOException e) {
            throw e;
        }
        return jsonDegreeArray;
    }

    private static class JsonDeserializerWithInheritance<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(
                JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonPrimitive classNamePrimitive = (JsonPrimitive) jsonObject.get("type");

            String className = "fi.tuni.prog3.model." + classNamePrimitive.getAsString();
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e.getMessage());
            }
            return context.deserialize(jsonObject, clazz);
        }
    }
}
