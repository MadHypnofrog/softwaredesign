package event_sourcing.events;

import event_sourcing.domains.MemberDatabase;

import java.util.Date;

public class EnterEvent extends EntryEvent {

    private int membershipId;

    public EnterEvent(int memberId, int membershipId) {
        this.memberId = memberId;
        this.membershipId = membershipId;
    }

    public EnterEvent(int memberId, int membershipId, Date at) {
        date = at;
        this.memberId = memberId;
        this.membershipId = membershipId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.enterMember(memberId, date);
        }
    }

    public int getMembershipId() {
        return membershipId;
    }

}
