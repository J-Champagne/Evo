package ca.uqam.latece.evo.server.core.poc.factoryBis.actors;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipantTestFactory {

    @Autowired
    private ParticipantService participantService;

    public Participant getParticipant(Patient patient, Role role) {
        return participantService.create(new Participant(role, patient));
    }

}
