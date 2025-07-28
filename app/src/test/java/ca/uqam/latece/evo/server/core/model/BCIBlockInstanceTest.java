package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * BCIBlockInstance Test.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
public class BCIBlockInstanceTest {
    private BehaviorChangeInterventionBlockInstance bciBlockInstance;

    private BCIActivityInstance bciActivityInstance;

    @BeforeEach
    public void setUp() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        bciActivityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(bciActivityInstance));

        bciBlockInstance = new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.BEGINNING, activities);
    }

    @Test
    public void testGetActivities() {
        List<BCIActivityInstance> activities = bciBlockInstance.getActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
    }

    @Test
    public void testAddActivity() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2028/01/08"), participants);

        bciBlockInstance.addActivity(activityInstance);

        List<BCIActivityInstance> activities = bciBlockInstance.getActivities();

        assertNotNull(activities);
        assertEquals(2, activities.size());
    }

    @Test
    public void testAddActivities() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        BCIActivityInstance activityInstance2 = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"), participants);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(activityInstance, activityInstance2));

        bciBlockInstance.addActivities(activities);

        assertNotNull(bciBlockInstance.getActivities());
        assertEquals(3, bciBlockInstance.getActivities().size());
    }

    @Test
    public void testRemoveActivity() {
        bciBlockInstance.removeActivity(bciActivityInstance);

        assertNotNull(bciBlockInstance.getActivities());
        assertEquals(0, bciBlockInstance.getActivities().size());
    }
}
