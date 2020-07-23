package vk.likes;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vk.ActorFactory;
import vk.BaseTest;
import vk.TestDataItem;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class AddLikesTests extends BaseTest {
    private static final LikesHelper CLIENT = new LikesHelper(
            new VkApiClient(HttpTransportClient.getInstance())
    );


    @BeforeMethod(alwaysRun = true)
    public void setUp(Object[] paramsObject, Method method) throws IOException {
        var testData = extractData(paramsObject);
        var params = testData.getParams();
        Test testClass = method.getAnnotation(Test.class);

        LOG.info(String.format("Running test: %s", testData.getName()));
        actor = ActorFactory.getUserActor(testData.getActor());

        if (!Arrays.asList(testClass.groups()).contains("positive")){
            return;
        }

        try {
            if (CLIENT.isLiked(actor, params).isLiked()) {
                CLIENT.deleteLike(actor, params);
            }
        } catch (Exception e){
            LOG.warn("Error in test setup: " + e.getMessage());
        }
    }

    @Test(
            dataProvider = "addLikesValidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"positive"},
            suiteName = "likes"
    )
    public void addLikeToItem(TestDataItem testData) throws ClientException, ApiException, InterruptedException {
        AddResponse response = CLIENT.addLike(actor, testData.getParams());
        LOG.info("Got response: " + response);

        List<Integer> likeListAfter = CLIENT
                .getLikes(actor, testData.getParams())
                .getItems();

        Assert.assertTrue(
                response.toString().matches(testData.getExpected()),
                "Actual response doesn't match expected"
        );
        Assert.assertTrue(
                likeListAfter.contains(actor.getId()),
                "Actor ID not present in likes list");
    }

    @Test(
            testName = "Add likes with invalid parameters",
            dataProvider = "likesInvalidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"negative"},
            suiteName = "likes"
    )
    public void addLikeInvalidParams(TestDataItem testData) {
        try {
            CLIENT.addLike(actor, testData.getParams());
            throw new AssertionError("Expected exception was not thrown");
        } catch (ClientException | ApiException | InterruptedException expected) {
            var message = expected.getMessage();
            LOG.info("Got response: " + message);

            Assert.assertTrue(
                    message.matches(testData.getExpected()),
                    "Actual exception doesn't match expected"
            );
        }
    }
}
