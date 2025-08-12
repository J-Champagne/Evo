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

public class BCIInstanceTest {
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";

    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    private BehaviorChangeInterventionPhaseInstance bciPhaseInstance;

    private BehaviorChangeInterventionInstance bciInstance;

    @BeforeEach
    public void setUp() {
        Role role = new Role("Administrator");

        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");

        Patient patient = new Patient("Bob", "bob@gmail.com", "222-2222",
                "1901-01-01", "Participant", "3333 Street", pmf);

        Participant participant = new Participant(role, patient);

        List<Participant> participants = List.of(participant);

        BCIActivityInstance bciActivityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

        List<BCIActivityInstance> activities = new ArrayList<>(List.of(bciActivityInstance));

        BehaviorChangeInterventionBlockInstance bciBlockInstance = new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.BEGINNING, activities);

        List<BehaviorChangeInterventionBlockInstance> blockInstances = new ArrayList<>(List.of(bciBlockInstance));

        BCIModuleInstance bciModuleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL,
                activities);

        List<BCIModuleInstance> moduleInstances = new ArrayList<>(List.of(bciModuleInstance));

        BehaviorChangeInterventionPhase bciPhase = new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION, PHASE_EXIT_CONDITION);

        bciPhaseInstance = new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), bciBlockInstance, blockInstances, moduleInstances, bciPhase);

        List<BehaviorChangeInterventionPhaseInstance> phaseInstances = new ArrayList<>(List.of(bciPhaseInstance));

        bciInstance = new BehaviorChangeInterventionInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), patient, bciPhaseInstance, phaseInstances);

    }

    @Test
    public void testGetActivities() {
        List<BehaviorChangeInterventionPhaseInstance> activities = bciInstance.getActivities();

        assertNotNull(activities);
        assertEquals(1, activities.size());
    }

    @Test
    public void testAddActivity() {
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                bciPhaseInstance.getCurrentBlock(), bciPhaseInstance.getActivities(), bciPhaseInstance.getModules(),
                bciPhaseInstance.getBehaviorChangeInterventionPhase());

        bciInstance.addActivity(newPhaseInstance);

        List<BehaviorChangeInterventionPhaseInstance> activities = bciInstance.getActivities();

        assertNotNull(activities);
        assertEquals(2, activities.size());
    }

    @Test
    public void testAddActivities() {
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.FINISHED, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                bciPhaseInstance.getCurrentBlock(), bciPhaseInstance.getActivities(), bciPhaseInstance.getModules(),
                bciPhaseInstance.getBehaviorChangeInterventionPhase());

        BehaviorChangeInterventionPhaseInstance newPhaseInstance2 = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.UNKNOWN, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2030/01/08"),
                bciPhaseInstance.getCurrentBlock(), bciPhaseInstance.getActivities(), bciPhaseInstance.getModules(),
                bciPhaseInstance.getBehaviorChangeInterventionPhase());

        List<BehaviorChangeInterventionPhaseInstance> activities = new ArrayList<>(List.of(newPhaseInstance, newPhaseInstance2));

        bciInstance.addActivities(activities);

        assertNotNull(bciInstance.getActivities());
        assertEquals(3, bciInstance.getActivities().size());
    }

    @Test
    public void testRemoveActivity() {
        bciInstance.removeActivity(bciPhaseInstance);

        assertNotNull(bciInstance.getActivities());
        assertEquals(0, bciInstance.getActivities().size());
    }
}
