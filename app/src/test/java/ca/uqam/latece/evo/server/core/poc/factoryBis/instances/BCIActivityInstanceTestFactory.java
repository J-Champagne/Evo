package ca.uqam.latece.evo.server.core.poc.factoryBis.instances;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.poc.factoryBis.actors.ParticipantTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIActivityRecipeTestFactory;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
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
    ParticipantTestFactory patientTestFactory;

    @Autowired
    private BCIActivityRecipeTestFactory bciActivityRecipeTestFactory;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    public BCIActivityInstance getActivityInstance(Patient patient, Role role,BCIActivity bciActivity) {

        //set up BCIActivity dependencies
        Participant participant = patientTestFactory.getParticipant(patient, role);

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);


        //create and return the BCIActivity instance
        return bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, bciActivity));

    }

    public BCIActivityInstance getFirstActivityWithTrueConditions(Patient patient, Role role) {

        //set up BCIActivity dependencies
        Participant participant = patientTestFactory.getParticipant(patient, role);

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
        Participant participant = patientTestFactory.getParticipant(patient, role);

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
        Participant participant = patientTestFactory.getParticipant(patient, role);

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityRecipeTestFactory.getThirdReceipeWithTrueConditions();

        //create and return the BCIActivity instance
        return bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/02/07"),
                participants, bciActivity));
    }


}
