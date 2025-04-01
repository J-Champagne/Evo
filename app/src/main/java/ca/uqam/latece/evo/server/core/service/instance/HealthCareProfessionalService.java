package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.repository.instance.HealthCareProfessionalRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HealthCareProfessional Service.
 * @version 1.0
 * @author Julien Champagne.
 */
@Service
@Transactional
public class HealthCareProfessionalService extends AbstractEvoService<HealthCareProfessional> {
    private final Logger logger = LoggerFactory.getLogger(HealthCareProfessionalService.class);
    private static final String ERROR_EMAIL_ALREADY_REGISTERED = "HealthCareProfessional already registered with the same email!";

    @Autowired
    HealthCareProfessionalRepository hcpRepository;

    /**
     * Inserts a HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The saved HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public HealthCareProfessional create(HealthCareProfessional hcp) {
        HealthCareProfessional hcpCreated = null;

        ObjectValidator.validateObject(hcp);
        ObjectValidator.validateEmail(hcp.getEmail());
        ObjectValidator.validateString(hcp.getName());
        ObjectValidator.validateString(hcp.getContactInformation());

        // The email should be unique.
        if (this.existsByEmail(hcp.getEmail())) {
            throw this.createDuplicateHcpException(hcp);
        } else {
            hcpCreated = this.save(hcp);
            logger.info("HealthCareProfessional created: {}", hcpCreated);
        }

        return hcpCreated;
    }

    /**
     * Create duplicate HealthCareProfessional Exception.
     * @param hcp the HealthCareProfessional entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateHcpException(HealthCareProfessional hcp) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
                " HealthCareProfessional Name: " + hcp.getName() +
                ". HealthCareProfessional Email: " + hcp.getEmail());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Updates an HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The updated HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public HealthCareProfessional update(HealthCareProfessional hcp) {
        HealthCareProfessional hcpUpdated = null;
        HealthCareProfessional hcpFound = this.findById(hcp.getId());

        ObjectValidator.validateObject(hcp);
        ObjectValidator.validateEmail(hcp.getEmail());
        ObjectValidator.validateString(hcp.getName());
        ObjectValidator.validateString(hcp.getContactInformation());

        if (hcpFound.getEmail().equalsIgnoreCase(hcp.getEmail())) {
            hcpUpdated = this.save(hcp);
        } else {
            List<HealthCareProfessional> hcpEmailFound = hcpRepository.findByEmail(hcp.getEmail());

            // The email should be unique
            if (hcpEmailFound.isEmpty()) {
                hcpUpdated = this.save(hcp);
            } else {
                throw this.createDuplicateHcpException(hcp);
            }
        }

        logger.info("HealthCareProfessional updated: {}", hcpUpdated);
        return hcpUpdated;
    }

    /**
     * Method used to create or update an HealthCareProfessional.
     * @param hcp the HealthCareProfessional entity.
     * @return The inserted or updated HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *          present but does not exist in the database.
     */
    @Override
    @Transactional
    public HealthCareProfessional save(HealthCareProfessional hcp) {
        return this.hcpRepository.save(hcp);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified id exists in the repository.
     * @param id the id of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.existsById(id);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified name exists in the repository.
     * @param name the name of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return this.hcpRepository.existsByName(name);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified email exists in the repository.
     * @param email the email of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified email exists, false otherwise.
     * @throws IllegalArgumentException if the email is null.
     */
    public boolean existsByEmail(String email) {
        ObjectValidator.validateString(email);
        return this.hcpRepository.existsByEmail(email);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified contact information exists in the repository.
     * @param contactInformation the contactInformation of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified contactInformation exists, false otherwise.
     * @throws IllegalArgumentException if the contactInformation is null or blank.
     */
    public boolean existsByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return this.hcpRepository.existsByContactInformation(contactInformation);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified position exists in the repository.
     * @param position the position of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified position exists, false otherwise.
     * @throws IllegalArgumentException if the position is null or blank.
     */
    public boolean existsByPosition(String position) {
        ObjectValidator.validateString(position);
        return hcpRepository.existsByPosition(position);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified affiliation exists in the repository.
     * @param affiliation the affiliation of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified affiliation exists, false otherwise.
     * @throws IllegalArgumentException if the affiliation is null or blank.
     */
    public boolean existsByAffiliation(String affiliation) {
        ObjectValidator.validateString(affiliation);
        return hcpRepository.existsByAffiliation(affiliation);
    }

    /**
     * Checks if an HealthCareProfessional entity with the specified specialties exists in the repository.
     * @param specialties the specialties of the HealthCareProfessional to check for existence, must not be null.
     * @return true if an HealthCareProfessional with the specified specialties exists, false otherwise.
     * @throws IllegalArgumentException if the specialties is null or blank.
     */
    public boolean existsBySpecialties(String specialties) {
        ObjectValidator.validateString(specialties);
        return hcpRepository.existsBySpecialties(specialties);
    }

    /**
     * Deletes the HealthCareProfessional with the given id.
     * If the HealthCareProfessional is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        hcpRepository.deleteById(id);
        logger.info("HealthCareProfessional deleted {}", id);
    }

    /**
     * Gets all HealthCareProfessional.
     * @return all HealthCareProfessional.
     */
    @Override
    public List<HealthCareProfessional> findAll() {
        return hcpRepository.findAll().stream().toList();
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return the HealthCareProfessional with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public HealthCareProfessional findById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("HealthCareProfessional not found"));
    }

    /**
     * Finds a HealthCareProfessional by its name.
     * @param name must not be null.
     * @return the HealthCareProfessional with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<HealthCareProfessional> findByName(String name) {
        ObjectValidator.validateString(name);
        return hcpRepository.findByName(name);
    }

    /**
     * Finds a HealthCareProfessional by its email.
     * @param email must not be null.
     * @return the HealthCareProfessional the given email or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    public List<HealthCareProfessional> findByEmail(String email) {
        ObjectValidator.validateString(email);
        return hcpRepository.findByEmail(email);
    }

    /**
     * Retrieves a list of HealthCareProfessional entities that match the specified contactInformation.
     * @param contactInformation must not be null or blank.
     * @return List<HealthCareProfessional> with the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    public List<HealthCareProfessional> findByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return hcpRepository.findByContactInformation(contactInformation);
    }

    /**
     * Retrieves a list of HealthCareProfessional entities that match the specified Role Id.
     * @param roleId The Role Id to filter HealthCareProfessional entities by, must not be null.
     * @return List<HealthCareProfessional> that have the specified Role id, or an empty list if no matches are found.
     */
    public List<HealthCareProfessional> findByRole(Long roleId) {
        ObjectValidator.validateId(roleId);
        return hcpRepository.findByRole(roleId);
    }

    /**
     * Retrieves a list of HealthCareProfessional entities that match the specified position.
     * @param position must not be null or blank.
     * @return List<HealthCareProfessional> with the given position or Optional#empty() if none found.
     * @throws IllegalArgumentException if position is null or blank.
     */
    public List<HealthCareProfessional> findByPosition(String position) {
        ObjectValidator.validateString(position);
        return hcpRepository.findByPosition(position);
    }

    /**
     * Retrieves a list of HealthCareProfessional entities that match the specified affiliation.
     * @param affiliation must not be null or blank.
     * @return List<HealthCareProfessional> with the given affiliation or Optional#empty() if none found.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    public List<HealthCareProfessional> findByAffiliation(String affiliation) {
        ObjectValidator.validateString(affiliation);
        return hcpRepository.findByAffiliation(affiliation);
    }

    /**
     * Retrieves a list of HealthCareProfessional entities that match the specified specialities.
     * @param specialities must not be null or blank.
     * @return List<HealthCareProfessional> with the given specialities or Optional#empty() if none found.
     * @throws IllegalArgumentException if specialities is null ir blank.
     */
    public List<HealthCareProfessional> findBySpecialties(String specialities) {
        ObjectValidator.validateString(specialities);
        return hcpRepository.findBySpecialties(specialities);
    }
}
