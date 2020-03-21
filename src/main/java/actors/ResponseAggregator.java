package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.PrintWriter;
import java.util.List;

public class ResponseAggregator {

    private static List<Response> responseList;

    ResponseAggregator() {
    }

    public void aggregate(String query) {
        ActorSystem system = ActorSystem.create("aggregation");
        ActorRef master = system.actorOf(Props.create(MasterActor.class, new PrintWriter(System.out)), "master");
        master.tell(query, ActorRef.noSender());
    }

    public static void setResponses(List<Response> responseList) {
        ResponseAggregator.responseList = responseList;
    }

    public static List<Response> getResponses() {
        return responseList;
    }

}
