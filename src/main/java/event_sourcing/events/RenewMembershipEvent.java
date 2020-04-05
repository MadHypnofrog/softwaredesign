package event_sourcing.events;

import event_sourcing.domains.MemberDatabase;

import java.util.Date;

public class RenewMembershipEvent extends MembershipEvent {

    public RenewMembershipEvent(int membershipId, Date membershipEnd) {
        this.membershipId = membershipId;
        this.membershipEnd = membershipEnd;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.renewMembership(membershipId, membershipEnd);
        }
    }

}
