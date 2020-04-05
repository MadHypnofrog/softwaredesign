package event_sourcing.events;

import event_sourcing.domains.Member;
import event_sourcing.domains.MemberDatabase;

public class NewMemberEvent extends Event {

    private String name;
    private int memberId;

    public NewMemberEvent(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public void process(MemberDatabase database) {
        if (database != null) {
            database.addMember(new Member(name, memberId));
        }
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

}
