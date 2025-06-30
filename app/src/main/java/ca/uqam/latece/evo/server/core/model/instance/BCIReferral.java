package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BCIReferral instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_referral")
@Transactional
public class BCIReferral extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_referral_id")
    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "bci_referral_date")
    private LocalDate date;

    @NotNull
    @Column(name = "bci_referral_reason")
    private String reason;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bci_referral_patient", referencedColumnName = "patient_id")
    private Patient patient;

    @NotNull
    @OneToOne
    @JoinColumn(name = "bci_referral_patient_assessment", referencedColumnName = "patient_assessment_id")
    private PatientAssessment patientAssessment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bci_referral_professional", referencedColumnName = "healthcare_professional_id")
    private HealthCareProfessional referringProfessional;

    @ManyToOne
    @JoinColumn(name = "bci_referral_interventionist", referencedColumnName = "healthcare_professional_id")
    private HealthCareProfessional behaviorChangeInterventionist;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bci_referral_interventions",
            joinColumns = @JoinColumn(name = "bci_referral_interventions_referral_id", referencedColumnName="bci_referral_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_referral_interventions_bci_id", referencedColumnName="bci_instance_id"))
    private List<BehaviorChangeInterventionInstance> interventions;

    public BCIReferral() {
        this.interventions = new ArrayList<>();
    }

    public BCIReferral(@NotNull String reason, @NotNull Patient patient, @NotNull PatientAssessment patientAssessment,
                                       @NotNull HealthCareProfessional referringProfessional) throws IllegalArgumentException {
        if (!patient.equals(patientAssessment.getPatient())) {
            throw new IllegalArgumentException("The Patient is not the same as the patient from PatientAssessment");
        }
        this.date = LocalDate.now();
        this.patient = patient;
        this.reason = reason;
        this.patientAssessment = patientAssessment;
        this.referringProfessional = referringProfessional;
    }

    public BCIReferral(@NotNull String reason, @NotNull Patient patient, @NotNull PatientAssessment patientAssessment,
        @NotNull HealthCareProfessional referringProfessional, HealthCareProfessional behaviorChangeInterventionist)
            throws IllegalArgumentException {
        this(reason, patient, patientAssessment, referringProfessional);
        this.behaviorChangeInterventionist = behaviorChangeInterventionist;
    }

    public BCIReferral(@NotNull String reason, @NotNull Patient patient, @NotNull PatientAssessment patientAssessment,
                       @NotNull HealthCareProfessional referringProfessional, HealthCareProfessional behaviorChangeInterventionist,
                       List<BehaviorChangeInterventionInstance> interventions) throws IllegalArgumentException {
        this(reason, patient, patientAssessment, referringProfessional, behaviorChangeInterventionist);
        this.interventions = interventions;
    }

    public BCIReferral(@NotNull String reason, @NotNull Patient patient, @NotNull PatientAssessment patientAssessment,
                       @NotNull HealthCareProfessional referringProfessional, List<BehaviorChangeInterventionInstance> interventions)
            throws IllegalArgumentException {
        this(reason, patient, patientAssessment, referringProfessional);
        this.interventions = interventions;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PatientAssessment getPatientAssessment() {
        return patientAssessment;
    }

    public void setPatientAssessment(PatientAssessment patientAssessment) {
        this.patientAssessment = patientAssessment;
    }

    public HealthCareProfessional getReferringProfessional() {
        return referringProfessional;
    }

    public void setReferringProfessional(HealthCareProfessional referringProfessional) {
        this.referringProfessional = referringProfessional;
    }

    public HealthCareProfessional getBehaviorChangeInterventionist() {
        return behaviorChangeInterventionist;
    }

    public void setBehaviorChangeInterventionist(HealthCareProfessional behaviorChangeInterventionist) {
        this.behaviorChangeInterventionist = behaviorChangeInterventionist;
    }

    public List<BehaviorChangeInterventionInstance> getInterventions() {
        return interventions;
    }

    public void setBehaviorChangeInterventionInstances(List<BehaviorChangeInterventionInstance> interventions) {
        if (interventions != null && !interventions.isEmpty()) {
            this.interventions.addAll(interventions);
        }
    }
}
