package fi.tuni.prog3.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class JSONUtils {
    private JSONUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isFileExisting(String filePath) {
        File f = new File(filePath);
        if (f.isFile()) {
            return true;
        }
        return false;
    }

    public static void saveToJSONFile(Object target, String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(target, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String readJSONFromFile(String filePath) throws IOException {
        System.out.println("Fetching JSON from file: " + filePath);
        // Create Buffer reader for the File that is downloaded
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            final String NL = System.lineSeparator();

            // create StringBuilder object
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            // Append items from the file to string builder
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            // delete the last new line separator
            stringBuilder.delete(stringBuilder.length() - NL.length(), stringBuilder.length());
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. This is expected as we only have a few JSON files locally");
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error reading course unit file");
        }
    }

    public static String fetchJSONfromURL(String urlString) throws IOException {
        System.out.println("Fetching JSON from URL: " + urlString);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlString)).build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Server error: fetching JSON from URL");
            }
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(String.format("Error fetching JSON from URL %s",
                    urlString));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException(String.format("(Interrupted) Error fetching JSON from URL %s", urlString));
        }
    }
}
