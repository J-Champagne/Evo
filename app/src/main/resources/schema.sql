/***********************************************************************************************************************
The provided SQL script defines a relational database structure involving multiple tables —
role, actor, skill, content, skill_content, requires, and develops.

 - since 22.01.2025.
 - version 1.0
 - author Edilton Lima dos Santos.
***********************************************************************************************************************/


/***********************************************************************************************************************
role table: This table stores information about roles in the system (e.g., admin, user, etc.).
- Columns:
  - role_id: A unique identifier for each role. It's type BIGSERIAL, meaning it's an auto-incrementing integer.
  - role_name: The name of the role (e.g., "Admin", "e-Facilitor").
- Constraints:
  - role_pkey: Declares role_id as the primary key — ensuring each row has a unique identifier.
  - role_role_name_ukey: Ensures that role_name is unique, meaning no duplicate role names can exist.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS role (
    role_id BIGSERIAL NOT NULL,
    role_name VARCHAR,
    CONSTRAINT role_pkey PRIMARY KEY (role_id),
    CONSTRAINT role_role_name_ukey UNIQUE (role_name)
);

/***********************************************************************************************************************
actor table: This table stores information about actors (users or individuals) in the system.
- Columns:
  - actor_id: A unique identifier for each actor, auto-incremented.
  - actor_name: The name of the actor. It's required (NOT NULL).
  - actor_email: The email of the actor, which is also required and must be unique.
  - actor_role_id: A foreign key referencing the role_id in the role table. It links an actor to a role.
- Constraints:
  - actor_pkey: Establishes actor_id as the primary key.
  - actor_actor_email_ukey: Ensures that actor_email is unique.
  - actor_role_fkey: Ensures that actor_role_id references a valid role in the role table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS actor (
    actor_id BIGSERIAL NOT NULL,
    actor_name VARCHAR NOT NULL,
    actor_email VARCHAR NOT NULL,
    actor_role_id BIGINT NULL,
    CONSTRAINT actor_pkey PRIMARY KEY (actor_id),
    CONSTRAINT actor_actor_email_ukey UNIQUE (actor_email),
    CONSTRAINT actor_role_fkey FOREIGN KEY (actor_role_id) REFERENCES role (role_id)
);

/***********************************************************************************************************************
skill table: This table stores details about skills that actors may hold or require.
- Columns:
  - skill_id: A unique identifier for each skill, auto-incremented.
  - skill_name: The name of the skill. It's required and must be unique.
  - skill_description: A description of the skill.
  - skill_type: An optional string to classify the skill.
  - skill_skill_id: An optional foreign key used to identify the dependence between skills.
    It links a skill to another skill.
- Constraints:
  - skill_pkey: Establishes skill_id as the primary key.
  - skill_skill_name_ukey: Ensures that skill_name is unique.
  - skill_skill_fkey: Ensures that skill_skill_id references a valid skill (sub skill) in the skill table.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS skill (
     skill_id BIGSERIAL NOT NULL,
     skill_name VARCHAR NOT NULL,
     skill_description VARCHAR NULL,
     skill_type VARCHAR NULL,
     skill_skill_id BIGINT NULL,
     CONSTRAINT skill_pkey PRIMARY KEY (skill_id),
     CONSTRAINT skill_skill_name_ukey UNIQUE (skill_name),
     CONSTRAINT skill_skill_fkey FOREIGN KEY (skill_skill_id) REFERENCES skill (skill_id)
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
    content_name VARCHAR NOT NULL,
    content_description VARCHAR NOT NULL,
    content_type VARCHAR NULL,
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
bci_activity table:This table stores details about Behavior Change Technique Intervention Activity.
- Columns:
  - bci_activity_id: A unique identifier for each Behavior Change Technique Intervention Activity, auto-incremented.
  - bci_activity_name: The name of the Behavior Change Technique Intervention Activity. It's required and must be unique.
  - bci_activity_description: An optional description of the Behavior Change Technique Intervention Activity.
  - bci_activity_type: An optional string to classify the Behavior Change Technique Intervention Activity.
  - bci_activity_preconditions: An optional string to define a preconditions of Behavior Change Technique
    Intervention Activity.
  - bci_activity_postconditions: An optional string to define a post-conditions of Behavior Change Technique
    Intervention Activity.
- Constraints:
  - bci_activity_pkey: Establishes bci_activity_id as the primary key.
  - bci_activity_name_ukey: Ensures that bci_activity_name is unique.
***********************************************************************************************************************/
CREATE TABLE IF NOT EXISTS bci_activity (
    bci_activity_id BIGSERIAL NOT NULL,
    bci_activity_name VARCHAR NOT NULL,
    bci_activity_description VARCHAR NULL,
    bci_activity_type VARCHAR NULL,
    bci_activity_preconditions VARCHAR NULL,
    bci_activity_postconditions VARCHAR NULL,
    CONSTRAINT bci_activity_pkey PRIMARY KEY (bci_activity_id),
    CONSTRAINT bci_activity_name_ukey UNIQUE (bci_activity_name)
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
    requires_level VARCHAR NOT NULL,
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
    develops_level VARCHAR NOT NULL,
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

