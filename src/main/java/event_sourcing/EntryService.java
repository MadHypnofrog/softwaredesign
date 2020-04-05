package event_sourcing;

import event_sourcing.domains.Membership;
import event_sourcing.events.EnterEvent;
import event_sourcing.events.ExitEvent;

import java.util.Date;

public class EntryService {

    private EventProcessor processor;

    EntryService(EventProcessor processor) {
        this.processor = processor;
    }

    public void enterMember(int memberId, int membershipId) {
        Membership membership = processor.getLatestMembershipInfo(membershipId);
        if (membership.getOwnerId() != memberId) {
            throw new IllegalArgumentException("Can't enter with someone else's membership!");
        }
        if (membership.getTo().compareTo(new Date()) < 0) {
            throw new IllegalArgumentException("This membership has expired!");
        }
        processor.processEvent(new EnterEvent(memberId, membershipId), null);
    }

    public void enterMemberAt(int memberId, int membershipId, Date at) {
        Membership membership = processor.getLatestMembershipInfo(membershipId);
        if (membership.getOwnerId() != memberId) {
            throw new IllegalArgumentException("Can't enter with someone else's membership!");
        }
        if (membership.getTo().compareTo(new Date()) < 0) {
            throw new IllegalArgumentException("This membership has expired!");
        }
        processor.processEvent(new EnterEvent(memberId, membershipId, at), null);
    }

    public void exitMember(int memberId) {
        processor.processEvent(new ExitEvent(memberId), null);
    }

    public void exitMemberAt(int memberId, Date at) {
        processor.processEvent(new ExitEvent(memberId, at), null);
    }

}
