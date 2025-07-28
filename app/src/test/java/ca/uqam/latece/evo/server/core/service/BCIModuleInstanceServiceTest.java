package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.event.BCIModuleInstanceEvent;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.service.instance.*;

import ca.uqam.latece.evo.server.core.util.DateFormatter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The test class for the {@link BCIModuleInstanceService}, responsible for testing its various functionalities. This
 * class includes integration tests for CRUD operations and other repository queries using a PostgreSQL database in a
 * containerized setup.
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@RecordApplicationEvents
@ApplicationScope
@ContextConfiguration(classes = {BCIModuleInstanceService.class, BCIModuleInstanceService.class})
public class BCIModuleInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    BCIModuleInstanceService bciModuleInstanceService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    private BCIModuleInstance moduleInstance;

    @Autowired
    private ApplicationEvents applicationEvents;

    @BeforeEach
    public void setUp() {
        Role role = roleService.create(new Role("Administrator"));
        HealthCareProfessional hcp = healthCareProfessionalService.create(new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));
        Participant participant = participantService.create(new Participant(role, hcp));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED, LocalDate.now(),
                DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), OutcomeType.SUCCESSFUL, activities));
    }

    @Test
    @Override
    public void testSave() {
        assert moduleInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        moduleInstance.setOutcome(OutcomeType.UNSUCCESSFUL);
        BCIModuleInstance updated = bciModuleInstanceService.update(moduleInstance);
        assertEquals(moduleInstance.getOutcome(), updated.getOutcome());
    }

    @Test
    @Override
    void testFindById() {
        BCIModuleInstance found = bciModuleInstanceService.findById(moduleInstance.getId());
        assertEquals(moduleInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        bciModuleInstanceService.deleteById(moduleInstance.getId());
        assertThrows(EntityNotFoundException.class, () -> bciModuleInstanceService.findById(moduleInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        List<BCIActivityInstance> activities = new ArrayList<>(moduleInstance.getActivities());
        bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.PARTIALLYSUCCESSFUL, activities));
        List<BCIModuleInstance> results = bciModuleInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByOutcome() {
        List<BCIModuleInstance> found = bciModuleInstanceService.findByOutcome(OutcomeType.SUCCESSFUL);

        assertEquals(1, found.size());
        assertEquals(moduleInstance.getId(), found.getFirst().getId());
    }

    @Test
    void testFindByActivitiesId() {
        List<BCIModuleInstance> found = bciModuleInstanceService.
                findByActivitiesId(moduleInstance.getActivities().getFirst().getId());

        assertEquals(1, found.size());
        assertEquals(moduleInstance.getId(), found.getFirst().getId());
    }

    @Test
    void testPublishEvent() {
        moduleInstance.setOutcome(OutcomeType.SUCCESSFUL);
        BCIModuleInstance updated = bciModuleInstanceService.update(moduleInstance);
        assertEquals(moduleInstance.getOutcome(), updated.getOutcome());

        assertEquals(1, applicationEvents.stream(BCIModuleInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getOutcome().equals(moduleInstance.getOutcome())).count());
    }
}
