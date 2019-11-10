package hashtag_tracker;

import com.vk.api.sdk.actions.Newsfeed;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class VKClientTest {

    private String randomString(int length) {
        Random R = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char)(R.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    @Test
    public void fetchPostsTest() throws ClientException, ApiException {
        Random R = new Random();
        String hashtag = randomString(R.nextInt(15) + 1);

        int hours = R.nextInt(24) + 1;
        Integer curTime = (int)(System.currentTimeMillis() / 1000);
        int numPosts = R.nextInt(1000);
        int numOutdatedPosts = R.nextInt(1000);
        int numPostsWithWrongHashtag = R.nextInt(1000);

        List<WallpostFull> expectedPosts = new ArrayList<>();
        List<WallpostFull> allPosts = new ArrayList<>();
        for (int i = 0; i < numPosts; i++) {
            WallpostFull mockPost = mock(WallpostFull.class);
            Integer mockPostDate = curTime - R.nextInt(hours * 3600) + 1;
            when(mockPost.getDate()).thenReturn(mockPostDate);
            when(mockPost.getText()).thenReturn("#" + hashtag + " " + randomString(R.nextInt(100) + 1));
            expectedPosts.add(mockPost);
            allPosts.add(mockPost);
        }

        for (int i = 0; i < numPostsWithWrongHashtag; i++) {
            WallpostFull mockPost = mock(WallpostFull.class);
            Integer mockPostDate = curTime - R.nextInt(hours * 3600) + 1;
            when(mockPost.getDate()).thenReturn(mockPostDate);
            when(mockPost.getText())
                    .thenReturn("#88" + randomString(R.nextInt(15) + 1) + " " + randomString(R.nextInt(100) + 1));
            allPosts.add(mockPost);
        }

        for (int i = 0; i < numOutdatedPosts; i++) {
            WallpostFull mockPost = mock(WallpostFull.class);
            Integer mockPostDate = curTime - hours * 3600 - R.nextInt(hours * 3600);
            when(mockPost.getDate()).thenReturn(mockPostDate);
            when(mockPost.getText()).thenReturn("#" + hashtag + " " + randomString(R.nextInt(100) + 1));
            allPosts.add(mockPost);
        }

        final TestData t = new TestData();
        VkApiClient mockVkApiClient = mock(VkApiClient.class);
        Newsfeed mockNewsfeed = mock(Newsfeed.class);
        NewsfeedSearchQuery mockNewsfeedSearchQuery = mock(NewsfeedSearchQuery.class);
        SearchResponse mockSearchResponse = mock(SearchResponse.class);
        when(mockVkApiClient.newsfeed()).thenReturn(mockNewsfeed);
        when(mockNewsfeed.search(any(ServiceActor.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.q(any(String.class))).thenAnswer(call -> {
            t.query = (String)call.getArguments()[0];
            return mockNewsfeedSearchQuery;
        });
        when(mockNewsfeedSearchQuery.count(any(Integer.class))).thenAnswer(call -> {
            t.count = (int)call.getArguments()[0];
            return mockNewsfeedSearchQuery;
        });
        when(mockNewsfeedSearchQuery.startTime(any(Integer.class))).thenAnswer(call -> {
            t.startTime = (int)call.getArguments()[0];
            return mockNewsfeedSearchQuery;
        });
        when(mockNewsfeedSearchQuery.endTime(any(Integer.class))).thenAnswer(call -> {
            t.endTime = (int)call.getArguments()[0];
            return mockNewsfeedSearchQuery;
        });
        when(mockNewsfeedSearchQuery.execute()).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getItems())
                .thenAnswer(call -> {
                    return allPosts.stream()
                            .filter(post -> post.getDate() >= t.startTime
                                    && post.getDate() <= t.endTime
                                    && post.getText().contains(t.query))
                            .sorted((p1, p2) -> p2.getDate().compareTo(p1.getDate()))
                            .limit(t.count).collect(Collectors.toList());
                });
        VKClient client = new VKClient(mockVkApiClient);
        List<WallpostFull> actualPosts = client.fetchPosts(hashtag, hours, curTime);

        assertEquals(expectedPosts.size(), actualPosts.size());
        for (WallpostFull post: expectedPosts) {
            assertThat(actualPosts, hasItem(post));
        }
    }


    private class TestData {
        int startTime;
        int endTime;
        int count;
        String query;
    }

}
