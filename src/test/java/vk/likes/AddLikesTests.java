package vk.likes;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.likes.LikesGetListQuery;
import com.vk.api.sdk.queries.likes.LikesType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AddLikesTests {
    private static final VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
    private static final UserActor USER = new UserActor(
            8922197,
            "2b78f5dd3dac588902300d2756676a61ae545045cc3f53e441f7376229bda5d0c33564d9a965bdd81617e"
    );

    @BeforeMethod
    public void setUp(Object[] paramsObject) throws ClientException, ApiException {
        var params = paramsObject[0];
//        System.out.println(LikesHelper.isLiked(VK, USER, params));
//        System.out.println(params.);
    }

    @Test(
            dataProvider = "addLikesValidData",
            dataProviderClass = LikesDataProvider.class
    )
    public void addLikeToPhoto(Map<String, Object> params) throws ClientException, ApiException {

        LikesGetListQuery query = VK
                .likes()
                .getList(USER, LikesType.valueOf(params.get("type").toString()))
                .itemId(Integer.parseInt(params.get("item_id").toString()));

        List<Integer> resultItems = query.execute().getItems();
        System.out.println(resultItems);
    }
}
