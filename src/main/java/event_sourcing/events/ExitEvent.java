package event_sourcing.events;

import event_sourcing.domains.MemberDatabase;

import java.util.Date;

public class ExitEvent extends EntryEvent {

    public ExitEvent(int memberId) {
        this.memberId = memberId;
    }

    public ExitEvent(int memberId, Date at) {
        date = at;
        this.memberId = memberId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.exitMember(memberId, date);
        }
    }

}
