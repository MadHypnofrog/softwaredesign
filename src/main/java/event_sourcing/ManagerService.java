package event_sourcing;

import event_sourcing.events.NewMemberEvent;
import event_sourcing.events.NewMembershipEvent;
import event_sourcing.events.RenewMembershipEvent;

import java.util.Date;

public class ManagerService {

    private EventProcessor processor;
    private int membershipId = 0;
    private int memberId = 0;

    ManagerService(EventProcessor processor) {
        this.processor = processor;
    }

    public String getMembershipInfo(int membershipId) {
        return processor.getLatestMembershipInfo(membershipId).toString();
    }

    public void newMember(String name) {
        processor.processEvent(new NewMemberEvent(name, ++memberId), null);
    }

    public void newMembership(int memberId, Date from, Date to) {
        processor.processEvent(new NewMembershipEvent(memberId, ++membershipId, from, to), null);
    }

    public void renewMembership(int membershipId, Date to) {
        processor.processEvent(new RenewMembershipEvent(membershipId, to), null);
    }

}
