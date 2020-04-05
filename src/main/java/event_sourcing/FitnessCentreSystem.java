package event_sourcing;

public class FitnessCentreSystem {

    public EntryService entryService;
    public ManagerService managerService;
    public StatsService statsService;
    public EventProcessor processor;

    public FitnessCentreSystem() {
        processor = new EventProcessor();
        entryService = new EntryService(processor);
        managerService = new ManagerService(processor);
        statsService = new StatsService(processor);
    }

}
