package event_sourcing.events;

public abstract class EntryEvent extends Event {

    protected int memberId;

    public int getMemberId() {
        return memberId;
    }

}
