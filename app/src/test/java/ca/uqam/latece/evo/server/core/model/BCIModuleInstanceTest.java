package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BCIModuleInstance Test.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
public class BCIModuleInstanceTest {
    private BCIModuleInstance bciModuleInstance;

    private BCIActivityInstance bciActivityInstance;

    @BeforeEach
    public void setUp() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
                "Intervention ENTRY", "Intervention EXIT");

        bciActivityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants, bciActivity);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(bciActivityInstance));

        bciModuleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL, activities);
    }

    @Test
    public void testGetActivities() {
        List<BCIActivityInstance> activities = bciModuleInstance.getActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
    }

    @Test
    public void testAddActivity() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
                "Intervention ENTRY", "Intervention EXIT");

        BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2028/01/08"), participants, bciActivity);

        bciModuleInstance.addActivity(activityInstance);

        List<BCIActivityInstance> activities = bciModuleInstance.getActivities();

        assertNotNull(activities);
        assertEquals(2, activities.size());
    }

    @Test
    public void testAddActivities() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
                "Intervention ENTRY", "Intervention EXIT");

        BCIActivity bciActivity2 = new BCIActivity("Wight loss", "Description", ActivityType.BCI_ACTIVITY,
                "Intervention ENTRY", "Intervention EXIT");

        BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants, bciActivity);

        BCIActivityInstance activityInstance2 = new BCIActivityInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"), participants, bciActivity2);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(activityInstance, activityInstance2));

        bciModuleInstance.addActivities(activities);

        assertNotNull(bciModuleInstance.getActivities());
        assertEquals(3, bciModuleInstance.getActivities().size());
    }

    @Test
    public void testRemoveActivity() {
        bciModuleInstance.removeActivity(bciActivityInstance);

        assertNotNull(bciModuleInstance.getActivities());
        assertEquals(0, bciModuleInstance.getActivities().size());
    }
}