package vk;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    @SuppressWarnings("unchecked")
    protected static Map<String, String> extractParameters(Object[] paramsObject) {
        if (paramsObject != null && paramsObject.length > 0) {
            Object params = paramsObject[0];
            if (!(params instanceof HashMap)) {
                throw new RuntimeException("Invalid test parameters format: " + params.getClass());
            }
            return (Map<String, String>) params;
        } else {
            throw new RuntimeException("Test parameters object is empty");
        }
    }
}
