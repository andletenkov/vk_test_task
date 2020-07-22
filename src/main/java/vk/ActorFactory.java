package vk;

public class ActorFactory {
    public static String getActor(ActorType type){
        switch (type){
            case BASIC_USER:
                return "user";
            case BASIC_GROUP:
                return "group";
            default:
                return "default";
        }
    }
}
