package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
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
 * BCIPhaseInstance Test.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
public class BCIPhaseInstanceTest {
    private BehaviorChangeInterventionPhaseInstance bciPhaseInstance;

    private BehaviorChangeInterventionBlockInstance bciBlockInstance;

    @BeforeEach
    public void setUp() {
        Role role = new Role("Administrator");

        Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));

        List<Participant> participants = List.of(participant);

        BCIActivityInstance bciActivityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(bciActivityInstance));

        bciBlockInstance = new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.BEGINNING, activities);

        List<BehaviorChangeInterventionBlockInstance> blockInstances = new ArrayList<>(List.of(bciBlockInstance));

        BCIModuleInstance bciModuleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL,
                activities);

        List<BCIModuleInstance> moduleInstances = new ArrayList<>(List.of(bciModuleInstance));

        bciPhaseInstance = new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), bciBlockInstance, blockInstances, moduleInstances);
    }

    @Test
    public void testGetActivities() {
        List<BehaviorChangeInterventionBlockInstance> activities = bciPhaseInstance.getActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
    }

    @Test
    public void testAddActivity() {
        BehaviorChangeInterventionBlockInstance newBlockInstance = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                TimeCycle.END, bciBlockInstance.getActivities());

        bciPhaseInstance.addActivity(newBlockInstance);

        List<BehaviorChangeInterventionBlockInstance> activities = bciPhaseInstance.getActivities();

        assertNotNull(activities);
        assertEquals(2, activities.size());
    }

    @Test
    public void testAddActivities() {
        BehaviorChangeInterventionBlockInstance newBlockInstance = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                TimeCycle.END, bciBlockInstance.getActivities());

        BehaviorChangeInterventionBlockInstance newBlockInstance2 = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                TimeCycle.UNSPECIFIED, bciBlockInstance.getActivities());

        List<BehaviorChangeInterventionBlockInstance> activities = new ArrayList<>(List.of(newBlockInstance, newBlockInstance2));

        bciPhaseInstance.addActivities(activities);

        assertNotNull(bciPhaseInstance.getActivities());
        assertEquals(3, bciPhaseInstance.getActivities().size());
    }

    @Test
    public void testRemoveActivity() {
        bciPhaseInstance.removeActivity(bciBlockInstance);

        assertNotNull(bciPhaseInstance.getActivities());
        assertEquals(0, bciPhaseInstance.getActivities().size());
    }
}
