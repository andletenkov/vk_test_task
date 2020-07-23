package vk.likes;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiParamException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;
import vk.ActorFactory;
import vk.BaseTest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddLikesTests extends BaseTest {
    private static final LikesHelper CLIENT = new LikesHelper(
            new VkApiClient(HttpTransportClient.getInstance())
    );


    @BeforeMethod()
    public void setUp(Object[] paramsObject, Method method) throws ClientException, ApiException {
        Test testClass = method.getAnnotation(Test.class);
        if (Arrays.asList(testClass.groups()).contains("positive")){
            return;
        }

        var params = extractParameters(paramsObject);
        UserActor actor = ActorFactory.getActor(params.get("user"));

        if (CLIENT.isLiked(actor, params).isLiked()) {
            CLIENT.deleteLike(actor, params);
        }
    }

    @Test(
            dataProvider = "addLikesValidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"positive", "likes"}
    )
    public void addLikeToItem(Map<String, String> params) throws ClientException, ApiException {
        UserActor actor = ActorFactory.getActor(params.get("user"));

        AddResponse response = CLIENT.addLike(actor, params);
        System.out.println(response);

        List<Integer> likeListAfter = CLIENT.getLikes(actor, params).getItems();

        Assert.assertTrue(response.toString().matches(params.get("expected")));
        Assert.assertTrue(likeListAfter.contains(actor.getId()));
    }

    @Test(
            dataProvider = "addLikesInvalidData",
            dataProviderClass = LikesDataProvider.class,
            groups = {"negative", "likes"}
    )
    public void addLikeInvalidParams(Map<String, String> params) {
        UserActor actor = ActorFactory.getActor(params.get("user"));

        try {
            AddResponse response = CLIENT.addLike(actor, params);
            throw new AssertionError("Expected exception was not thrown");
        } catch (ClientException | ApiException expected) {
            Assert.assertTrue(expected.getMessage().matches(params.get("expected_error")));
        }
    }
}
