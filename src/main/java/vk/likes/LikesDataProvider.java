package vk.likes;
import org.testng.annotations.DataProvider;
import vk.TestParameters;

public class LikesDataProvider {

    @DataProvider
    public static Object[] addLikesValidData() {
        return TestParameters.fromJson("add_likes.json");
    }
}
