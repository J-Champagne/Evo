package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;


/**
 * Skill model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "skill")
@JsonPropertyOrder({"id", "name", "description", "type"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Skill extends AbstractEvoModel {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="skill_id")
    private Long id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "skill_name", nullable = false, unique = true, length = 128)
    private String name;

    @JsonProperty("description")
    @Column(name = "skill_description", nullable = true, length = 250)
    private String description;

    @NotNull
    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type", nullable = false)
    private SkillType type;

    /**
     * Represents the many-to-one auto relationship for skill table. Foreign key to identify a skill required.
     */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "skill_sub_skill_id", referencedColumnName = "skill_id", nullable = true)
    @JsonProperty("subSkill")
    private Skill subSkill;

    /**
     * Represents the many-to-many Skill self-relationship.
     * This relationship is mapped to the "required_skill" join table in the database.
     * Each Skill can have multiple associated with another Skill entity.
     * The join table "required_skill" consists of the following columns:
     * "required_skill_skill_id" as a foreign key referencing "skill_id" in the "skill" table.
     * "required_skill_required_id" as a foreign key referencing required skill "skill_id" in the "skill" table.
     * "FetchType.LAZY" ensures that the associated Skill entities are loaded lazily.
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "required_skill",
            joinColumns = @JoinColumn(name = "required_skill_skill_id", referencedColumnName= "skill_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "required_skill_required_id", referencedColumnName= "skill_id", nullable = false))
    @JsonProperty("requiredSkill")
    private List<Skill> requiredSkill = new ArrayList<>();

    /**
     * Represents the many-to-many relationship between Skill and Content entities.
     * This relationship is mapped to the "skill_content" join table in the database.
     * Each Skill can have multiple associated Content entities, and each Content can be associated with multiple Skill entities.
     * The join table "skill_content" consists of the following columns:
     * - "skill_content_skill_id" as a foreign key referencing "skill_id" in the "skill" table.
     * - "skill_content_content_id" as a foreign key referencing "content_id" in the "content" table.
     * "FetchType.LAZY" ensures that the associated Content entities are loaded lazily.
     */
    @ManyToMany(
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "skill_content",
            joinColumns = @JoinColumn(name = "skill_content_skill_id", referencedColumnName="skill_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_content_content_id", referencedColumnName="content_id"))
    @JsonProperty("contents")
    private List<Content> contents = new ArrayList<>();

    @JsonProperty("develops")
    @OneToOne(mappedBy = "skill")
    private Develops develops;

    @JsonProperty("requires")
    @OneToOne(mappedBy = "skill")
    private Requires requires;

    @JsonProperty("skillComposedOfSkill")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "skill_composed_of_skill_id", referencedColumnName = "skill_id", nullable = true)
    private Skill skillComposedOfSkill;

    @OneToMany(mappedBy = "skillComposedOfSkill", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Skill> composedSkills = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "skills", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Assessment> assessments = new LinkedHashSet<>();


    public Skill() {}

    public Skill(@NotNull String name, @NotNull SkillType type) {
        this.name = name;
        this.type = type;
    }

    public Skill(@NotNull Long id, @NotNull String name, @NotNull SkillType type) {
        this(name,type);
        this.id = id;
    }


    public Requires getRequires() {
        return requires;
    }

    public void setRequires(Requires requires) {
        this.requires = requires;
    }

    public Develops getDevelops() {
        return develops;
    }

    public void setDevelops(Develops develops) {
        this.develops = develops;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillType getType() {
        return this.type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public List<Content> getContents() {
        return this.contents;
    }

    public void setContents(List<Content> contents) {
        if (contents != null) {
            if (!contents.isEmpty()) {
                this.contents = contents;
            }
        }
    }

    public void addContent(Content content) {
        if (this.contents != null) {
            this.contents.add(content);
        }
    }

    public void removeContent(Content content) {
        if (this.contents != null) {
            if (!this.contents.isEmpty()) {
                this.contents.remove(content);
            }
        }
    }

    public void setSubSkill(Skill subSkill) {
        if (subSkill != null) {
            this.subSkill = subSkill;
        }
    }

    public Skill getSubSkill() {
        return this.subSkill;
    }

    public void setRequiredSkill(List<Skill> requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public void addRequiredSkill(Skill requiredSkill) {
        if (this.requiredSkill != null) {
            this.requiredSkill.add(requiredSkill);
        }
    }

    public List<Skill> getRequiredSkill() {
        return this.requiredSkill;
    }


    public Set<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(Assessment... assessment) {
        if (assessment != null && assessment.length > 0) {
            this.assessments.addAll(List.of(assessment));
        }
    }

    public Set<Skill> getComposedSkills() {
        return composedSkills;
    }

    public void setComposedSkills(Skill... composedSkills) {
        if (composedSkills != null && composedSkills.length > 0) {
            this.composedSkills.addAll(List.of(composedSkills));
        }
    }

    public Skill getSkillComposedOfSkill() {
        return skillComposedOfSkill;
    }

    public void setSkillComposedOfSkill(Skill skillComposedOfSkill) {
        this.skillComposedOfSkill = skillComposedOfSkill;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            Skill skill = (Skill) object;
            return Objects.equals(this.getName(), skill.getName()) &&
                    Objects.equals(this.getDescription(), skill.getDescription()) &&
                    Objects.equals(this.getType(), skill.getType()) &&
                    Objects.equals(this.getContents(), skill.getContents()) &&
                    Objects.equals(this.getComposedSkills(), skill.getComposedSkills()) &&
                    Objects.equals(this.getSkillComposedOfSkill(), skill.getSkillComposedOfSkill()) &&
                    Objects.equals(this.getRequiredSkill(), skill.getRequiredSkill()) &&
                    Objects.equals(this.getSubSkill(), skill.getSubSkill()) &&
                    Objects.equals(this.getRequires(), skill.getRequires()) &&
                    Objects.equals(this.getDevelops(), skill.getDevelops()) &&
                    Objects.equals(this.getAssessments(), skill.getAssessments());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getDescription(), this.getType(), this.getSubSkill(),
                this.getComposedSkills(), this.getSkillComposedOfSkill(), this.getContents(), this.getRequiredSkill(),
                this.getRequires(), this.getDevelops(), this.getAssessments());
    }
}
