package vk;

import com.vk.api.sdk.client.actors.UserActor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Actor factory.
 * Provides Actor objects for specified values from property file
 */
public class ActorFactory {
    private static final String ACTOR_PROPERTIES_FILE_NAME = "actor.properties";

    /**
     * User actor factory method.
     * @param  type string value given by DataProvider.
     * @return UserActor instance.
     * @throws IOException if unable read from properties file.
     */
    public static UserActor getUserActor(String type) throws IOException {
        Path propertiesPath = FileSystems
                .getDefault()
                .getPath(System.getProperty("user.dir"), ACTOR_PROPERTIES_FILE_NAME);

        FileInputStream fis = null;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(propertiesPath.toString());
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

