package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.Scale;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "assessment")
@JsonPropertyOrder({"id", "name", "description", "type", "preconditions", "postconditions", "assessee", "assessor",
        "scale", "scoringFunction", "selfRelationship"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@PrimaryKeyJoinColumn(name="assessment_id", referencedColumnName = "bci_activity_id")
public class Assessment extends BCIActivity {

    @JsonProperty("assessee")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_assessee_role_id", nullable = false)
    private Role assessmentAssesseeRole;

    @JsonProperty("assessor")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_assessor_role_id", nullable = false)
    private Role assessmentAssessorRole;

    @JsonProperty("scale")
    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_scale", length = 6)
    private Scale assessmentScale;

    @JsonProperty("scoringFunction")
    @Size(max = 250)
    @Column(name = "assessment_scoring_function", length = 250)
    private String assessmentScoringFunction;

    @JsonProperty("selfRelationship")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_self_relationship_id")
    private Assessment assessmentSelfRelationship;

    @OneToMany(mappedBy = "assessmentSelfRelationship", orphanRemoval = true, cascade=CascadeType.ALL)
    private Set<Assessment> assessments = new LinkedHashSet<>();

    @JsonProperty("skills")
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "assessment_skill",
            joinColumns = @JoinColumn(name = "assessment_skill_assessment_id", referencedColumnName = "assessment_id"),
            inverseJoinColumns = @JoinColumn(name = "assessment_skill_skill_id", referencedColumnName = "skill_id"))
    private Set<Skill> skills = new LinkedHashSet<>();

    public Assessment() {}

    public Assessment(Role assessmentAssesseeRole, Role assessmentAssessorRole, Skill... skill) {
        this.addSkill(skill);
        this.assessmentAssesseeRole = assessmentAssesseeRole;
        this.assessmentAssessorRole = assessmentAssessorRole;
    }

    private void addSkill(Skill... skill) {
        if (skill != null && skill.length > 0) {
            this.skills.addAll(List.of(skill));
        }
    }

    public void setSkills(Skill... skills) {
        this.addSkill(skills);
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public Role getAssessmentAssesseeRole() {
        return assessmentAssesseeRole;
    }

    public void setAssessmentAssesseeRole(Role assessmentAssesseeRole) {
        this.assessmentAssesseeRole = assessmentAssesseeRole;
    }

    public Role getAssessmentAssessorRole() {
        return assessmentAssessorRole;
    }

    public void setAssessmentAssessorRole(Role assessmentAssessorRole) {
        this.assessmentAssessorRole = assessmentAssessorRole;
    }

    public Scale getAssessmentScale() {
        return assessmentScale;
    }

    public void setAssessmentScale(Scale assessmentScale) {
        this.assessmentScale = assessmentScale;
    }

    public String getAssessmentScoringFunction() {
        return assessmentScoringFunction;
    }

    public void setAssessmentScoringFunction(String assessmentScoringFunction) {
        this.assessmentScoringFunction = assessmentScoringFunction;
    }

    public Assessment getAssessmentSelfRelationship() {
        return assessmentSelfRelationship;
    }

    public void setAssessmentSelfRelationship(Assessment assessmentSelfRelationship) {
        this.assessmentSelfRelationship = assessmentSelfRelationship;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(Assessment... assessments) {
        if (assessments != null && assessments.length > 0) {
            this.assessments.addAll(List.of(assessments));
        }
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            Assessment assessment = (Assessment) object;
            return Objects.equals(this.getAssessmentAssesseeRole(), assessment.getAssessmentAssesseeRole()) &&
                    Objects.equals(this.getAssessmentAssessorRole(), assessment.getAssessmentAssessorRole()) &&
                    Objects.equals(this.getAssessmentScale(), assessment.getAssessmentScale()) &&
                    Objects.equals(this.getAssessmentScoringFunction(), assessment.getAssessmentScoringFunction()) &&
                    Objects.equals(this.getAssessmentSelfRelationship(), assessment.getAssessmentSelfRelationship()) &&
                    Objects.equals(this.getSkills(), assessment.getSkills()) &&
                    Objects.equals(this.getAssessments(), assessment.getAssessments());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getAssessmentAssesseeRole(), this.getAssessmentAssessorRole(),
                this.getAssessmentScale(), this.getAssessmentScoringFunction(), this.getAssessmentSelfRelationship(),
                this.getSkills(), this.getAssessments());
    }

}