package vk;
import java.util.Map;

/**
 * Object-like representation of data for single test.
 */
public class TestDataItem {
    private final String name;
    private final String actor;
    private final Map<String, String> params;
    private final String expected;


    public TestDataItem(String name, String actor, Map<String, String> params, String expected){
        this.name = name;
        this.actor = actor;
        this.params = params;
        this.expected = expected;
    }

    public String getName() {
        return name;
    }

    public String getActor() {
        return actor;
    }

    public String getExpected() {
        return expected;
    }

    public Map<String, String> getParams() {
        return params;
    }
}