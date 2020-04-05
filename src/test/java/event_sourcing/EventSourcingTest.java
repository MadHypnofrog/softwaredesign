package event_sourcing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventSourcingTest {

    private FitnessCentreSystem system;

    @BeforeEach
    void init() {
        system = new FitnessCentreSystem();
    }

    @Test
    public void testNewUsersEntry() {
        system.managerService.newMember("1st");
        system.managerService.newMember("2nd");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 2));
        system.entryService.enterMember(1, 1);
        system.entryService.exitMember(1);
    }

    @Test
    public void testInvalidEntries() throws InterruptedException {
        system.managerService.newMember("1st");
        system.managerService.newMember("2nd");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 2));
        Thread.sleep(2000);
        try {
            system.entryService.enterMember(1, 1);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("has expired"));
        }
        try {
            system.entryService.enterMember(2, 1);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Can't enter with"));
        }
        try {
            system.entryService.enterMember(2, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("This membership doesn't"));
        }
    }

    @Test
    public void testMembershipRenew() {
        system.managerService.newMember("1st");
        Date date = new Date();
        Date endDate = new Date(date.getTime() + 1000 * 2);
        system.managerService.newMembership(1, date, endDate);
        assertTrue(system.managerService.getMembershipInfo(1).contains("Membership ID: 1, owner ID: 1, valid from "
                + date.toString() + " to " + endDate.toString()));
        endDate = new Date(date.getTime() + 1000 * 100);
        system.managerService.renewMembership(1, endDate);
        assertTrue(system.managerService.getMembershipInfo(1).contains("Membership ID: 1, owner ID: 1, valid from "
                + date.toString() + " to " + endDate.toString()));
    }

    @Test
    public void testStatistics() {
        system.managerService.newMember("1st");
        system.managerService.newMember("2nd");
        system.managerService.newMembership(1, new Date(), new Date(new Date().getTime() + 1000 * 1000000));
        system.managerService.newMembership(2, new Date(), new Date(new Date().getTime() + 1000 * 1000000));
        Date date = new Date();
        system.entryService.enterMemberAt(1, 1, date);
        date = new Date(date.getTime() + 1000 * 60 * 5);
        system.entryService.exitMemberAt(1, date);  // 5 mins
        system.entryService.enterMemberAt(2, 2, date);
        system.entryService.exitMemberAt(2, new Date(date.getTime() + 1000 * 60 * 8));  // 8 mins
        system.entryService.enterMemberAt(1, 1, new Date(date.getTime() + 1000 * 60 * 3));
        assertTrue(system.statsService.getAverageTime().contains("2 visits: 6,500000 minutes"));
        system.entryService.exitMemberAt(1, new Date(date.getTime() + 1000 * 60 * 13));  // 10 mins
        system.entryService.enterMemberAt(1, 1, new Date(date.getTime() + 1000 * 60 * 1440));
        system.entryService.exitMemberAt(1, new Date(date.getTime() + 1000 * 60 * 1441));  // 1 min, next day
        assertTrue(system.statsService.getAverageTime().contains("4 visits: 6,000000 minutes"));
        assertTrue(system.statsService.getStatsForEachDay().contains("3 visitors"));
        assertTrue(system.statsService.getStatsForEachDay().contains("1 visitors"));
    }

}
