package event_sourcing.domains;

import java.util.Date;

public class Membership {

    private int ownerId;
    private int id;
    private Date from;
    private Date to;

    public Membership(int ownerId, int id, Date from, Date to) {
        this.ownerId = ownerId;
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getId() {
        return id;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public void renew(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("Membership ID: %d, owner ID: %d, valid from %s to %s",
                id, ownerId, from.toString(), to.toString());
    }
}
