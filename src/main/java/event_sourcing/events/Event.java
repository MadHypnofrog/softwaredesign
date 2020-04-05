package event_sourcing.events;

import event_sourcing.domains.MemberDatabase;

import java.util.Date;

public abstract class Event {

    protected Date date;

    Event() {
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public abstract void process(MemberDatabase database);

}
