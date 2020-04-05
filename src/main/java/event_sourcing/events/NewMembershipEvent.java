package event_sourcing.events;

import event_sourcing.domains.MemberDatabase;
import event_sourcing.domains.Membership;

import java.util.Date;

public class NewMembershipEvent extends MembershipEvent {

    private int memberId;
    private Date membershipStart;

    public NewMembershipEvent(int memberId, int membershipId, Date membershipStart, Date membershipEnd) {
        this.memberId = memberId;
        this.membershipId = membershipId;
        this.membershipStart = membershipStart;
        this.membershipEnd = membershipEnd;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.addMembership(new Membership(memberId, membershipId, membershipStart, membershipEnd));
        }
    }

    public int getMemberId() {
        return memberId;
    }

    public Date getMembershipStart() {
        return membershipStart;
    }

}
