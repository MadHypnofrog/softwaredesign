package hashtag_tracker;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallpostFull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VKClient {

    private VkApiClient client;
    private final String token = "f6a22391f6a22391f6a22391c6f6c29e9fff6a2f6a22391acc144b74f1bead4634fc13d";
    private final Integer appId = 6339854;
    private ServiceActor actor;

    public VKClient(VkApiClient cl) {
        client = cl;
        actor = new ServiceActor(appId, token);
    }

    public List<WallpostFull> fetchPosts(String hashtag, int hours, int endTime) {
        List<WallpostFull> allPosts = new ArrayList<>();
        try {
            int startTime = endTime - 3600 * hours;
            List<WallpostFull> posts;
            do {
                posts = client.newsfeed()
                        .search(actor)
                        .q("#" + hashtag)
                        .count(100)
                        .startTime(startTime)
                        .endTime(endTime)
                        .execute()
                        .getItems();
                if (!posts.isEmpty()) {
                    allPosts.addAll(posts);
                    endTime = posts.get(posts.size() - 1).getDate() - 1;
                }
            } while (posts.size() == 100);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return allPosts;
    }

}
