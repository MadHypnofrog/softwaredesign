package hashtag_tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in));) {
            String hashtag = r.readLine();
            int hours = Integer.parseInt(r.readLine());
            for (int freq : FrequencyCalculator.getFrequencies(hashtag, hours)) System.out.print(freq + " ");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
