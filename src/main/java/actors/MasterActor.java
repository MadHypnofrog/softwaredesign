package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;

public class MasterActor extends UntypedActor {

    private final int TIMEOUT_MS = 300;
    private List<Response> responses;
    private int received = 0;

    MasterActor() {
        this.responses = new ArrayList<>();
        getContext().setReceiveTimeout(Duration.create(Integer.toString(TIMEOUT_MS) + " milliseconds"));
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String query = (String) message;
            for (SearchEngines engine : SearchEngines.values()) {
                ActorRef child = getContext().actorOf(Props.create(ChildActor.class, engine), engine.toString());
                child.tell(query, self());
            }
        } else if (message instanceof List) {
            responses.addAll((List<Response>) message);
            received++;
        } else if (message instanceof ReceiveTimeout) {
            received++;
        }
        if (received == SearchEngines.values().length) {
            ResponseAggregator.setResponses(responses);
            getContext().system().terminate();
        }
        if (!(message instanceof List) && !(message instanceof ReceiveTimeout)) {
            unhandled(message);
        }
    }
}
