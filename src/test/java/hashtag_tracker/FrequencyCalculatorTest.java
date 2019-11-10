package hashtag_tracker;

import com.vk.api.sdk.objects.wall.WallpostFull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FrequencyCalculatorTest {

    @Test
    public void testFrequencyCalculator() {
        Random R = new Random();

        int hours = R.nextInt(24) + 1;
        Integer curTime = (int)(System.currentTimeMillis() / 1000);
        List<WallpostFull> posts = new ArrayList<>();
        int[] expectedFrequencies = new int[hours];
        for (int hour = 0; hour < hours; hour++) {
            expectedFrequencies[hour] = R.nextInt(100);
            for (int post = 0; post < expectedFrequencies[hour]; post++) {
                WallpostFull mockPost = mock(WallpostFull.class);
                Integer mockPostDate = curTime - 3600 * hour - R.nextInt(3600) + 1;
                when(mockPost.getDate()).thenReturn(mockPostDate);
                posts.add(mockPost);
            }
        }
        int[] actualFrequencies = FrequencyCalculator.calculate(posts, hours, curTime);
        assertArrayEquals(expectedFrequencies, actualFrequencies);
    }

}
