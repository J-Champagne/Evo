package ca.uqam.latece.evo.server.core.poc.factoryBis.actors;


import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientTestFactory {

    @Autowired
    private PatientService patientService;

    public Patient getPatient() {
        return patientService.findById(1L);
    }
}
