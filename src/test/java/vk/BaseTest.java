package vk;

import com.vk.api.sdk.client.actors.UserActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected UserActor actor;

    protected static TestDataItem extractData(Object[] paramsObject) {
        if (paramsObject != null && paramsObject.length > 0) {
            Object data = paramsObject[0];
            if (!(data instanceof TestDataItem)) {
                throw new RuntimeException("Invalid test parameters format: " + data.getClass());
            }
            return (TestDataItem) data;
        } else {
            throw new RuntimeException("Test parameters object is empty");
        }
    }
}
