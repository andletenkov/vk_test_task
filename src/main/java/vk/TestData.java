package vk;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test data files serializer for DDT approach.
 */
public class TestData {
    private static final String TEST_DATA_PATH = "/src/main/resources/test_data/";

    /**
     * Serializes JSON file to array of test data objects.
     * @param fileName name of JSON file with test data.
     * @return TestDataItem array.
     */
    public static TestDataItem[] fromJson(String fileName) {
        String fullPath = System.getProperty("user.dir") + TEST_DATA_PATH + fileName;

        try {
            JsonReader reader = new JsonReader(new FileReader(fullPath));
            return new Gson().fromJson(reader, TestDataItem[].class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to locate test data file: " + fileName);
        }
    }
}
