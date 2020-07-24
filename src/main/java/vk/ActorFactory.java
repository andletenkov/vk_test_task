package vk;

import com.vk.api.sdk.client.actors.UserActor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Actor factory.
 * Provides Actor objects for specified values from property file
 */
public class ActorFactory {
    private static final String ACTOR_PROPERTIES_FILE_NAME = "/src/main/resources/actor.properties";

    /**
     * User actor factory method.
     */
    public static UserActor getUserActor(String type) throws IOException {
        switch(UserActorType.getByValue(type)){
            case BASIC:
                return fromProperties(type);
            case NO_AUTH:
                return new UserActor(1234, "some_very_secret_token");
            default:
                return new UserActor(6789, "some_very_secret_token");

        }
    }

    /**
     * Reads actor data from properties file.
     *  @param  type string value given by DataProvider.
     *  @return UserActor instance.
     *  @throws IOException if unable read from properties file.
     */
    private static UserActor fromProperties(String type) throws IOException {
        FileInputStream fis = null;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(System.getProperty("user.dir") + ACTOR_PROPERTIES_FILE_NAME);
            property.load(fis);

            Integer actorId = Integer.parseInt(property.getProperty(String.format("%s.id", type)));
            String accessToken = property.getProperty(String.format("%s.token", type));

            return new UserActor(actorId, accessToken);

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}

