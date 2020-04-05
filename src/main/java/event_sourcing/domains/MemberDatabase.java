package event_sourcing.domains;

import event_sourcing.events.Event;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberDatabase {

    private Map<Integer, Member> members;
    private Map<Integer, Membership> memberships;
    private Map<Integer, Date> currentVisits;
    private Map<Integer, Pair<Date, Date>> previousVisits;

    public MemberDatabase() {
        members = new HashMap<>();
        memberships = new HashMap<>();
        currentVisits = new HashMap<>();
        previousVisits = new HashMap<>();
    }

    public Member getMember(int memberId) {
        if (!members.containsKey(memberId)) {
            throw new IllegalArgumentException("This member doesn't exist yet!");
        }
        return members.get(memberId);
    }

    public Membership getMembership(int membershipId) {
        if (!memberships.containsKey(membershipId)) {
            throw new IllegalArgumentException("This membership doesn't exist yet!");
        }
        return memberships.get(membershipId);
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public void addMembership(Membership membership) {
        memberships.put(membership.getId(), membership);
    }

    public void renewMembership(int membershipId, Date to) {
        if (!memberships.containsKey(membershipId)) {
            throw new IllegalArgumentException("This membership doesn't exist yet!");
        }
        memberships.get(membershipId).renew(to);
    }

    public void enterMember(int memberId, Date date) {
        if (!members.containsKey(memberId)) {
            throw new IllegalArgumentException("This member doesn't exist yet!");
        }
        currentVisits.put(memberId, date);
    }

    public void exitMember(int memberId, Date date) {
        if (!currentVisits.containsKey(memberId)) {
            throw new IllegalArgumentException("This member didn't enter yet!");
        }
        previousVisits.put(memberId, new Pair<>(currentVisits.remove(memberId), date));
    }

    public void processEvents(Collection<Event> eventCollection) {
        for (Event e : eventCollection) {
            e.process(this);
        }
    }

}
