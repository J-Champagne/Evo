/***********************************************************************************************************************
The provided SQL script defines a relational database structure involving multiple tables part of Evo+ implementation.
 - since 22.01.2025.
 - version 1.0
 - author Edilton Lima dos Santos.
***********************************************************************************************************************/


/***********************************************************************************************************************
role table: This table stores information about roles in the system (e.g., admin, user, etc.).
- Columns:
  - role_id: A unique identifier for each role. It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - role_name: The name of the role (e.g., "Admin", "e-Facilitor").
  - role_description: A description of the Role.
- Constraints:
  - role_pkey: Declares role_id as the primary key — ensuring each row has a unique identifier.
  - role_role_name_ukey: Ensures that role_name is unique, meaning no duplicate role names can exist.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS role (
    role_id BIGSERIAL NOT NULL,
    role_name VARCHAR(128) NOT NULL,
    role_description VARCHAR(250),
    CONSTRAINT role_pkey PRIMARY KEY (role_id),
    CONSTRAINT role_role_name_ukey UNIQUE (role_name)
);

/***********************************************************************************************************************
actor table: This table stores information about actors (users or individuals) in the system.
- Columns:
  - actor_id: A unique identifier for each actor, auto-incremented.
  - actor_name: The name of the actor. It's required (NOT NULL).
  - actor_email: The email of the actor, which is also required and must be unique.
  - actor_contactInformation: The contact information of the actor. It's required (NOT NULL).
- Constraints:
  - actor_pkey: Establishes actor_id as the primary key.
  - actor_actor_email_ukey: Ensures that actor_email is unique.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS actor (
    actor_id BIGSERIAL NOT NULL,
    actor_name VARCHAR NOT NULL,
    actor_email VARCHAR NOT NULL,
    actor_contact_information VARCHAR NOT NULL,
    CONSTRAINT actor_pkey PRIMARY KEY (actor_id),
    CONSTRAINT actor_actor_email_ukey UNIQUE (actor_email)
);

/***********************************************************************************************************************
healthcare_professional table: This table stores information about health care professionals (a subtype of Actors) in the system.
- Columns:
  - healthcare_professional_id: A unique identifier for each healthcareprofessional, auto-incremented.
  - healthcare_professional_position: The position of the healthcareprofessional.
  - healthcare_professional_affiliation: The affiliation of the healthcareprofessional.
  - healthcare_professional_specialties: The specialties of the healthcareprofessional.
  - healthcare_professional_actor_id: A foreign key referencing the actor_id in the actor table. It links a healthcareprofessional to an actor.
- Constraints:
  - healthcare_professional_pkey: Establishes healthcare_professional_id as the primary key.
  - healthcare_professional_fkey: Ensures that healthcare_professional_actor_id references a valid actor in the actor table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS healthcare_professional (
    healthcare_professional_id BIGINT NOT NULL,
    healthcare_professional_position VARCHAR,
    healthcare_professional_affiliation VARCHAR,
    healthcare_professional_specialties VARCHAR,
    CONSTRAINT healthcare_professional_pkey PRIMARY KEY (healthcare_professional_id),
    CONSTRAINT healthcare_professional_actor_fkey FOREIGN KEY (healthcare_professional_id) REFERENCES actor (actor_id)
);

/***********************************************************************************************************************
patient_medicalfile table: This table stores information about patient medical files in the system.
- Columns:
  - patient_medicalfile_id: A unique identifier for each PatientMedicalFile, auto-incremented.
  - patient_medicalfile_date: The date of creation of the PatientMedicalFile.
  - patient_medicalfile_medicalhistory: The medical history of the PatientMedicalFile.
- Constraints:
  - patient_medicalfile_pkey: Establishes patient_medicalfile_id as the primary key.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS patient_medicalfile (
    patient_medicalfile_id BIGSERIAL NOT NULL,
    patient_medicalfile_date DATE NOT NULL,
    patient_medicalfile_medicalhistory VARCHAR,
    CONSTRAINT patient_medicalfile_pkey PRIMARY KEY (patient_medicalfile_id)
);

/***********************************************************************************************************************
patient table: This table stores information about patients (a subtype of Actors).
- Columns:
  - patient_id: A unique identifier for each patient, auto-incremented.
  - patient_birthdate: The birthdate of the patient.
  - patient_occupation: The occupation of the patient.
  - patient_address: The address of the patient.
  - patient_actor_id: A foreign key referencing the actor_id in the actor table.
- Constraints:
  - patient_pkey: Establishes patient_id as the primary key.
  - patient_fkey: Ensures that patient_actor_id references a valid actor in the actor table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS patient (
    patient_id BIGINT NOT NULL,
    patient_birthdate VARCHAR,
    patient_occupation VARCHAR,
    patient_address VARCHAR,
    patient_patient_medical_file_id BIGINT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (patient_id),
    CONSTRAINT patient_actor_fkey FOREIGN KEY (patient_id) REFERENCES actor (actor_id),
    CONSTRAINT patient_patient_medicalfile_fkey FOREIGN KEY (patient_patient_medical_file_id) REFERENCES patient_medicalfile (patient_medicalfile_id)
);

/***********************************************************************************************************************
participant table: This table stores information about participants.
- Columns:
  - participant_id: A unique identifier for each participant, auto-incremented.
  - participant_actor_id: The actor of the participant, can be any subclass of Actor.
  - participant_role_id: The role of the participant.
- Constraints:
  - participant_pkey: Establishes participant_id as the primary key.
  - participant_actor_id_fkey: Ensures that participant_actor references a valid actor in the actor table.
  - participant_role_fkey: Ensures that participant_role references a valid role in the role table.
  ***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS participant (
    participant_id BIGSERIAL NOT NULL,
    participant_role_id BIGINT NOT NULL,
    participant_actor_id BIGINT NOT NULL,
    CONSTRAINT participant_pkey PRIMARY KEY (participant_id),
    CONSTRAINT participant_role_id_fkey FOREIGN KEY (participant_role_id) REFERENCES role (role_id),
    CONSTRAINT participant_actor_id_fkey FOREIGN KEY (participant_actor_id) REFERENCES actor (actor_id)
);

/***********************************************************************************************************************
patient assessment table: This table stores information about patient assessments.
- Columns:
  - patient_assessment_id: A unique identifier for each patient assessment, auto-incremented.
  - patient_assessment_date: The date of creation of the patient assessment.
  - patient_assessment_assessment: The assessment of the patient.
  - patient_assessment_patient_id: A foreign key referencing the patient that is being assessed.
- Constraints:
  - patient_assessment_pkey: Establishes patient_assessment_id as the primary key.
  - patient_assessment_patient_fkey: Ensures that patient_assessment_patient_id references a valid patient in the patient table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS patient_assessment (
    patient_assessment_id BIGSERIAL NOT NULL,
    patient_assessment_date DATE NOT NULL,
    patient_assessment_assessment VARCHAR NOT NULL,
    patient_assessment_patient BIGINT NOT NULL,
    CONSTRAINT patient_assessment_pkey PRIMARY KEY (patient_assessment_id),
    CONSTRAINT patient_assessment_patient_fkey FOREIGN KEY (patient_assessment_patient) REFERENCES patient (patient_id)
);

/***********************************************************************************************************************
skill table: This table stores details about skills that actors may hold or require.
- Columns:
  - skill_id: A unique identifier for each skill, auto-incremented.
  - skill_name: The name of the skill. It's required and must be unique.
  - skill_description: A description of the skill.
  - skill_type: An optional string to classify the skill.
  - skill_sub_skill_id: An optional foreign key used to identify the dependence between skill and subskill.
    It links a skill to another skill.
  - skill_composed_of_skill_id: An optional foreign key used to identify the dependence between skill and
  its composition with other Skill.
- Constraints:
  - skill_pkey: Establishes skill_id as the primary key.
  - skill_name_ukey: Ensures that skill_name is unique.
  - skill_sub_skill_fkey: Ensures that skill_sub_skill_id references a valid skill (sub skill) in the skill table.
  - skill_composed_of_skill_fkey: Ensures that skill_composed_of_skill_id references a valid skill (composed of)
  zin the skill table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS skill (
     skill_id BIGSERIAL NOT NULL,
     skill_name VARCHAR(128) NOT NULL,
     skill_description VARCHAR(250) NULL,
     skill_type VARCHAR(10) NOT NULL,
     skill_sub_skill_id BIGINT NULL,
     skill_composed_of_skill_id BIGINT NULL,
     CONSTRAINT skill_pkey PRIMARY KEY (skill_id),
     CONSTRAINT skill_name_ukey UNIQUE (skill_name),
     CONSTRAINT skill_sub_skill_fkey FOREIGN KEY (skill_sub_skill_id) REFERENCES skill (skill_id),
     CONSTRAINT skill_composed_of_skill_fkey FOREIGN KEY (skill_composed_of_skill_id) REFERENCES skill (skill_id) ON DELETE SET NULL
);

/***********************************************************************************************************************
required_skill table: This is a junction table that represents a many-to-many Skill self-relationship. It links a skill
  corresponds to which required Skill(s).
- Columns:
  - required_skill_skill_id, required_skill_required_id: A unique identifier for each required skill, is a self-foreign key.
  - required_skill_skill_id: A foreign key used to identify the main Skill.
  - required_skill_required_id: A foreign key used to identify the required Skill. It links a Skill with the required Skill.
- Constraints:
  - required_skill_pkey: Establishes skill_id as the primary key.
  - required_skill_fkey: Ensures that required_skill_requires_id references a valid required skill in the Skill table.
  - required_skill_required_fkey: Ensures that required_skill_required_id references a valid required skill in the Skill table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS required_skill (
    required_skill_skill_id BIGINT NOT NULL,
    required_skill_required_id BIGINT NOT NULL,
    CONSTRAINT required_skill_pkey PRIMARY KEY (required_skill_skill_id, required_skill_required_id),
    CONSTRAINT required_skill_fkey FOREIGN KEY (required_skill_skill_id) REFERENCES skill (skill_id),
    CONSTRAINT required_skill_required_fkey FOREIGN KEY (required_skill_required_id) REFERENCES skill (skill_id)
);

/***********************************************************************************************************************
content table: This table stores details about content.

- Columns:
  - content_id: A unique identifier for each piece of content, auto-incremented.
  - content_name: The name of the content. It's required and must be unique.
  - content_description: A description of the content. It's required.
  - content_type: An optional string specifying the type of content.
- Constraints**:
  - content_pkey: Establishes content_id as the primary key.
  - content_content_name_ukey: Ensures that content_name is unique.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS content (
    content_id BIGSERIAL NOT NULL,
    content_name VARCHAR(128) NOT NULL,
    content_description VARCHAR(256) NOT NULL,
    content_type VARCHAR(256) NULL,
    CONSTRAINT content_pkey PRIMARY KEY (content_id),
    CONSTRAINT content_content_name_ukey UNIQUE (content_name)
);

/***********************************************************************************************************************
skill_content table: This is a junction table that represents a many-to-many relationship between skill and content.
It links which content corresponds to which skill(s).
- Columns:
  - skill_content_id: A unique identifier for each mapping, auto-incremented.
  - skill_content_skill_id: A foreign key referencing a skill_id in the skill table.
  - skill_content_content_id: A foreign key referencing a content_id in the content table.
- Constraints:
  - skill_content_pkey: Primary key for this table.
  - skill_content_skill_fkey: Ensures that skill_content_skill_id references a valid record in the skill table.
  - skill_content_content_fkey: Ensures that skill_content_content_id references a valid record in the content table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS skill_content (
    skill_content_id BIGSERIAL NOT NULL,
    skill_content_skill_id BIGINT NOT NULL,
    skill_content_content_id BIGINT NOT NULL,
    CONSTRAINT skill_content_pkey PRIMARY KEY (skill_content_id),
    CONSTRAINT skill_content_skill_fkey FOREIGN KEY (skill_content_skill_id) REFERENCES skill (skill_id),
    CONSTRAINT skill_content_content_fkey FOREIGN KEY (skill_content_content_id) REFERENCES content (content_id)
);

/***********************************************************************************************************************
bci_activity table: This table stores details about Behavior Change Technique Intervention Activity.
- Columns:
  - bci_activity_id: A unique identifier for each Behavior Change Technique Intervention Activity, auto-incremented.
  - bci_activity_name: The name of the Behavior Change Technique Intervention Activity. It's required and must be unique.
  - bci_activity_description: An optional description of the Behavior Change Technique Intervention Activity.
  - bci_activity_type: An optional string to classify the Behavior Change Technique Intervention Activity.
  - bci_activity_preconditions: An optional string to define a preconditions of Behavior Change Technique
    Intervention Activity.
  - bci_activity_postconditions: An optional string to define a post-conditions of Behavior Change Technique
    Intervention Activity.
  - bci_activity_type_class: Used by the Hibernate to map the subclass of BCIActivity.
- Constraints:
  - bci_activity_pkey: Establishes bci_activity_id as the primary key.
  - bci_activity_name_ukey: Ensures that bci_activity_name is unique.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity (
    bci_activity_id BIGSERIAL NOT NULL,
    bci_activity_name VARCHAR(256) NOT NULL,
    bci_activity_description VARCHAR(256) NULL,
    bci_activity_type VARCHAR(128) NULL,
    bci_activity_preconditions VARCHAR(256) NULL,
    bci_activity_postconditions VARCHAR(256) NULL,
    bci_activity_type_class VARCHAR(128),
    CONSTRAINT bci_activity_pkey PRIMARY KEY (bci_activity_id),
    CONSTRAINT bci_activity_name_ukey UNIQUE (bci_activity_name)
);

/**********************************************************************************************************************
    activity_instance table: Holds data for the instances of activity (ActivityInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS activity_instance (
    activity_instance_id BIGSERIAL NOT NULL,
    activity_instance_status VARCHAR(12) NULL,
    activity_instance_entry_date DATE NULL,
    activity_instance_exit_date DATE NULL,
    CONSTRAINT activity_instance_pkey PRIMARY KEY (activity_instance_id)
);

/**********************************************************************************************************************
    bci_activity_instance table: Holds data for the instances of bci_activity (BCIActivityInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity_instance (
    bci_activity_instance_id BIGINT NOT NULL,
    CONSTRAINT bci_activity_instance_pkey PRIMARY KEY (bci_activity_instance_id),
    CONSTRAINT bci_activity_instance_activity_instance_fkey FOREIGN KEY (bci_activity_instance_id) REFERENCES activity_instance (activity_instance_id)
);

/***********************************************************************************************************************
    bci_activity_instance_participants table: Junction table for the many-to-many relationship
        between bci_activity_instance and participant.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity_instance_participants (
    bci_activity_instance_participants_bci_activity_instance_id BIGINT NOT NULL,
    bci_activity_instance_participants_participant_id BIGINT NOT NULL,
    CONSTRAINT  bci_activity_instance_participants_pk PRIMARY KEY (bci_activity_instance_participants_bci_activity_instance_id, bci_activity_instance_participants_participant_id),
    CONSTRAINT  bci_activity_instance_participants_bci_activity_fkey FOREIGN KEY (bci_activity_instance_participants_bci_activity_instance_id) REFERENCES bci_activity_instance (bci_activity_instance_id),
    CONSTRAINT  bci_activity_instance_participants_participant_fkey FOREIGN KEY (bci_activity_instance_participants_participant_id) REFERENCES participant (participant_id)
);

/***********************************************************************************************************************
goal_setting table: This table stores details about Goal Setting.
- Columns:
  - goal_setting_id: A unique identifier for each Goal Setting. Also works as a foreign key referencing a bci_activity_id
  in the bci_activity table used by the Hibernate to map the subclass of BCIActivity.
  - goal_setting_concerns_bci_activity_id: This foreign key is used to mapping the relationship between a goal setting
  (goal_setting) and bci activity (bci_activity). Consequently, the goal_setting_concerns_bci_activity_id key referencing
  a bci_activity_id in the bci_activity table.
- Constraints:
  - goal_setting_pkey: Primary key for this table.
  - goal_setting_fkey: This constraint is used by the Hibernate to map the subclass of BCIActivity.
  - goal_setting_concerns_fkey: Ensures that goal_setting_concerns_bci_activity_id references a valid record in the bci_activity table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS goal_setting (
    goal_setting_id BIGINT NOT NULL,
    goal_setting_concerns_bci_activity_id BIGINT NULL,
    CONSTRAINT goal_setting_pkey PRIMARY KEY (goal_setting_id),
    CONSTRAINT goal_setting_fkey FOREIGN KEY (goal_setting_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT goal_setting_concerns_fkey FOREIGN KEY (goal_setting_concerns_bci_activity_id) REFERENCES bci_activity (bci_activity_id)
);

/***********************************************************************************************************************
goal_setting_instance table: This table stores details about Goal Setting Instance.
- Columns:
  - goal_setting_instance_id: A unique identifier for each Goal Setting Instance. Also works as a foreign key referencing
  bci_activity_instance_id in the bci_activity_instance table used by the Hibernate to map the subclass of BCIActivityInstance.
  - goal_setting_instance_bci_concerns_instance_id: This foreign key is used to mapping the relationship between a goal
  setting instance (goal_setting_instance) and bci activity instance (bci_activity_instance). Consequently, the
  goal_setting_instance_bci_concerns_instance_id key referencing a bci_activity_instance_id in the bci_activity_instance table.
  - goal_setting_instance_goal_setting_id: This foreign key is used to mapping the relationship between a goal
  setting instance (goal_setting_instance) and goal setting (goal_setting).
- Constraints:
  - goal_setting_instance_pkey: Primary key for this table.
  - goal_setting_instance_fkey: This constraint is used by the Hibernate to map the subclass of BCIActivityInstance.
  - goal_setting_instance_bci_concerns_instance_fkey: Ensures that goal_setting_instance_bci_concerns_instance_id references a valid
  record in the bci_activity_instance table.
  - goal_setting_instance_goal_setting_fkey: Ensures that goal_setting_instance_goal_setting_id references a valid record in the goal_setting_id table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS goal_setting_instance (
    goal_setting_instance_id BIGINT NOT NULL,
    goal_setting_instance_bci_concerns_instance_id BIGINT NULL,
    goal_setting_instance_goal_setting_id BIGINT NOT NULL,
    CONSTRAINT goal_setting_instance_pkey PRIMARY KEY (goal_setting_instance_id),
    CONSTRAINT goal_setting_instance_fkey FOREIGN KEY (goal_setting_instance_id) REFERENCES bci_activity_instance (bci_activity_instance_id),
    CONSTRAINT goal_setting_instance_bci_concerns_instance_fkey FOREIGN KEY (goal_setting_instance_bci_concerns_instance_id) REFERENCES bci_activity_instance (bci_activity_instance_id),
    CONSTRAINT goal_setting_instance_goal_setting_fkey FOREIGN KEY (goal_setting_instance_goal_setting_id) REFERENCES goal_setting (goal_setting_id)
);

/***********************************************************************************************************************
reporting table: This table stores details about reporting.
- Columns:
  - reporting_id: A unique identifier for each reporting. Also works as a foreign key referencing a bci_activity_id in
  the bci_activity table used by the Hibernate to map the subclass of BCIActivity.
  - reporting_frequency: The report frequency.
  - reporting_concerns_bci_activity_id: This foreign key is used to mapping the relationship between a reporting
  (reporting) and bci activity (bci_activity). Consequently, the reporting_concerns_bci_activity_id key referencing
  a bci_activity_id in the bci_activity table.
- Constraints:
  - reporting_pkey: Primary key for this table.
  - reporting_fkey: This constraint is used by the Hibernate to map the subclass of BCIActivity.
  - reporting_concerns_fkey: Ensures that reporting_concerns_bci_activity_id references a valid record in the bci_activity table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS reporting (
    reporting_id BIGINT NOT NULL,
    reporting_concerns_bci_activity_id BIGINT NULL,
    reporting_frequency VARCHAR(256) NOT NULL,
    CONSTRAINT reporting_pkey PRIMARY KEY (reporting_id),
    CONSTRAINT reporting_fkey FOREIGN KEY (reporting_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT reporting_concerns_fkey FOREIGN KEY (reporting_concerns_bci_activity_id) REFERENCES bci_activity (bci_activity_id)
);

/***********************************************************************************************************************
interaction table: This table stores details about interaction.
- Columns:
  - interaction_id: A unique identifier for each interaction. Also works as a foreign key referencing a bci_activity_id in
  the bci_activity table used by the Hibernate to map the subclass of BCIActivity.
  - interaction_mode: The interaction mode.
  - interaction_initiator_role_id: The role id used to initiate the interaction.
  - interaction_medium: The interaction medium.
- Constraints:
  - interaction_pkey: Primary key for this table.
  - interaction_fkey: This constraint is used by the Hibernate to map the subclass of BCIActivity.
  - interaction_initiator_role_fkey: Ensures that interaction_initiator_role_id references a valid record in the role table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS interaction (
    interaction_id BIGINT NOT NULL,
    interaction_mode VARCHAR(12) NOT NULL,
    interaction_initiator_role_id BIGINT NOT NULL,
    interaction_medium1 VARCHAR(9) NOT NULL,
    interaction_medium2 VARCHAR(9) NULL,
    interaction_medium3 VARCHAR(9) NULL,
    interaction_medium4 VARCHAR(9) NULL,
    CONSTRAINT interaction_pkey PRIMARY KEY (interaction_id),
    CONSTRAINT interaction_fkey FOREIGN KEY (interaction_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT interaction_initiator_role_fkey FOREIGN KEY (interaction_initiator_role_id) REFERENCES role (role_id)
);

/***********************************************************************************************************************
interaction_instance table: Holds data for the instances of interaction (InteractionInstance class).
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS interaction_instance (
    interaction_instance_id BIGINT NOT NULL,
    CONSTRAINT interaction_instance_pkey PRIMARY KEY (interaction_instance_id),
    CONSTRAINT interaction_instance_fkey FOREIGN KEY (interaction_instance_id) REFERENCES bci_activity_instance (bci_activity_instance_id)
);

/***********************************************************************************************************************
behavior_performance table: This table stores details about Behavior Performance.
- Columns:
  - behavior_performance_id: A unique identifier for each Behavior Performance. Also, works as a foreign key referencing
  a bci_activity_id in the bci_activity table. This key is used by the Hibernate to map the subclass of BCIActivity.
- Constraints:
  - behavior_performance_pkey: Primary key for this table.
  - behavior_performance_fkey: Ensures that behavior_performance_id references a valid record in the
  bci_activity table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS behavior_performance (
    behavior_performance_id BIGINT NOT NULL,
    CONSTRAINT behavior_performance_pkey PRIMARY KEY (behavior_performance_id),
    CONSTRAINT behavior_performance_fkey FOREIGN KEY (behavior_performance_id) REFERENCES bci_activity (bci_activity_id)
);

/***********************************************************************************************************************
behavior_performance_instance table: This table stores details about Behavior Performance Instance.
- Columns:
  - behavior_performance_instance_id: A unique identifier for each Behavior Performance Instance. Also works as a foreign
  key referencing a bci_activity_instance_id in the bci_activity_instance table. This key is used by the Hibernate to
  map the subclass of BCIActivityInstance.
- Constraints:
  - behavior_performance_instance_pkey: Primary key for this table.
  - behavior_performance_instance_fkey: Ensures that behavior_performance_instance_id references a valid record in the
  bci_activity_instance table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS behavior_performance_instance (
    behavior_performance_instance_id BIGINT NOT NULL,
    behavior_performance_instance_behavior_performance_id BIGINT NOT NULL,
    CONSTRAINT behavior_performance_instance_pkey PRIMARY KEY (behavior_performance_instance_id),
    CONSTRAINT behavior_performance_instance_fkey FOREIGN KEY (behavior_performance_instance_id) REFERENCES bci_activity_instance (bci_activity_instance_id)
);

/***********************************************************************************************************************
requires table: This table represents skills required for certain roles at a particular level.
- Columns:
  - requires_id: A unique identifier for each requirement, auto-incremented.
  - requires_level: Denotes the level of proficiency required (e.g., "Beginner", "Intermediate", "Advanced").
  - requires_role_id: A foreign key linking to a role_id in the role table.
  - requires_skill_id: A foreign key linking to a skill_id in the skill table.
  - requires_bci_activity_id: A foreign key linking to a bci_activity_id in the bci_activity table.
- Constraints:
  - requires_pkey: Primary key for this table.
  - requires_bci_activity_fkey: Ensures that requires_bci_activity_id references a valid record in the bci_activity table.
  - requires_skill_fkey: Ensures that requires_skill_id references a valid record in the skill table.
  - requires_role_fkey: Ensures that requires_role_id references a valid record in the role table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS requires (
    requires_id BIGSERIAL NOT NULL,
    requires_level VARCHAR(256) NOT NULL,
    requires_role_id BIGINT NOT NULL,
    requires_skill_id BIGINT NOT NULL,
    requires_bci_activity_id BIGINT NOT NULL,
    CONSTRAINT requires_pkey PRIMARY KEY (requires_id),
    CONSTRAINT requires_bci_activity_fkey FOREIGN KEY (requires_bci_activity_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT requires_skill_fkey FOREIGN KEY (requires_skill_id) REFERENCES skill (skill_id),
    CONSTRAINT requires_role_fkey FOREIGN KEY (requires_role_id) REFERENCES role (role_id)
);

/***********************************************************************************************************************
develops table: This table represents skills that are developed by roles at a certain level of expertise.
- Columns:
  - develops_id: A unique identifier for each row, auto-incremented.
  - develops_level: Denotes the level of expertise developed (e.g., "Beginner", "Intermediate", "Advanced").
  - develops_role_id: A foreign key linking to a role_id in the role table.
  - develops_skill_id: A foreign key linking to a skill_id in the skill table.
  - develops_bci_activity_id: A foreign key linking to a bci_activity_id in the bci_activity table.
- Constraints:
  - develops_pkey: Primary key for this table.
  - develops_bci_activity_fkey: Ensures that develops_bci_activity_id references a valid record in the bci_activity table.
  - develops_skill_fkey: Ensures that develops_skill_id references a valid record in the skill table.
  - develops_role_fkey: Ensures that develops_role_id references a valid record in the role table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS develops (
    develops_id BIGSERIAL NOT NULL,
    develops_level VARCHAR(256) NOT NULL,
    develops_role_id BIGINT NOT NULL,
    develops_skill_id BIGINT NOT NULL,
    develops_bci_activity_id BIGINT NOT NULL,
    CONSTRAINT develops_pkey PRIMARY KEY (develops_id),
    CONSTRAINT develops_bci_activity_fkey FOREIGN KEY (develops_bci_activity_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT develops_skill_fkey FOREIGN KEY (develops_skill_id) REFERENCES skill (skill_id),
    CONSTRAINT develops_role_fkey FOREIGN KEY (develops_role_id) REFERENCES role (role_id)
);

/***********************************************************************************************************************
bci_activity_content table: This is a junction table that represents a many-to-many relationship between bci_activity
  and content. It links which content corresponds to which bci_activity(s).
- Columns:
  - bci_activity_content_id: A unique identifier for each mapping, auto-incremented.
  - bci_activity_content_bci_activity_id: A foreign key referencing a bci_activity_id in the bci_activity table.
  - bci_activity_content_content_id: A foreign key referencing a content_id in the content table.
- Constraints:
  - bci_activity_content_pkey: Primary key for this table.
  - bci_activity_content_bci_activity_fkey: Ensures that bci_activity_content_bci_activity_id references a valid record
    in the bci_activity table.
  - bci_activity_content_content_fkey: Ensures that bci_activity_content_content_id references a valid record in the
    content table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity_content (
    bci_activity_content_id BIGSERIAL NOT NULL,
    bci_activity_content_bci_activity_id BIGINT NOT NULL,
    bci_activity_content_content_id BIGINT NOT NULL,
    CONSTRAINT bci_activity_content_pkey PRIMARY KEY (bci_activity_content_id),
    CONSTRAINT bci_activity_content_bci_activity_fkey FOREIGN KEY (bci_activity_content_bci_activity_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT bci_activity_content_content_fkey FOREIGN KEY (bci_activity_content_content_id) REFERENCES content (content_id)
);

/***********************************************************************************************************************
bci_activity_role table: This is a junction table that represents a many-to-many relationship between bci_activity
  and role. It links which role corresponds to which bci_activity(s).
- Columns:
  - bci_activity_role_id: A unique identifier for each mapping, auto-incremented.
  - bci_activity_role_bci_activity_id: A foreign key referencing a bci_activity_id in the bci_activity table.
  - bci_activity_role_role_id: A foreign key referencing a role_id in the role table.
- Constraints:
  - bci_activity_role_pkey: Primary key for this table.
  - bci_activity_role_bci_activity_fkey: Ensures that bci_activity_content_bci_activity_id references a valid record
    in the bci_activity table.
  - bci_activity_role_content_fkey: Ensures that bci_activity_role_role_id references a valid record in the
    role table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity_role (
    bci_activity_role_id BIGSERIAL NOT NULL,
    bci_activity_role_bci_activity_id BIGINT NOT NULL,
    bci_activity_role_role_id BIGINT NOT NULL,
    CONSTRAINT bci_activity_role_pkey PRIMARY KEY (bci_activity_role_id),
    CONSTRAINT bci_activity_role_bci_activity_fkey FOREIGN KEY (bci_activity_role_bci_activity_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT bci_activity_role_content_fkey FOREIGN KEY (bci_activity_role_role_id) REFERENCES role (role_id)
);

/***********************************************************************************************************************
behavior_change_intervention table: This table stores information about Behavior Change Intervention.
- Columns:
  - behavior_change_intervention_id: A unique identifier for each Behavior Change Intervention. It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - behavior_change_intervention_name: The name of the behavior change intervention.
- Constraints:
  - behavior_change_intervention_pkey: Declares behavior_change_intervention_id as the primary key — ensuring each row has a unique identifier.
  - behavior_change_intervention_name_ukey: Ensures that behavior_change_intervention_name is unique, meaning no
    duplicate behavior change intervention names can exist.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS behavior_change_intervention (
    behavior_change_intervention_id BIGSERIAL NOT NULL,
    behavior_change_intervention_name VARCHAR(256) NOT NULL,
    CONSTRAINT behavior_change_intervention_pkey PRIMARY KEY (behavior_change_intervention_id),
    CONSTRAINT behavior_change_intervention_name_ukey UNIQUE (behavior_change_intervention_name)
);

/***********************************************************************************************************************
behavior_change_intervention_block table: This table stores information about Behavior Change Intervention Block.
- Columns:
  - behavior_change_intervention_block_id: A unique identifier for each Behavior Change Intervention Block.
    It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - behavior_change_intervention_block_entry_conditions: The entry conditions of the behavior change intervention block.
  - behavior_change_intervention_block_exit_conditions: The exit conditions of the behavior change intervention block.
- Constraints:
  - behavior_change_intervention_block_pkey: Declares behavior_change_intervention_block_id as the primary key —
    ensuring each row has a unique identifier.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS behavior_change_intervention_block (
    behavior_change_intervention_block_id BIGSERIAL NOT NULL,
    behavior_change_intervention_block_entry_conditions VARCHAR(256) NOT NULL,
    behavior_change_intervention_block_exit_conditions VARCHAR(256) NOT NULL,
    CONSTRAINT behavior_change_intervention_block_pkey PRIMARY KEY (behavior_change_intervention_block_id)
);

/***********************************************************************************************************************
composed_of table: This is a junction table that represents a many-to-many relationship between bci_activity and
  behavior_change_intervention_block.
- Columns:
  - composed_of_id: A unique identifier for each mapping, auto-incremented.
  - composed_of_time_cycle: Represents the Time Cycle of Composed Of class.
  - composed_of_order:
  - composed_of_bci_activity_id: A foreign key referencing a bci_activity_id in the bci_activity table.
  - composed_of_bci_block_id: A foreign key referencing a behavior_change_intervention_block_id in the
  behavior_change_intervention_block table.
- Constraints:
  - composed_of_pkey: Primary key for this table.
  - composed_of_bci_activity_fkey: Ensures that composed_of_bci_activity_id references a valid record in the bci_activity table.
  - composed_of_bci_block_fkey: Ensures that composed_of_bci_block_id references a valid record in the
  behavior_change_intervention_block table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS composed_of (
    composed_of_id BIGSERIAL NOT NULL,
    composed_of_time_cycle VARCHAR(128) NOT NULL,
    composed_of_order INTEGER,
    composed_of_bci_activity_id BIGINT NOT NULL,
    composed_of_bci_block_id BIGINT NOT NULL,
    CONSTRAINT composed_of_pkey PRIMARY KEY (composed_of_id),
    CONSTRAINT composed_of_bci_activity_fkey FOREIGN KEY (composed_of_bci_activity_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT composed_of_bci_block_fkey FOREIGN KEY (composed_of_bci_block_id)
        REFERENCES behavior_change_intervention_block (behavior_change_intervention_block_id)
);

/***********************************************************************************************************************
behavior_change_intervention_phase table: This table stores information about Behavior Change Intervention Phase.
- Columns:
  - behavior_change_intervention_phase_id: A unique identifier for each Behavior Change Intervention Phase.
    It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - behavior_change_intervention_phase_entry_conditions: The entry conditions of the behavior change intervention phase.
  - behavior_change_intervention_phase_exit_conditions: The exit conditions of the behavior change intervention phase.
  - behavior_change_intervention_phase_bci_id: A foreign key referencing a behavior_change_intervention_id in the
  behavior_change_intervention table.
- Constraints:
  - behavior_change_intervention_phase_pkey: Declares behavior_change_intervention_phase_id as the primary key —
    ensuring each row has a unique identifier.
  - behavior_change_intervention_phase_bci_fkey: Ensures that behavior_change_intervention_phase_bci_id references a valid
  record in the behavior_change_intervention table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS behavior_change_intervention_phase (
    behavior_change_intervention_phase_id BIGSERIAL NOT NULL,
    behavior_change_intervention_phase_entry_conditions VARCHAR(256) NOT NULL,
    behavior_change_intervention_phase_exit_conditions VARCHAR(256) NOT NULL,
    behavior_change_intervention_phase_bci_id BIGINT NULL,
    CONSTRAINT behavior_change_intervention_phase_pkey PRIMARY KEY (behavior_change_intervention_phase_id),
    CONSTRAINT behavior_change_intervention_phase_bci_fkey FOREIGN KEY (behavior_change_intervention_phase_bci_id)
        REFERENCES behavior_change_intervention (behavior_change_intervention_id)
);

/***********************************************************************************************************************
compose_of_phase_block table: This is a junction table that represents a many-to-many relationship between
  behavior_change_intervention_phase and behavior_change_intervention_block.
- Columns:
  - compose_of_phase_block_id: A unique identifier for each mapping, auto-incremented.
  - compose_of_phase_block_bci_phase_id: A foreign key referencing a behavior_change_intervention_phase_id in the
  behavior_change_intervention_phase table.
  - compose_of_phase_block_bci_block_id: A foreign key referencing a behavior_change_intervention_block_id in the
  behavior_change_intervention_block table.
- Constraints:
  - compose_of_phase_block_block_pkey: Primary key for this table.
  - compose_of_phase_block_bci_phase_fkey: Ensures that compose_of_phase_block_bci_phase_id references a valid
  record in the behavior_change_intervention_phase table.
  - compose_of_phase_block_bci_block_fkey: Ensures that compose_of_phase_block_bci_block_id references a valid
  record in the behavior_change_intervention_block table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS compose_of_phase_block (
    compose_of_phase_block_id BIGSERIAL NOT NULL,
    compose_of_phase_block_bci_phase_id BIGINT NOT NULL,
    compose_of_phase_block_bci_block_id BIGINT NOT NULL,
    CONSTRAINT compose_of_phase_block_block_pkey PRIMARY KEY (compose_of_phase_block_id),
    CONSTRAINT compose_of_phase_block_bci_phase_fkey FOREIGN KEY (compose_of_phase_block_bci_phase_id)
        REFERENCES behavior_change_intervention_phase (behavior_change_intervention_phase_id),
    CONSTRAINT compose_of_phase_block_bci_block_fkey FOREIGN KEY (compose_of_phase_block_bci_block_id)
        REFERENCES behavior_change_intervention_block (behavior_change_intervention_block_id)
);

/***********************************************************************************************************************
bci_module table: This table stores information about Module.
- Columns:
  - bci_module_id: A unique identifier for each Module. It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - bci_module_name: The name of the module.
  - bci_module_description: Used to describe a module.
  - bci_module_preconditions: Used to define th module preconditions.
  - bci_module_postconditions: Used to define th module pos-conditions.
- Constraints:
  - bci_module_pk: Declares bci_module_id as the primary key — ensuring each row has a unique identifier.
  - bci_module_name_unique: Ensures that bci_module_name is unique, meaning no duplicate module names can exist.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_module (
    bci_module_id bigserial NOT NULL,
    bci_module_name varchar NOT NULL,
    bci_module_description varchar NULL,
    bci_module_preconditions varchar NOT NULL,
    bci_module_postconditions varchar NOT NULL,
    CONSTRAINT bci_module_pk PRIMARY KEY (bci_module_id),
    CONSTRAINT bci_module_name_unique UNIQUE (bci_module_name)
);

/***********************************************************************************************************************
bci_module_skill table: This is a junction table that represents a many-to-many relationship between bci_module and skill.
- Columns:
  - bci_module_skill_bci_module_id: A foreign key referencing a bci_module_id in the bci_module table.
  - bci_module_skill_skill_id: A foreign key referencing a skill_id in the skill table.
- Constraints:
  - bci_module_skill_pk: Primary key for this table is composed of bci_module_skill_bci_module_id and bci_module_skill_skill_id.
  - bci_module_skill_bci_module_fkey: Ensures that bci_module_skill_bci_module_id references a valid record in the bci_module table.
  - bci_module_skill_skill_fkey: Ensures that bci_module_skill_skill_id references a valid record in the skill_id table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_module_skill (
    bci_module_skill_bci_module_id BIGINT NOT NULL,
    bci_module_skill_skill_id BIGINT NOT NULL,
    CONSTRAINT bci_module_skill_pk PRIMARY KEY (bci_module_skill_bci_module_id, bci_module_skill_skill_id),
    CONSTRAINT bci_module_skill_bci_module_fkey FOREIGN KEY (bci_module_skill_bci_module_id)
        REFERENCES bci_module (bci_module_id),
    CONSTRAINT bci_module_skill_skill_fkey FOREIGN KEY (bci_module_skill_skill_id)
        REFERENCES skill (skill_id)
);

/***********************************************************************************************************************
module_composed_activity table: This is a junction table that represents a many-to-many relationship between bci_module
  and bci_activity.
- Columns:
  - module_composed_activity_id: A unique identifier for each Module. It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - module_composed_activity_bci_module_id: A foreign key referencing a bci_module_id in the bci_module table.
  - module_composed_activity_bci_activity_id: A foreign key referencing a bci_activity_id in the bci_activity table.
  - module_composed_activity_order: Define the activity order.
- Constraints:
  - module_composed_activity_pk: Declares module_composed_activity_id as the primary key — ensuring each row has a unique identifier.
  - module_composed_activity_bci_module_fkey: Ensures that module_composed_activity_bci_module_id references a valid
  record in the bci_module table.
  - module_composed_activity_bci_activity_fkey: Ensures that module_composed_activity_bci_activity_id references a valid
  record in the bci_activity table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS module_composed_activity (
    module_composed_activity_id BIGSERIAL NOT NULL,
    module_composed_activity_bci_module_id BIGINT NOT NULL,
    module_composed_activity_bci_activity_id BIGINT NOT NULL,
    module_composed_activity_order INT NOT NULL,
    CONSTRAINT module_composed_activity_pk PRIMARY KEY (module_composed_activity_id),
    CONSTRAINT module_composed_activity_bci_module_fkey FOREIGN KEY (module_composed_activity_bci_module_id)
        REFERENCES bci_module (bci_module_id),
    CONSTRAINT module_composed_activity_bci_activity_fkey FOREIGN KEY (module_composed_activity_bci_activity_id)
        REFERENCES bci_activity (bci_activity_id)
);

/***********************************************************************************************************************
bci_phase_contains_module table: This is a junction table that represents a many-to-many relationship between bci_module
  and behavior_change_intervention_phase.
- Columns:
  - bci_phase_contains_module_phase_id: A foreign key referencing a behavior_change_intervention_phase_id in the
  behavior_change_intervention_phase table.
  - bci_phase_contains_module_module_id: A foreign key referencing a bci_module_id in the bci_module table.
- Constraints:
  - bci_phase_contains_module_pk: Primary key for this table is composed of bci_phase_contains_module_phase_id and
  bci_phase_contains_module_module_id.
  - bci_phase_contains_module_bci_module_fkey: Ensures that bci_phase_contains_module_module_id references a valid record
  in the bci_module table.
  - behavior_change_intervention_phase: Ensures that behavior_change_intervention_phase_id references a valid record in
  the behavior_change_intervention_phase table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_phase_contains_module (
    bci_phase_contains_module_phase_id BIGINT NOT NULL,
    bci_phase_contains_module_module_id BIGINT NOT NULL,
    CONSTRAINT bci_phase_contains_module_pk PRIMARY KEY (bci_phase_contains_module_phase_id, bci_phase_contains_module_module_id),
     CONSTRAINT bci_phase_contains_module_bci_module_fkey FOREIGN KEY (bci_phase_contains_module_module_id)
         REFERENCES bci_module (bci_module_id),
     CONSTRAINT bci_phase_contains_module_phase_fkey FOREIGN KEY (bci_phase_contains_module_phase_id)
         REFERENCES behavior_change_intervention_phase (behavior_change_intervention_phase_id)
);

/***********************************************************************************************************************
assessment table: This table stores information about Assessment.
- Columns:
  - assessment_id: A unique identifier for each Assessment. Also works as a foreign key referencing a bci_activity_id
  in the bci_activity table used by the Hibernate to map the subclass of BCIActivity.
  - assessment_assessee_role_id: The assessee role id.
  - assessment_assessor_role_id: The assessor role id.
  - assessment_scale: The assessment scale.
  - assessment_scoring_function: The assessment scoring function.
  - assessment_self_relationship_id: Used to indicate that an assessment has a relationship with another assessment.
- Constraints:
  - assessment_pk: Declares assessment_id as the primary key — ensuring each row has a unique identifier.
  - assessment_fkey: This constraint is used by the Hibernate to map the subclass of BCIActivity.
  - assessment_assessee_role_fkey: Ensures that assessment_assessee_role_id references a valid record in the role table.
  - assessment_assessor_role_fkey: Ensures that assessment_assessor_role_id references a valid record in the role table.
  - assessment_self_relationship_fkey: Ensures that assessment_self_relationship_id references a valid record in the assessment table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS assessment (
    assessment_id BIGINT NOT NULL,
    assessment_assessee_role_id BIGINT NOT NULL,
    assessment_assessor_role_id BIGINT NOT NULL,
    assessment_scale VARCHAR(6),
    assessment_scoring_function VARCHAR(250),
    assessment_self_relationship_id BIGINT,
    CONSTRAINT assessment_pk PRIMARY KEY (assessment_id),
    CONSTRAINT assessment_fkey FOREIGN KEY (assessment_id) REFERENCES bci_activity (bci_activity_id),
    CONSTRAINT assessment_assessee_role_fkey FOREIGN KEY (assessment_assessee_role_id) REFERENCES role (role_id),
    CONSTRAINT assessment_assessor_role_fkey FOREIGN KEY (assessment_assessor_role_id) REFERENCES role (role_id),
    CONSTRAINT assessment_self_relationship_fkey FOREIGN KEY (assessment_self_relationship_id) REFERENCES assessment (assessment_id)
);

/***********************************************************************************************************************
assessment_skill table: This is a junction table that represents a many-to-many relationship between assessment and skill.
- Columns:
  - assessment_skill_assessment_id: A foreign key referencing an assessment_id in the assessment table.
  - assessment_skill_skill_id: A foreign key referencing a skill_id in the skill table.
- Constraints:
  - assessment_skill_pk: Primary key for this table is composed of assessment_id and assessment_skill_skill_id.
  - assessment_skill_assessment_fkey: Ensures that assessment_skill_assessment_id references a valid record in the assessment table.
  - assessment_skill_skill_fkey: Ensures that assessment_skill_skill_id references a valid record in the skill_id table.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS assessment_skill (
    assessment_skill_assessment_id BIGINT NOT NULL,
    assessment_skill_skill_id BIGINT NOT NULL,
    CONSTRAINT assessment_skill_pk PRIMARY KEY (assessment_skill_assessment_id, assessment_skill_skill_id),
    CONSTRAINT assessment_skill_assessment_fkey FOREIGN KEY (assessment_skill_assessment_id) REFERENCES assessment (assessment_id),
    CONSTRAINT assessment_skill_skill_fkey FOREIGN KEY (assessment_skill_skill_id) REFERENCES skill (skill_id)
);

/***********************************************************************************************************************
    bci_block_instance table: Holds data for the instances of bci_block (BehaviorChangeInterventionBlockInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_block_instance (
    bci_block_instance_id BIGSERIAL NOT NULL,
    bci_block_instance_stage VARCHAR(128) NOT NULL,
    CONSTRAINT bci_block_instance_pk PRIMARY KEY (bci_block_instance_id),
    CONSTRAINT bci_block_instance_activity_instance_fkey FOREIGN KEY (bci_block_instance_id) REFERENCES activity_instance (activity_instance_id)
);

/***********************************************************************************************************************
    bci_block_instance_activities table: Junction table for the many-to-many relationship
        between bci_block_instance and bci_activity_instance.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_block_instance_activities (
    bci_block_instance_activities_block_id BIGINT NOT NULL,
    bci_block_instance_activities_activity_id BIGINT NOT NULL,
    CONSTRAINT bci_block_instance_activities_pk PRIMARY KEY (bci_block_instance_activities_block_id, bci_block_instance_activities_activity_id),
    CONSTRAINT bci_block_instance_activities_block_fkey FOREIGN KEY (bci_block_instance_activities_block_id) REFERENCES bci_block_instance (bci_block_instance_id),
    CONSTRAINT bci_block_instance_activities_activity_fkey FOREIGN KEY (bci_block_instance_activities_activity_id) REFERENCES bci_activity_instance (bci_activity_instance_id)
);

/***********************************************************************************************************************
    bci_module_instance table: Holds data for the instances of bci_module (BCIModuleInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_module_instance (
    bci_module_instance_id BIGINT NOT NULL,
    bci_module_instance_outcome VARCHAR(128) NULL,
    CONSTRAINT bci_module_instance_pk PRIMARY KEY (bci_module_instance_id),
    CONSTRAINT bci_module_instance_activity_instance_fkey FOREIGN KEY (bci_module_instance_id) REFERENCES activity_instance (activity_instance_id)
);

/***********************************************************************************************************************
    bci_module_instance_activities table: Junction table for the many-to-many relationship
        between bci_module_instance and bci_activity_instance.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_module_instance_activities (
    bci_module_instance_activities_module_id BIGINT NOT NULL,
    bci_module_instance_activities_activity_id BIGINT NOT NULL,
    CONSTRAINT bci_module_instance_activities_pk PRIMARY KEY (bci_module_instance_activities_module_id, bci_module_instance_activities_activity_id),
    CONSTRAINT bci_module_instance_activities_module_fkey FOREIGN KEY (bci_module_instance_activities_module_id) REFERENCES bci_module_instance (bci_module_instance_id),
    CONSTRAINT bci_module_instance_activities_activity_fkey FOREIGN KEY (bci_module_instance_activities_activity_id) REFERENCES bci_activity_instance (bci_activity_instance_id)
);

/**********************************************************************************************************************
    bci_phase_instance table: Holds data for the instances of bci_phases (BehaviorChangeInterventionPhaseInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_phase_instance (
    bci_phase_instance_id BIGINT NOT NULL,
    bci_phase_instance_currentblock_id BIGINT NOT NULL,
    CONSTRAINT bci_phase_instance_pk PRIMARY KEY (bci_phase_instance_id),
    CONSTRAINT bci_phase_instance_currentblock_fkey FOREIGN KEY (bci_phase_instance_currentblock_id) REFERENCES bci_block_instance (bci_block_instance_id),
    CONSTRAINT bci_phase_instance_activity_instance_fkey FOREIGN KEY (bci_phase_instance_id) REFERENCES activity_instance (activity_instance_id)
);

/***********************************************************************************************************************
    bci_phase_instance_activities table: Junction table for the many-to-many relationship
        between bci_phase_instance and bci_block_instance.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_phase_instance_activities (
    bci_phase_instance_activities_phase_id BIGINT NOT NULL,
    bci_phase_instance_activities_block_id BIGINT NOT NULL,
    CONSTRAINT bci_phase_instance_activities_pk PRIMARY KEY (bci_phase_instance_activities_phase_id, bci_phase_instance_activities_block_id),
    CONSTRAINT bci_phase_instance_activities_phase_fkey FOREIGN KEY (bci_phase_instance_activities_phase_id) REFERENCES bci_phase_instance (bci_phase_instance_id),
    CONSTRAINT bci_phase_instance_activities_block_fkey FOREIGN KEY (bci_phase_instance_activities_block_id) REFERENCES bci_block_instance (bci_block_instance_id)
);

/***********************************************************************************************************************
    bci_module_instance_activities table: Junction table for the many-to-many relationship
        between bci_phase_instance and bci_module_instance.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_phase_instance_modules (
    bci_phase_instance_modules_phase_id BIGINT NOT NULL,
    bci_phase_instance_modules_module_id BIGINT NOT NULL,
    CONSTRAINT bci_phase_instance_modules_pk PRIMARY KEY (bci_phase_instance_modules_phase_id, bci_phase_instance_modules_module_id),
    CONSTRAINT bci_phase_instance_modules_phase_fkey FOREIGN KEY (bci_phase_instance_modules_phase_id) REFERENCES bci_phase_instance (bci_phase_instance_id),
    CONSTRAINT bci_phase_instance_modules_module_fkey FOREIGN KEY (bci_phase_instance_modules_module_id) REFERENCES bci_module_instance (bci_module_instance_id)
);

/***********************************************************************************************************************
    bci_instance table: Holds data for the instances of behavioral change interventions (BehaviorChangeInterventionInstance class).
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_instance (
    bci_instance_id BIGINT NOT NULL,
    bci_instance_patient_id BIGINT NOT NULL,
    bci_instance_currentphase_id BIGINT,
    bci_instance_activities_id BIGINT,
    CONSTRAINT bci_instance_pk PRIMARY KEY (bci_instance_id),
    CONSTRAINT bci_instance_patient_fkey FOREIGN KEY (bci_instance_patient_id) REFERENCES patient (patient_id),
    CONSTRAINT bci_instance_currentphase_fkey FOREIGN KEY (bci_instance_currentphase_id) REFERENCES bci_phase_instance (bci_phase_instance_id),
    CONSTRAINT bci_instance_activity_instance_fkey FOREIGN KEY (bci_instance_id) REFERENCES activity_instance (activity_instance_id)
);

/***********************************************************************************************************************
    bci_instance_pactivities table: Junction table for the many-to-many relationship
        between bci_instance and bci_phase_instance.
 **********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_instance_activities (
    bci_instance_activities_bci_id BIGINT NOT NULL,
    bci_instance_activities_phase_id BIGINT NOT NULL,
    CONSTRAINT bci_instance_activities_pk PRIMARY KEY (bci_instance_activities_bci_id, bci_instance_activities_phase_id),
    CONSTRAINT bci_instance_activities_bci_fkey FOREIGN KEY (bci_instance_activities_bci_id) REFERENCES bci_instance (bci_instance_id),
    CONSTRAINT bci_instance_activities_phase_fkey FOREIGN KEY (bci_instance_activities_phase_id) REFERENCES bci_phase_instance (bci_phase_instance_id)
);

/***********************************************************************************************************************
bci referral table: This table stores information about bci referral.
- Columns:
  - bci_referral_id: A unique identifier for each patient assessment, auto-incremented.
  - bci_referral_date: The date of creation
  - bci_referral_reason: The reason behind the referral
  - bci_referral_patient_assessment: The assessment tied to the referral
  - bci_referral_professional: The professional who made the referral
  - bci_referral_interventionist: The interventionist that may be recommended by the referral
  - bci_referral_interventions: The list of bci instances
- Constraints:
  - bci_referral_pkey: Establishes bci_referral_id as the primary key.
  - bci_referral_patient_assessment_fkey: Ensures that bci_referral_patient_assessment references a valid entry in the patient_assessment table.
  - bci_referral_professional_fkey: Ensures that bci_referral_professional references a valid entry in the healthcare_professional table.
  - bci_referral_interventionist_fkey: Ensures that bci_referral_interventionist references a valid entry in the healthcare_professional table.
  - bci_referral_interventions_fkey: Reference to bci_instance table for interventions
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_referral (
    bci_referral_id BIGSERIAL NOT NULL,
    bci_referral_date DATE NOT NULL,
    bci_referral_reason VARCHAR NOT NULL,
    bci_referral_patient BIGINT NOT NULL,
    bci_referral_patient_assessment BIGINT NOT NULL,
    bci_referral_professional BIGINT NOT NULL,
    bci_referral_interventionist BIGINT,
    CONSTRAINT bci_referral_pkey PRIMARY KEY (bci_referral_id),
    CONSTRAINT bci_referral_patient_fkey FOREIGN KEY (bci_referral_patient) REFERENCES patient (patient_id),
    CONSTRAINT bci_referral_patient_assessment_fkey FOREIGN KEY (bci_referral_patient_assessment) REFERENCES patient_assessment (patient_assessment_id),
    CONSTRAINT bci_referral_professional_fkey FOREIGN KEY (bci_referral_professional) REFERENCES healthcare_professional (healthcare_professional_id),
    CONSTRAINT bci_referral_interventionist_fkey FOREIGN KEY (bci_referral_interventionist) REFERENCES healthcare_professional (healthcare_professional_id)
);

CREATE TABLE IF NOT EXISTS bci_referral_interventions (
    bci_referral_interventions_bci_id BIGINT NOT NULL,
    bci_referral_interventions_referral_id BIGINT NOT NULL,
    CONSTRAINT bci_referral_interventions_pk PRIMARY KEY (bci_referral_interventions_bci_id, bci_referral_interventions_referral_id),
    CONSTRAINT bci_referral_interventions_bci_fkey FOREIGN KEY (bci_referral_interventions_bci_id) REFERENCES bci_instance (bci_instance_id),
    CONSTRAINT bci_referral_interventions_referral_fkey FOREIGN KEY (bci_referral_interventions_referral_id) REFERENCES bci_referral (bci_referral_id)
);