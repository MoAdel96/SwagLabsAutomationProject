package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
    public final static String TEST_DATA_PATH = "src/test/resources/TestData/";
    public final static String DATA_PATH = "src/test/resources/TestData/";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper Mapper = new ObjectMapper();

    //TODO: Read data from json file
    public static String getJsonData(String jsonFilename, String field) {
        try {
            // Define object of file Reader
            FileReader reader = new FileReader(TEST_DATA_PATH + jsonFilename + ".json");
            // Parse the JSON directly into a JsonElement
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    //TODO: get properties from any .properties file
    public static String getPropertyValue(String fileName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH + fileName + ".properties"));
        return properties.getProperty(key);
    }

//    //TODO: get Dynamic data from same parameter from Json file
//    public static JsonNode getTestData(String testCaseName) throws IOException {
//        File jsonFile = new File("src/test/resources/TestData/dynamicLogin.json");
//        JsonNode rootNode = Mapper.readTree(jsonFile);
//        JsonNode testCaseNode = rootNode.path(testCaseName);
//
//        if (testCaseNode.isMissingNode()) {
//            throw new IOException("Test case data not found for: " + testCaseName);
//        }
//        return testCaseNode;
//    }

    public static String getData(String fileName, String field) {
        try {
            File jsonFile = new File(DATA_PATH + fileName + ".json");
            return JsonPath.parse(jsonFile).read("$." + field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
