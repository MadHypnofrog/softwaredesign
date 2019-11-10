package hashtag_tracker;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallpostFull;

import java.util.List;

public class FrequencyCalculator {

    static int[] calculate(List<WallpostFull> allPosts, int hours, int endTime) {
        int[] frequencies = new int[hours];
        for (WallpostFull post: allPosts) {
            int hour = (endTime - post.getDate()) / 3600;
            frequencies[hour]++;
        }
        return frequencies;
    }

    public static int[] getFrequencies(String hashtag, int hours) {
        VKClient vkClient = new VKClient(new VkApiClient(HttpTransportClient.getInstance()));
        int endTime = (int)(System.currentTimeMillis() / 1000);
        List<WallpostFull> allPosts = vkClient.fetchPosts(hashtag, hours, endTime);
        return calculate(allPosts, hours, endTime);
    }

}
