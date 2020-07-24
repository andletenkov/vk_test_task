package vk;

public enum  UserActorType {
    BASIC("basic"),
    NO_AUTH("no_auth");

    private final String value;

    UserActorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserActorType getByValue(String value){
        for (UserActorType type: values()){
            if (type.getValue().equals(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown actor type: " + value);
    }
}
