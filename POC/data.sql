/***********************************************************************************************************************
Script generated for loading data that will be used in the POC.
Note: Before running this script, drop all tables and create a new schema using the schema.sql file found at
`../app/src/main/resources`.
***********************************************************************************************************************/



/***********************************************************************************************************************
Scenario of test.
The scenario was created to test the happy path. Thus, the scenario involves patient Martin Aghion (id: 5) and a Behavioral
Change Intervention Instance (id: 108), which is an instance of Behavioral Change Intervention (id: 2) with two phases:

- Phase 1 (id: 106) was set to “IN_PROGRESS” and marked as the current phase. It contains one Block (id: 103), also
“IN_PROGRESS” and at the “BEGINNING” stage. This Block contains an Activity (id: 99), likewise “IN_PROGRESS.”
- Phase 2 (id: 107) was initialized as “READY.” It includes two blocks: Block 1 (id: 104), the Current Block, is at the
'UNSPECIFIED' stage and “READY” status with a single Activity (id: 100) also “READY.” Block 2 (id: 105), also 'UNSPECIFIED'
and “READY,” contains two Activities (ids: 101 and 102), both with “READY” status.
***********************************************************************************************************************/

/***********************************************************************************************************************
role
***********************************************************************************************************************/
INSERT INTO "role" (role_name,role_description) VALUES
	 ('Admin','Administrator'),
	 ('Participant','Participant'),
	 ('E-Health','E-Health'),
	 ('Research','Research');


/***********************************************************************************************************************
bci_activity - Activity and Interaction.
***********************************************************************************************************************/
INSERT INTO bci_activity (bci_activity_name,bci_activity_description,bci_activity_type,bci_activity_preconditions,bci_activity_postconditions,bci_activity_type_class) VALUES
	 ('Activity POC 1','Activity POC 1 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 2','Activity POC 2 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 3','Activity POC 3 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 4','Activity POC 4 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 5','Activity POC 5 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Interaction POC 1','Interaction POC 1 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Interaction POC 2','Interaction POC 2 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 8','Activity POC 8 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 9','Activity POC 9 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 10','Activity POC 10 - Description','BCI_ACTIVITY','x->true','x->true',NULL);
INSERT INTO bci_activity (bci_activity_name,bci_activity_description,bci_activity_type,bci_activity_preconditions,bci_activity_postconditions,bci_activity_type_class) VALUES
	 ('Activity POC 11','Activity POC 11 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 12','Activity POC 12 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 13','Activity POC 13 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 14','Activity POC 14 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 15','Activity POC 15 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 16','Activity POC 16 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 17','Activity POC 17 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 18','Activity POC 18 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 19','Activity POC 19 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 20','Activity POC 20 - Description','BCI_ACTIVITY','x->true','x->true',NULL);
INSERT INTO bci_activity (bci_activity_name,bci_activity_description,bci_activity_type,bci_activity_preconditions,bci_activity_postconditions,bci_activity_type_class) VALUES
	 ('Activity POC 21','Activity POC 21 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 22','Activity POC 22 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 23','Activity POC 23 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 24','Activity POC 24 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 25','Activity POC 25 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 26','Activity POC 26 - Description','BCI_ACTIVITY','x->true','x->true',NULL),
	 ('Activity POC 27','Activity POC 27 - Description','BCI_ACTIVITY','x->true','x->true',NULL);


/***********************************************************************************************************************
bci_activity_role - association between activity and role.
***********************************************************************************************************************/
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (1,1),
	 (1,2),
	 (1,3),
	 (1,4),
	 (2,1),
	 (2,2),
	 (2,3),
	 (2,4),
	 (3,1),
	 (3,2);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (3,3),
	 (3,4),
	 (4,1),
	 (4,2),
	 (4,3),
	 (4,4),
	 (5,1),
	 (5,2),
	 (5,3),
	 (5,4);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (6,1),
	 (6,2),
	 (6,3),
	 (6,4),
	 (7,1),
	 (7,2),
	 (7,3),
	 (7,4),
	 (8,1),
	 (8,2);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (8,3),
	 (8,4),
	 (9,1),
	 (9,2),
	 (9,3),
	 (9,4),
	 (10,1),
	 (10,2),
	 (10,3),
	 (10,4);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (11,1),
	 (11,2),
	 (11,3),
	 (11,4),
	 (12,1),
	 (12,2),
	 (12,3),
	 (12,4),
	 (13,1),
	 (13,2);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (13,3),
	 (13,4),
	 (14,1),
	 (14,2),
	 (14,3),
	 (14,4),
	 (15,1),
	 (15,2),
	 (15,3),
	 (15,4);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (16,1),
	 (16,2),
	 (16,3),
	 (16,4),
	 (17,1),
	 (17,2),
	 (17,3),
	 (17,4),
	 (18,1),
	 (18,2);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (18,3),
	 (18,4),
	 (19,1),
	 (19,2),
	 (19,3),
	 (19,4),
	 (20,1),
	 (20,2),
	 (20,3),
	 (20,4);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (21,1),
	 (21,2),
	 (21,3),
	 (21,4),
	 (22,1),
	 (22,2),
	 (22,3),
	 (22,4),
	 (23,1),
	 (23,2);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (23,3),
	 (23,4),
	 (24,1),
	 (24,2),
	 (24,3),
	 (24,4),
	 (25,1),
	 (25,2),
	 (25,3),
	 (25,4);
INSERT INTO bci_activity_role (bci_activity_role_bci_activity_id,bci_activity_role_role_id) VALUES
	 (26,1),
	 (26,2),
	 (26,3),
	 (26,4),
	 (27,1),
	 (27,2),
	 (27,3),
	 (27,4);


/***********************************************************************************************************************
Interaction - The id should be the same as the activity id.
***********************************************************************************************************************/
INSERT INTO interaction (interaction_id,interaction_mode,interaction_initiator_role_id,interaction_medium1,interaction_medium2,interaction_medium3,interaction_medium4) VALUES
	 (6,'HYBRID',2,'MESSAGING','EMAIL','VOICE','VIDEO'),
	 (7,'SYNCHRONOUS',2,'VOICE','VIDEO',NULL,NULL);


/***********************************************************************************************************************
Skill
***********************************************************************************************************************/
INSERT INTO skill (skill_name,skill_description,skill_type,skill_sub_skill_id,skill_composed_of_skill_id) VALUES
     ('Skill 1','Skill 1 - Description','BCT',NULL,NULL),
     ('Skill 2','Skill 2 - Description','MENTAL',NULL,NULL),
     ('Skill 3','Skill 3 - Description','PHYSICAL',NULL,NULL);


/***********************************************************************************************************************
BehaviorChangeInterventionBlock
***********************************************************************************************************************/
INSERT INTO behavior_change_intervention_block (behavior_change_intervention_block_entry_conditions,behavior_change_intervention_block_exit_conditions) VALUES
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
	 ('x->true','x->true'),
     ('x->true','x->true'),
     ('x->true','x->true'),
     ('x->true','x->true'),
     ('x->true','x->true'),
     ('x->true','x->true'),
     ('x->true','x->true');

/***********************************************************************************************************************
composed_of - association between block and activity.
***********************************************************************************************************************/
INSERT INTO composed_of (composed_of_time_cycle,composed_of_order,composed_of_bci_activity_id,composed_of_bci_block_id) VALUES
	 ('BEGINNING',1,1,1),
	 ('UNSPECIFIED',2,6,1),
	 ('UNSPECIFIED',1,2,2),
	 ('UNSPECIFIED',2,3,2),
	 ('UNSPECIFIED',2,4,3),
	 ('UNSPECIFIED',1,5,3),
	 ('UNSPECIFIED',1,7,4),
	 ('UNSPECIFIED',2,8,4),
	 ('UNSPECIFIED',1,9,5),
	 ('UNSPECIFIED',2,10,5);
INSERT INTO composed_of (composed_of_time_cycle,composed_of_order,composed_of_bci_activity_id,composed_of_bci_block_id) VALUES
	 ('UNSPECIFIED',1,11,6),
	 ('UNSPECIFIED',2,12,6),
	 ('UNSPECIFIED',1,13,7),
	 ('UNSPECIFIED',2,14,7),
	 ('UNSPECIFIED',1,15,8),
	 ('UNSPECIFIED',2,16,8),
	 ('UNSPECIFIED',1,17,9),
	 ('UNSPECIFIED',2,18,9),
	 ('UNSPECIFIED',1,19,10),
	 ('UNSPECIFIED',2,20,10);
INSERT INTO composed_of (composed_of_time_cycle,composed_of_order,composed_of_bci_activity_id,composed_of_bci_block_id) VALUES
	 ('UNSPECIFIED',1,21,11),
	 ('UNSPECIFIED',2,22,11),
	 ('UNSPECIFIED',1,23,12),
	 ('UNSPECIFIED',2,24,12),
	 ('UNSPECIFIED',1,25,13),
	 ('UNSPECIFIED',2,26,13),
	 ('UNSPECIFIED',1,27,14),
	 ('BEGINNING',1,1,15),
     ('UNSPECIFIED',1,2,16),
     ('UNSPECIFIED',1,3,17),
     ('UNSPECIFIED',2,4,17);


/***********************************************************************************************************************
behavior_change_intervention
***********************************************************************************************************************/
INSERT INTO behavior_change_intervention (behavior_change_intervention_name,behavior_change_intervention_entry_conditions,behavior_change_intervention_exit_conditions) VALUES
	 ('Intervention POC 1','x->true','x->true'),
	 ('Intervention POC Scenario - 2','x->true','x->true');


/***********************************************************************************************************************
behavior_change_intervention_phase
***********************************************************************************************************************/
INSERT INTO behavior_change_intervention_phase (behavior_change_intervention_phase_entry_conditions,behavior_change_intervention_phase_exit_conditions,behavior_change_intervention_phase_bci_id) VALUES
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',1),
	 ('x->true','x->true',2),
	 ('x->true','x->true',2);


/***********************************************************************************************************************
compose_of_phase_block - association between phase and block.
***********************************************************************************************************************/
INSERT INTO compose_of_phase_block (compose_of_phase_block_bci_phase_id,compose_of_phase_block_bci_block_id) VALUES
	 (1,1),
	 (1,2),
	 (2,3),
	 (2,4),
	 (3,5),
	 (3,6),
	 (4,7),
	 (4,8),
	 (5,9),
	 (5,10);
INSERT INTO compose_of_phase_block (compose_of_phase_block_bci_phase_id,compose_of_phase_block_bci_block_id) VALUES
	 (6,11),
	 (6,12),
	 (7,13),
	 (7,14),
	 (8,15),
     (9,16),
     (9,17);


/***********************************************************************************************************************
actor
***********************************************************************************************************************/
INSERT INTO actor (actor_name,actor_email,actor_contact_information) VALUES
	 ('Pierre Drapeau','p.drapeau@gmail.com','99999'),
	 ('Marie Leblanc','marie@gmail.com','88888'),
	 ('Marie Drapeau','marie.drapeau@gmail.com','77777'),
	 ('Java Carrey','jcarrey@gmail.com','66666'),
     ('Martin Aghion','martin.aghion@gmail.com','55555');


/***********************************************************************************************************************
patient_medicalfile
***********************************************************************************************************************/
INSERT INTO patient_medicalfile (patient_medicalfile_date,patient_medicalfile_medicalhistory) VALUES
	 ('2025-08-06','Healthy'),
	 ('2025-05-06','Healthy'),
     ('2025-10-06','Healthy');


/***********************************************************************************************************************
patient
***********************************************************************************************************************/
INSERT INTO patient (patient_id,patient_birthdate,patient_occupation,patient_address,patient_patient_medical_file_id) VALUES
	 (1,'1990-01-01','Software Developer','3333 Street',1),
	 (2,'1995-06-07','Student','72 Street Vert',2),
     (5,'1985-10-10','Engineer','4 Street',3);


/***********************************************************************************************************************
healthcare_professional
***********************************************************************************************************************/
INSERT INTO healthcare_professional (healthcare_professional_id,healthcare_professional_position,healthcare_professional_affiliation,healthcare_professional_specialties) VALUES
	 (3,'POC Position 1','CIUSSS','Healthcare'),
	 (4,'POC Position 2','CIUSSS','Healthcare');


/***********************************************************************************************************************
participant
***********************************************************************************************************************/
INSERT INTO participant (participant_role_id,participant_actor_id) VALUES
	 (2,1),
	 (2,2),
	 (3,3),
	 (3,4),
     (2,5);


/***********************************************************************************************************************
activity_instance
***********************************************************************************************************************/
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('IN_PROGRESS','2025-10-08',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-08',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-08',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-08',NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('IN_PROGRESS','2025-10-10',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-10',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-10',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-10',NULL);
INSERT INTO activity_instance (activity_instance_status,activity_instance_entry_date,activity_instance_exit_date) VALUES
	 ('IN_PROGRESS','2025-10-15',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-15',NULL),
	 ('READY',NULL,NULL),
	 ('READY',NULL,NULL),
	 ('IN_PROGRESS','2025-10-15',NULL),
     ('READY',NULL,NULL),
     ('IN_PROGRESS','2025-10-15',NULL);



/***********************************************************************************************************************
bci_activity_instance - association between activity_instance and bci_activity.
***********************************************************************************************************************/
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (1,1),
	 (2,2),
	 (3,3),
	 (4,4),
	 (5,5),
	 (6,6),
	 (7,7),
	 (8,8),
	 (9,9),
	 (10,10);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (11,11),
	 (12,12),
	 (13,13),
	 (14,14),
	 (15,15),
	 (16,16),
	 (17,17),
	 (18,18),
	 (19,19),
	 (20,20);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (21,21),
	 (22,22),
	 (23,23),
	 (24,24),
	 (25,25),
	 (26,26),
	 (27,27);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (50,1),
	 (51,2),
	 (52,3),
	 (53,4),
	 (54,5),
	 (55,6),
	 (56,7),
	 (57,8),
	 (58,9),
	 (59,10);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (60,11),
	 (61,12),
	 (62,13),
	 (63,14),
	 (64,15),
	 (65,16),
	 (66,17),
	 (67,18),
	 (68,19),
	 (69,20);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (70,21),
	 (71,22),
	 (72,23),
	 (73,24),
	 (74,25),
	 (75,26),
	 (76,27);
INSERT INTO bci_activity_instance (bci_activity_instance_id,bci_activity_instance_bci_activity_id) VALUES
	 (99,1),
	 (100,2),
	 (101,3),
	 (102,4);


/***********************************************************************************************************************
interaction_instance - association between bci_activity_instance and interaction.
***********************************************************************************************************************/
INSERT INTO interaction_instance (interaction_instance_id,interaction_instance_interaction_id) VALUES
	 (6,6),
	 (7,7);
INSERT INTO interaction_instance (interaction_instance_id,interaction_instance_interaction_id) VALUES
	 (55,6),
	 (56,7);


/***********************************************************************************************************************
bci_activity_instance_participants - association between bci_activity_instance and participant.
***********************************************************************************************************************/
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (1,1),
	 (1,3),
	 (1,4),
	 (2,1),
	 (2,3),
	 (2,4),
	 (3,1),
	 (3,3),
	 (3,4),
	 (4,1);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (4,3),
	 (4,4),
	 (5,1),
	 (5,3),
	 (5,4),
	 (6,1),
	 (6,3),
	 (6,4),
	 (7,1),
	 (7,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (7,4),
	 (8,1),
	 (8,3),
	 (8,4),
	 (9,1),
	 (9,3),
	 (9,4),
	 (10,1),
	 (10,3),
	 (10,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (11,1),
	 (11,3),
	 (11,4),
	 (12,1),
	 (12,3),
	 (12,4),
	 (13,1),
	 (13,3),
	 (13,4),
	 (14,1);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (14,3),
	 (14,4),
	 (15,1),
	 (15,3),
	 (15,4),
	 (16,1),
	 (16,3),
	 (16,4),
	 (17,1),
	 (17,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (17,4),
	 (18,1),
	 (18,3),
	 (18,4),
	 (19,1),
	 (19,3),
	 (19,4),
	 (20,1),
	 (20,3),
	 (20,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (21,1),
	 (21,3),
	 (21,4),
	 (22,1),
	 (22,3),
	 (22,4),
	 (23,1),
	 (23,3),
	 (23,4),
	 (24,1);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (24,3),
	 (24,4),
	 (25,1),
	 (25,3),
	 (25,4),
	 (26,1),
	 (26,3),
	 (26,4),
	 (27,1),
	 (27,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (27,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (50,2),
	 (50,3),
	 (50,4),
	 (51,2),
	 (51,3),
	 (51,4),
	 (52,2),
	 (52,3),
	 (52,4),
	 (53,2);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (53,3),
	 (53,4),
	 (54,2),
	 (54,3),
	 (54,4),
	 (55,2),
	 (55,3),
	 (55,4),
	 (56,2),
	 (56,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (56,4),
	 (57,2),
	 (57,3),
	 (57,4),
	 (58,2),
	 (58,3),
	 (58,4),
	 (59,2),
	 (59,3),
	 (59,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (60,2),
	 (60,3),
	 (60,4),
	 (61,2),
	 (61,3),
	 (61,4),
	 (62,2),
	 (62,3),
	 (62,4),
	 (63,2);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (63,3),
	 (63,4),
	 (64,2),
	 (64,3),
	 (64,4),
	 (65,2),
	 (65,3),
	 (65,4),
	 (66,2),
	 (66,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (66,4),
	 (67,2),
	 (67,3),
	 (67,4),
	 (68,2),
	 (68,3),
	 (68,4),
	 (69,2),
	 (69,3),
	 (69,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (70,2),
	 (70,3),
	 (70,4),
	 (71,2),
	 (71,3),
	 (71,4),
	 (72,2),
	 (72,3),
	 (72,4),
	 (73,2);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (73,3),
	 (73,4),
	 (74,2),
	 (74,3),
	 (74,4),
	 (75,2),
	 (75,3),
	 (75,4),
	 (76,2),
	 (76,3);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (76,4);
INSERT INTO bci_activity_instance_participants (bci_activity_instance_participants_bci_activity_instance_id,bci_activity_instance_participants_participant_id) VALUES
	 (99,5),
	 (99,3),
     (99,4),
     (100,5),
     (100,3),
     (100,4),
     (101,5),
     (101,3),
     (101,4),
	 (102,2),
	 (102,3),
     (102,4);


/***********************************************************************************************************************
bci_block_instance
***********************************************************************************************************************/
INSERT INTO bci_block_instance (bci_block_instance_id,bci_block_instance_stage,bci_block_instance_behavior_change_intervention_block_id) VALUES
	 (28,'BEGINNING',1),
	 (29,'UNSPECIFIED',2),
	 (30,'UNSPECIFIED',3),
	 (31,'UNSPECIFIED',4),
	 (32,'UNSPECIFIED',5),
	 (33,'UNSPECIFIED',6),
	 (34,'UNSPECIFIED',7),
	 (35,'UNSPECIFIED',8),
	 (36,'UNSPECIFIED',9),
	 (37,'UNSPECIFIED',10);
INSERT INTO bci_block_instance (bci_block_instance_id,bci_block_instance_stage,bci_block_instance_behavior_change_intervention_block_id) VALUES
	 (38,'UNSPECIFIED',11),
	 (39,'UNSPECIFIED',12),
	 (40,'UNSPECIFIED',13),
	 (41,'UNSPECIFIED',14);
INSERT INTO bci_block_instance (bci_block_instance_id,bci_block_instance_stage,bci_block_instance_behavior_change_intervention_block_id) VALUES
	 (77,'BEGINNING',1),
	 (78,'UNSPECIFIED',2),
	 (79,'UNSPECIFIED',3),
	 (80,'UNSPECIFIED',4),
	 (81,'UNSPECIFIED',5),
	 (82,'UNSPECIFIED',6),
	 (83,'UNSPECIFIED',7),
	 (84,'UNSPECIFIED',8),
	 (85,'UNSPECIFIED',9),
	 (86,'UNSPECIFIED',10);
INSERT INTO bci_block_instance (bci_block_instance_id,bci_block_instance_stage,bci_block_instance_behavior_change_intervention_block_id) VALUES
	 (87,'UNSPECIFIED',11),
	 (88,'UNSPECIFIED',12),
	 (89,'UNSPECIFIED',13),
	 (90,'UNSPECIFIED',14);
INSERT INTO bci_block_instance (bci_block_instance_id,bci_block_instance_stage,bci_block_instance_behavior_change_intervention_block_id) VALUES
	 (103,'BEGINNING',15),
	 (104,'UNSPECIFIED',16),
	 (105,'UNSPECIFIED',17);


/***********************************************************************************************************************
bci_block_instance_activities - association between bci_block_instance and bci_activity_instance.
***********************************************************************************************************************/
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (28,1),
	 (28,6),
	 (29,2),
	 (29,3),
	 (30,4),
	 (30,5),
	 (31,7),
	 (31,8),
	 (32,9),
	 (32,10);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (33,11),
	 (33,12),
	 (34,13),
	 (34,14),
	 (35,15),
	 (35,16),
	 (36,17),
	 (36,18),
	 (37,19),
	 (37,20);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (38,21),
	 (38,22),
	 (39,23),
	 (39,24),
	 (40,25),
	 (40,26),
	 (41,27);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (77,50),
	 (77,55),
	 (78,51),
	 (78,52),
	 (79,53),
	 (79,54),
	 (80,56),
	 (80,57),
	 (81,58),
	 (81,59);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (82,60),
	 (82,61),
	 (83,62),
	 (83,63),
	 (84,64),
	 (84,65),
	 (85,66),
	 (85,67),
	 (86,68),
	 (86,69);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (87,70),
	 (87,71),
	 (88,72),
	 (88,73),
	 (89,74),
	 (89,75),
	 (90,76);
INSERT INTO bci_block_instance_activities (bci_block_instance_activities_block_id,bci_block_instance_activities_activity_id) VALUES
	 (103,99),
	 (104,100),
	 (105,101),
	 (105,102);


/***********************************************************************************************************************
bci_phase_instance
***********************************************************************************************************************/
INSERT INTO bci_phase_instance (bci_phase_instance_id,bci_phase_instance_currentblock_id,bci_phase_instance_behavior_change_intervention_phase_id) VALUES
	 (42,28,1),
	 (43,30,2),
	 (44,32,3),
	 (45,34,4),
	 (46,36,5),
	 (47,38,6),
	 (48,40,7);
INSERT INTO bci_phase_instance (bci_phase_instance_id,bci_phase_instance_currentblock_id,bci_phase_instance_behavior_change_intervention_phase_id) VALUES
	 (91,77,1),
	 (92,79,2),
	 (93,81,3),
	 (94,83,4),
	 (95,85,5),
	 (96,87,6),
	 (97,89,7);
INSERT INTO bci_phase_instance (bci_phase_instance_id,bci_phase_instance_currentblock_id,bci_phase_instance_behavior_change_intervention_phase_id) VALUES
	 (106,103,8),
	 (107,104,9);


/***********************************************************************************************************************
bci_phase_instance_activities - association between bci_phase_instance and bci_block_instance.
***********************************************************************************************************************/
INSERT INTO bci_phase_instance_activities (bci_phase_instance_activities_phase_id,bci_phase_instance_activities_block_id) VALUES
	 (42,28),
	 (42,29),
	 (43,30),
	 (43,31),
	 (44,32),
	 (44,33),
	 (45,34),
	 (45,35),
	 (46,36),
	 (46,37);
INSERT INTO bci_phase_instance_activities (bci_phase_instance_activities_phase_id,bci_phase_instance_activities_block_id) VALUES
	 (47,38),
	 (47,39),
	 (48,40),
	 (48,41);
INSERT INTO bci_phase_instance_activities (bci_phase_instance_activities_phase_id,bci_phase_instance_activities_block_id) VALUES
	 (91,77),
	 (91,78),
	 (92,79),
	 (92,80),
	 (93,81),
	 (93,82),
	 (94,83),
	 (94,84),
	 (95,85),
	 (95,86);
INSERT INTO bci_phase_instance_activities (bci_phase_instance_activities_phase_id,bci_phase_instance_activities_block_id) VALUES
	 (96,87),
	 (96,88),
	 (97,89),
	 (97,90);
INSERT INTO bci_phase_instance_activities (bci_phase_instance_activities_phase_id,bci_phase_instance_activities_block_id) VALUES
	 (106,103),
	 (107,104),
	 (107,105);


/***********************************************************************************************************************
bci_instance
***********************************************************************************************************************/
INSERT INTO bci_instance (bci_instance_id,bci_instance_patient_id,bci_instance_currentphase_id,bci_instance_behavior_change_intervention_id) VALUES
	 (49,1,42,1),
	 (98,2,91,1),
	 (108,5,106,2);


/***********************************************************************************************************************
bci_instance_activities - association between bci_instance and bci_phase_instance.
***********************************************************************************************************************/
INSERT INTO bci_instance_activities (bci_instance_activities_bci_id,bci_instance_activities_phase_id) VALUES
	 (49,42),
	 (49,43),
	 (49,44),
	 (49,45),
	 (49,46),
	 (49,47),
	 (49,48);
INSERT INTO bci_instance_activities (bci_instance_activities_bci_id,bci_instance_activities_phase_id) VALUES
	 (98,91),
	 (98,92),
	 (98,93),
	 (98,94),
	 (98,95),
	 (98,96),
	 (98,97);
INSERT INTO bci_instance_activities (bci_instance_activities_bci_id,bci_instance_activities_phase_id) VALUES
	 (108,106),
	 (108,107);
