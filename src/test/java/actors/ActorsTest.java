package actors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorsTest {

    private StubServer server;

    @BeforeEach
    void init() {
        server = new StubServer();
        ResponseAggregator.setResponses(null);
    }

    @Test
    public void noTimeout() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        aggregator.aggregate("test_query");
        Thread.sleep(3000);
        assertEquals(15, ResponseAggregator.getResponses().size());
    }

    @Test
    public void bigTimeout() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        server.setTimeout(600);
        aggregator.aggregate("test_query");
        Thread.sleep(3000);
        assertEquals(0, ResponseAggregator.getResponses().size());
    }

    @Test
    public void bigTimeoutFastYandex() throws InterruptedException {
        ResponseAggregator aggregator = new ResponseAggregator();
        server.setTimeout(600);
        server.setFastYandex(true);
        aggregator.aggregate("test_query");
        Thread.sleep(3000);
        assertEquals(5, ResponseAggregator.getResponses().size());
    }

}
