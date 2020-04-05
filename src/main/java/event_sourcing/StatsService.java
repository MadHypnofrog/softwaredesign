package event_sourcing;

import event_sourcing.events.EnterEvent;
import event_sourcing.events.EntryEvent;
import event_sourcing.events.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsService {

    private EventProcessor processor;
    private List<Event> events;
    private int processedQueries = 0;

    StatsService(EventProcessor processor) {
        this.processor = processor;
        events = new ArrayList<>();
    }

    public String getStatsForEachDay() {
        updateDatabase();
        Map<String, List<Event>> eventsForEachDay = events.stream()
                .filter(e -> e instanceof EnterEvent)
                .collect(Collectors.groupingBy(e -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(e.getDate());
                    return String.format("%s.%s.%s", calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                }));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Event>> entry : eventsForEachDay.entrySet()) {
            sb.append(String.format("At date %s: %d visitors\n", entry.getKey(), entry.getValue().size()));
        }
        return sb.toString();
    }

    public String getAverageTime() {
        updateDatabase();
        List<EntryEvent> entryEvents = events.stream().filter(e -> e instanceof EntryEvent)
                .map(e -> (EntryEvent) e).collect(Collectors.toList());
        double time = 0;
        int visits = 0;
        Map<Integer, Date> currentVisits = new HashMap<>();
        for (EntryEvent e : entryEvents) {
            if (e instanceof EnterEvent) {
                currentVisits.put(e.getMemberId(), e.getDate());
            } else {
                time += (e.getDate().getTime() - currentVisits.remove(e.getMemberId()).getTime()) / 1000 / 60;
                visits++;
            }
        }
        return String.format("Average time based on %d visits: %f minutes", visits, time / visits);
    }

    private void updateDatabase() {
        List<Event> toUpdate = processor.getEventLog().stream().skip(processedQueries).collect(Collectors.toList());
        events.addAll(toUpdate);
        processedQueries += toUpdate.size();
    }

}
