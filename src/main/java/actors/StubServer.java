package actors;

import javafx.util.Pair;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StubServer {

    private static int timeout_ms = 100;
    private static boolean fastYandex = false;

    StubServer() {
        StubServer.timeout_ms = 100;
        StubServer.fastYandex = false;
    }

    public void setTimeout(int timeout_ms) {
        StubServer.timeout_ms = timeout_ms;
    }

    public void setFastYandex(boolean fastYandex) {
        StubServer.fastYandex = fastYandex;
    }

    static JSONObject process(URL url) {
        long timeStart = System.currentTimeMillis();
        Map<String, String> queries = new HashMap<>();
        for (String query : url.getQuery().split("&")) {
            String[] values = query.split("=");
            queries.put(values[0], values[1]);
        }
        String host = url.getHost();
        JSONObject json = new JSONObject();
        String search = queries.get("query");
        for (int i = 0; i < Integer.valueOf(queries.get("number")); i++) {
            Pair<String, String> response = generateResponse(host, search);
            json.put(response.getKey(), response.getValue());
        }
        if (!host.equals("yandex.ru") || !fastYandex) {
            while (timeStart + timeout_ms > System.currentTimeMillis()) {
            }
        }
        return json;
    }

    private static Pair<String, String> generateResponse(String host, String query) {
        switch (host) {
            case "yandex.ru": {
                return new Pair<>(generateSite(10, 0.3, 0.1),
                        query + " " + generateText(5) + " " + query + " " + generateText(10));
            }
            case "google.com": {
                return new Pair<>(generateSite(6, 0.7, 0.5),
                        generateText(11) + " " + query + " " + generateText(3) + " " + query + " " + generateText(5));
            }
            case "bing.com": {
                return new Pair<>(generateSite(12, 0.9, 0.2),
                        generateText(3) + " " + query + " " + generateText(3) + " " + query);
            }
            default: {
                return null;
            }
        }
    }

    private static String generateSite(int length, double probCom, double probNum) {
        Random r = new Random();
        double com = r.nextDouble();
        String siteName = "https://" + generateAlphanumeric(length, probNum);
        return com < probCom ? siteName + ".com" : siteName + ".ru";
    }

    private static String generateText(int words) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < words; i++) {
            sb.append(generateAlphanumeric(r.nextInt(10) + 1, 0)).append(" ");
        }
        return sb.toString().trim();
    }

    private static String generateAlphanumeric(int length, double probNum) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            double num = r.nextDouble();
            if (num < probNum) {
                sb.append((char) (r.nextInt(10) + '0'));
            } else {
                sb.append((char) (r.nextInt(26) + 'a'));
            }
        }
        return sb.toString();
    }

}
