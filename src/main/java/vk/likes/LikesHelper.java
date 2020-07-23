package vk.likes;

import com.vk.api.sdk.actions.Likes;
import com.vk.api.sdk.client.ApiRequest;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.responses.AddResponse;
import com.vk.api.sdk.objects.likes.responses.DeleteResponse;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.objects.likes.responses.IsLikedResponse;
import com.vk.api.sdk.queries.likes.LikesType;
import java.util.Map;

public class LikesHelper {
    private final Likes client;
    private static final int DEFAULT_RETRY_TIMEOUT = 1000;
    private static final int DEFAULT_RETRY_REQUEST_COUNT = 3;


    public LikesHelper(VkApiClient vk) {
        client = vk.likes();
    }

    @SuppressWarnings("unchecked")
    private <T> T safeExecute(ApiRequest request) throws InterruptedException, ClientException, ApiException {
        int retries = DEFAULT_RETRY_REQUEST_COUNT;
        ApiTooManyException tooManyRequestsException = null;
        for (;retries > 0; retries--) {
            try {
                return (T) request.execute();
            } catch (ApiTooManyException e){
                Thread.sleep(DEFAULT_RETRY_TIMEOUT);
                tooManyRequestsException = e;
            }
        }
        throw tooManyRequestsException;

    }

    public IsLikedResponse isLiked(UserActor actor, Map<String, String> params)
            throws ClientException, ApiException, InterruptedException {
        return safeExecute(
                client.isLiked(
                        actor,
                        LikesType.valueOf(params.get("type")),
                        Integer.parseInt(params.get("item_id"))
        ));
    }

    public GetListResponse getLikes(UserActor actor, Map<String, String> params)
            throws ClientException, ApiException, InterruptedException {
        return safeExecute(
                client.getList(actor, LikesType.valueOf(params.get("type")))
                        .itemId(Integer.parseInt(params.get("item_id"))));
    }

    public AddResponse addLike(UserActor actor, Map<String, String> params)
            throws ClientException, ApiException, InterruptedException {
        return safeExecute(
                client.add(
                        actor,
                        LikesType.valueOf(params.get("type")),
                        Integer.parseInt(params.get("item_id"))
                ));
    }

    public DeleteResponse deleteLike(UserActor actor, Map<String, String> params)
            throws ClientException, ApiException, InterruptedException {
        return safeExecute(
                client.delete(
                        actor,
                        LikesType.valueOf(params.get("type")),
                        Integer.parseInt(params.get("item_id"))
                ));
    }
}
