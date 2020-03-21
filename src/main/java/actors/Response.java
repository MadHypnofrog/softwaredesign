package actors;

import java.net.URL;

public class Response {

    private SearchEngines engine;
    private URL url;
    private String header;

    Response(SearchEngines engine, URL url, String header) {
        this.engine = engine;
        this.url = url;
        this.header = header;
    }

    @Override
    public String toString() {
        return "From " + engine.toString() + ": " + url.toString() + " " + header;
    }

}
