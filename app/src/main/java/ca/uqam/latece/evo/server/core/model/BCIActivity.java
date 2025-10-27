package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BCIActivity model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "bci_activity")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonPropertyOrder({"id", "name", "description", "type", "preconditions", "postconditions"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BCIActivity extends Activity {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bci_activity_id")
    private Long id;

    @NotNull
    @Column(name = "bci_activity_name", nullable = false, length = 256)
    private String name;

    @NotNull
    @Column(name = "bci_activity_description", nullable = false, length = 256)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bci_activity_type", nullable = false, length = 128)
    private ActivityType type;

    @Column(name = "bci_activity_preconditions", nullable = true, length = 256)
    private String preconditions;

    @Column(name = "bci_activity_postconditions", nullable = true, length = 256)
    private String postconditions;

    @JsonIgnore
    @OneToMany(mappedBy = "bciActivityDevelops", orphanRemoval = true, targetEntity = Develops.class)
    private List<Develops> developsBCIActivity = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bciActivityRequires", orphanRemoval = true, targetEntity = Requires.class)
    private List<Requires> requiresBCIActivities = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "bci_activity_content",
            joinColumns = @JoinColumn(name = "bci_activity_content_bci_activity_id", referencedColumnName="bci_activity_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_activity_content_content_id", referencedColumnName="content_id")
    )
    private List<Content> contentBCIActivities = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "bci_activity_role",
            joinColumns = @JoinColumn(name = "bci_activity_role_bci_activity_id", referencedColumnName="bci_activity_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_activity_role_role_id", referencedColumnName="role_id")
    )
    private List<Role> parties = new ArrayList<>();

    @OneToMany(mappedBy = "bciActivityComposedOf", orphanRemoval = true, targetEntity = ComposedOf.class)
    private List<ComposedOf> composedOfList = new ArrayList<>();

    public BCIActivity() {}

    public BCIActivity(@NotNull String name, @NotNull String description, @NotNull ActivityType type, @NotNull String preconditions,
                       @NotNull String postconditions) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
    }

    public BCIActivity(@NotNull String name, @NotNull String description, @NotNull ActivityType type, @NotNull String preconditions,
                       @NotNull String postconditions, @NotNull Develops develop, @NotNull Role... parties) {
        this(name, description, type, preconditions, postconditions);
        this.developsBCIActivity.add(develop);

        if(parties.length > 0) {
            this.parties.addAll(List.of(parties));
        }
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public ActivityType getType() {
        return this.type;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getPreconditions() {
        return this.preconditions;
    }

    public void setPostconditions(String postconditions) {
        this.postconditions = postconditions;
    }

    public String getPostconditions() {
        return this.postconditions;
    }

    public void setDevelops(List<Develops> develops) {
        ObjectValidator.validateObject(develops);

        if(!develops.isEmpty()) {
            this.developsBCIActivity = develops;
        }
    }

    public List<Develops> getDevelops() {
        return this.developsBCIActivity;
    }

    public void setRequires(List<Requires> requires) {
        ObjectValidator.validateObject(requires);
        if(!requires.isEmpty()) {
            this.requiresBCIActivities = requires;
        }
    }

    public List<Requires> getRequires() {
        return this.requiresBCIActivities;
    }

    public void addRequires(Requires requires) {
        ObjectValidator.validateObject(requires);
        this.requiresBCIActivities.add(requires);
    }

    public void removeRequires(Requires requires) {
        ObjectValidator.validateObject(requires);

        if(!this.requiresBCIActivities.isEmpty()) {
            this.requiresBCIActivities.remove(requires);
        }
    }

    public void addContent(Content content) {
        ObjectValidator.validateObject(content);
        this.contentBCIActivities.add(content);
    }

    public void removeContent(Content content) {
        List<Content> contentList = new ArrayList<>();
        ObjectValidator.validateObject(content);
        contentList.add(content);
        this.removeAllContent(contentList);
    }

    public void removeAllContent(List<Content> content){
        ObjectValidator.validateObject(content);

        if(!this.contentBCIActivities.isEmpty()) {
            this.contentBCIActivities.removeAll(content);
        }
    }

    public void setContent(List<Content> content) {
        ObjectValidator.validateObject(content);
        if(!content.isEmpty()) {
            this.contentBCIActivities = content;
        }
    }

    public List<Content> getContent() {
        return this.contentBCIActivities;
    }

    public void addParty(Role... role) {
        ObjectValidator.validateObject(role);

        if(role.length > 0) {
            this.parties.addAll(List.of(role));
        }
    }

    public void addDevelops(Develops develops) {
        ObjectValidator.validateObject(develops);
        this.developsBCIActivity.add(develops);
    }

    public void removeDevelops(Develops develops) {
        ObjectValidator.validateObject(develops);

        if(!this.developsBCIActivity.isEmpty()) {
            this.developsBCIActivity.remove(develops);
        }
    }

    public void removeParty(Role role) {
        List<Role> roleList = new ArrayList<>();
        ObjectValidator.validateObject(role);
        roleList.add(role);

        this.removeAllParties(roleList);
    }

    public void removeAllParties(List<Role> roles) {
        ObjectValidator.validateObject(roles);

        if(!this.parties.isEmpty()) {
            this.parties.removeAll(roles);
        }
    }

    public List<Role> getParties() {
        return this.parties;
    }

    public void setParties(List<Role> role) {
        ObjectValidator.validateObject(role);
        if(!role.isEmpty()) {
            this.parties = role;
        }
    }

    public List<ComposedOf> getComposedOf() {
        return this.composedOfList;
    }

    public void setComposedOf(ComposedOf... composedOf) {
        if(composedOf != null && composedOf.length > 0) {
            this.composedOfList.addAll(List.of(composedOf));
        }
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BCIActivity bciActivity = (BCIActivity) object;
            return Objects.equals(this.getName(), bciActivity.getName()) &&
                    Objects.equals(this.getDescription(), bciActivity.getDescription()) &&
                    Objects.equals(this.getComposedOf(), bciActivity.getComposedOf()) &&
                    Objects.equals(this.getContent(), bciActivity.getContent()) &&
                    Objects.equals(this.getDevelops(), bciActivity.getDevelops()) &&
                    Objects.equals(this.getPostconditions(), bciActivity.getPostconditions()) &&
                    Objects.equals(this.getPreconditions(), bciActivity.getPreconditions()) &&
                    Objects.equals(this.getRequires(), bciActivity.getRequires()) &&
                    Objects.equals(this.getParties(), bciActivity.getParties()) &&
                    Objects.equals(this.getType(), bciActivity.getType());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getDescription(), this.getComposedOf(),this.getContent(),
                this.getDevelops(), this.getPostconditions(), this.getPreconditions(), this.getRequires(), this.getParties(),
                this.getType());
    }

}
