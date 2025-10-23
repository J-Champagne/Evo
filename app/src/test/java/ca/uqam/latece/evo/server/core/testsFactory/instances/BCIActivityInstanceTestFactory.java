package ca.uqam.latece.evo.server.core.testsFactory.instances;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.RoleService;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
import ca.uqam.latece.evo.server.core.testsFactory.receipes.BCIActivityRecipeTestFactory;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * BCIActivityTestFactory is a Spring (@Component) factory used to create
 * preconfigured BCIActivityInstance objects for testing purposes.
 * <p>
 * Each method returns a BCIActivityInstance with specific conditions ("true" or "false") and associated BCIActivity data.
 *
 * @author Mohamed Djawad Abi Ayad.
 * @version 1.0
 */
@Component
public class BCIActivityInstanceTestFactory {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private BCIActivityRecipeTestFactory bciActivityRecipeTestFactory;


    public BCIActivityInstance getFirstActivityWithTrueConditions(Patient patient, Role role) {

        //set up BCIActivity dependencies
        Participant participant = participantService.create(new Participant(role, patient));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityRecipeTestFactory.getFirstReceipeWithTrueConditions();

        //create and return the BCIActivity instance
        return bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, bciActivity));

    }

    public BCIActivityInstance getSecondActivityWithTrueConditions(Patient patient, Role role) {

        //set up BCIActivity dependencies
        Participant participant = participantService.create(new Participant(role, patient));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityRecipeTestFactory.getSecondReceipeWithTrueConditions();

        //create and return the BCIActivity instance
        return bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/02/07"),
                participants, bciActivity));
    }

    public BCIActivityInstance getThirdActivityWithTrueConditions(Patient patient, Role role) {

        //set up BCIActivity dependencies
        Participant participant = participantService.create(new Participant(role, patient));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityRecipeTestFactory.getThirdReceipeWithTrueConditions();

        //create and return the BCIActivity instance
        return bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/02/07"),
                participants, bciActivity));
    }


}
