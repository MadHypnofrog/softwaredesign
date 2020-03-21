package rx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RxTest {

    @BeforeEach
    public void init() {
        sendRequest("wipe");
    }

    @Test
    public void registerAddAndShow() {
        assertTrue(sendRequest("register?login=first&currency=RUR").contains("registered."));
        assertTrue(sendRequest("add?name=candy&price=2.5&currency=EUR").contains("added."));
        assertTrue(sendRequest("show?login=first").contains("price: 213,500000 RUR"));
        assertTrue(sendRequest("register?login=second&currency=USD").contains("registered."));
        assertTrue(sendRequest("show?login=second").contains("price: 2,672090 USD"));
    }

    @Test
    public void incorrectLoginAndCurrency() {
        assertTrue(sendRequest("register?login=&currency=RUR").contains("Empty login"));
        assertTrue(sendRequest("register?login=first&currency=RUR").contains("registered."));
        assertTrue(sendRequest("register?login=first&currency=EUR").contains("is already registered"));
        assertTrue(sendRequest("register?login=second&currency=2").contains("Invalid currency"));
        assertTrue(sendRequest("add?name=candy&price=2.5&currency=EUR").contains("added."));
        assertTrue(sendRequest("show?login=second").contains("Invalid login"));
    }

    @Test
    public void incorrectPrice() {
        assertTrue(sendRequest("register?login=first&currency=RUR").contains("registered."));
        assertTrue(sendRequest("add?name=candy&price=invalid&currency=EUR").contains("Invalid price"));
        assertTrue(sendRequest("add?name=candy&price=-1&currency=EUR").contains("Negative price"));
    }


    private String sendRequest(String query) {
        try {
            URL url = new URL("http://localhost:8080/" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    responseStr.append(line);
                }
            }
            return responseStr.toString();
        } catch (IOException ex) {
            fail("Request " + query + " failed: " + ex.getMessage());
            return "";
        }
    }

}
