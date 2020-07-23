package vk;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class TestData {


    private static final String TEST_DATA_PATH = "src.main.resources.test_data";

    public static TestDataItem[] fromJson(String fileName) {
        String actualTestDataDir = TEST_DATA_PATH.replace(".", System.getProperty("file.separator"));

        Path path = FileSystems
                .getDefault()
                .getPath(System.getProperty("user.dir"), actualTestDataDir, fileName);

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(path.toString()));
            return gson.fromJson(reader, TestDataItem[].class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to locate test data file: " + fileName);
        }
    }
}
