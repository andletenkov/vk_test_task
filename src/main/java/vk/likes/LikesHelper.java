package vk.likes;

import com.vk.api.sdk.actions.Likes;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import com.vk.api.sdk.objects.likes.responses.DeleteResponse;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.objects.likes.responses.IsLikedResponse;
import com.vk.api.sdk.queries.likes.LikesType;
import java.util.Map;
import static vk.ActorFactory.getActor;

public class LikesHelper {
    private final Likes client;

    public LikesHelper(VkApiClient vk) {
        client = vk.likes();
    }

    public IsLikedResponse isLiked(UserActor actor, Map<String, String> params) throws ClientException, ApiException {
        return client.isLiked(
                actor,
                LikesType.valueOf(params.get("type")),
                Integer.parseInt(params.get("item_id"))
        ).execute();
    }

    public GetListResponse getLikes(UserActor actor, Map<String, String> params) throws ClientException, ApiException {
        return client.getList(
                actor,
                LikesType.valueOf(params.get("type")))
                .itemId(Integer.parseInt(params.get("item_id")))
                .execute();
    }

    public AddResponse addLike(UserActor actor, Map<String, String> params) throws ClientException, ApiException {
        return client.add(
                actor,
                LikesType.valueOf(params.get("type")),
                Integer.parseInt(params.get("item_id"))
        ).execute();
    }

    public DeleteResponse deleteLike(UserActor actor, Map<String, String> params) throws ClientException, ApiException {
        return client.delete(
                actor,
                LikesType.valueOf(params.get("type")),
                Integer.parseInt(params.get("item_id"))
        ).execute();
    }
}
