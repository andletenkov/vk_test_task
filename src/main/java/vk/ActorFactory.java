package vk;

import com.vk.api.sdk.client.actors.UserActor;

public class ActorFactory {
    public static UserActor getActor(String type) {
        ActorType enumType = ActorType.valueOf(type);
        switch (enumType) {
            case BASIC_USER:
                return new UserActor(8922197,
                        "2b78f5dd3dac588902300d2756676a61ae545045cc3f53e441f7376229bda5d0c33564d9a965bdd81617e");
            case BASIC_GROUP:
            case NO_AUTH_USER:
            default:
                return new UserActor(1,
                        "1");
        }
    }
}
