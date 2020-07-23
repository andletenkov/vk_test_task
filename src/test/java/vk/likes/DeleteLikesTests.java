package vk.likes;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.likes.responses.DeleteResponse;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vk.ActorFactory;
import vk.BaseTest;
import vk.TestDataItem;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class DeleteLikesTests extends BaseTest {
    private static final LikesHelper CLIENT = new LikesHelper(
            new VkApiClient(HttpTransportClient.getInstance())
    );
    private UserActor actor;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Object[] paramsObject, Method method, ITestContext ctx) throws IOException {
        var testData = extractData(paramsObject);
        var params = testData.getParams();
        Test testClass = method.getAnnotation(Test.class);

        LOG.info(String.format("Running test: %s [%s]", testClass.testName(), testData.getName()));
        actor = ActorFactory.getUserActor(testData.getActor());

        if (!Arrays.asList(testClass.groups()).contains("positive")){
            return;
        }

        try {
            if (!CLIENT.isLiked(actor, params).isLiked()) {
                CLIENT.addLike(actor, params);
            }
        } catch (Exception e){
            LOG.warn("Error in test setup: " + e.getMessage());
        }
    }

    @Test(
            dataProvider = "deleteLikesValidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"positive"},
            suiteName = "likes"
    )
    public void deleteLikeFromItem(TestDataItem testData) throws ClientException, ApiException, InterruptedException {
        DeleteResponse response = CLIENT.deleteLike(actor, testData.getParams());
        LOG.info("Got response: " + response);

        List<Integer> likeListAfter = CLIENT
                .getLikes(actor, testData.getParams())
                .getItems();

        Assert.assertTrue(
                response.toString().matches(testData.getExpected()),
                "Actual response doesn't match expected"
        );
        Assert.assertFalse(
                likeListAfter.contains(actor.getId()),
                "Actor ID present in likes list"
        );
    }

    @Test(
            testName = "Delete likes with invalid parameters",
            dataProvider = "likesInvalidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"negative"},
            suiteName = "likes"
    )
    public void deleteLikeInvalidParams(TestDataItem testData) {
        try {
            CLIENT.deleteLike(actor, testData.getParams());
            throw new AssertionError("Expected exception was not thrown");
        } catch (ClientException | ApiException | InterruptedException expected) {
            var message = expected.getMessage();
            LOG.info("Got response: " + message);

            Assert.assertTrue(
                    message.matches(testData.getExpected()),
                    "Actual exception doesn't match with expected "
            );
        }
    }
}
