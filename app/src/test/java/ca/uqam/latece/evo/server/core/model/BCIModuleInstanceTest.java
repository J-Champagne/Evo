package ca.uqam.latece.evo.server.core.model;

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

public class BCIModuleInstanceTest {
     private BCIModuleInstance bciModuleInstance;

     private BCIActivityInstance bciActivityInstance;

    @BeforeEach
    public void setUp() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        bciActivityInstance = new BCIActivityInstance(
                "In progress", LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(bciActivityInstance));

        bciModuleInstance = new BCIModuleInstance("NOTSTARTED", OutcomeType.SUCCESSFUL, activities);
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

        BCIActivityInstance activityInstance = new BCIActivityInstance(
                "Not started", LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2028/01/08"), participants);

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

        BCIActivityInstance activityInstance = new BCIActivityInstance(
                "Not started", LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        BCIActivityInstance activityInstance2 = new BCIActivityInstance(
                "Not started", LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"), participants);

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