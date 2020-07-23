package vk.likes;
import org.testng.annotations.DataProvider;
import vk.TestData;

public class LikesDataProvider {

    @DataProvider
    public static Object[] addLikesValidData() {
        return TestData.fromJson("add_likes.json");
    }

    @DataProvider
    public static Object[] deleteLikesValidData() {
        return TestData.fromJson("delete_likes.json");
    }

    @DataProvider
    public static Object[] likesInvalidData() {
        return TestData.fromJson("likes_invalid.json");
    }
}
