package vk.likes;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.likes.LikesType;

import java.util.Map;

public class LikesHelper {
    protected static Boolean isLiked(
            VkApiClient vk,
            UserActor user,
            Map<String, Object> params
    ) throws ClientException, ApiException {
        LikesType type = (LikesType) params.get("type");
        Integer item = (Integer) params.get("item_id");

        return vk.likes().isLiked(user, type, item).execute().isLiked();
    }
}
