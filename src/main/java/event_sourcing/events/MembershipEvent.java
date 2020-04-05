package event_sourcing.events;

import java.util.Date;

public abstract class MembershipEvent extends Event {

    protected int membershipId;
    protected Date membershipEnd;

    public int getMembershipId() {
        return membershipId;
    }

    public Date getMembershipEnd() {
        return membershipEnd;
    }

}
