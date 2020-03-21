package actors;

import akka.actor.UntypedActor;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChildActor extends UntypedActor {

    private SearchEngines engine;
    private final int SEARCH_RESULTS = 5;

    ChildActor(SearchEngines engine) {
        this.engine = engine;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String query = (String) message;
            URL url = new URL("https://" + engine.toString() + "/search?number=" + SEARCH_RESULTS + "&query=" + query);
            JSONObject jsonResponses = StubServer.process(url);
            List<Response> responses = new ArrayList<>();
            for (String site : jsonResponses.keySet()) {
                responses.add(new Response(engine, new URL(site), jsonResponses.getString(site)));
            }
            sender().tell(responses, self());
            getContext().stop(self());
        } else {
            unhandled(message);
        }
    }
}
