package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.Role;
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
 * @author Julien Champagne.
 */
@Service
@Transactional
public class HealthCareProfessionalService extends AbstractEvoService<HealthCareProfessional> {
    private static final Logger logger = LoggerFactory.getLogger(HealthCareProfessionalService.class);
    private static final String ERROR_EMAIL_ALREADY_REGISTERED = "HealthCareProfessional already registered with the same email!";

    @Autowired
    HealthCareProfessionalRepository hcpRepository;

    /**
     * Creates a HealthCareProfessional in the database.
     * @param hcp HealthCareProfessional.
     * @return The created HealthCareProfessional.
     * @throws IllegalArgumentException if hcp is null or if another HealthCareProfessional was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public HealthCareProfessional create(HealthCareProfessional hcp) {
        HealthCareProfessional hcpCreated;

        ObjectValidator.validateObject(hcp);
        ObjectValidator.validateEmail(hcp.getEmail());
        ObjectValidator.validateString(hcp.getName());
        ObjectValidator.validateString(hcp.getContactInformation());

        // The email should be unique.
        if (existsByEmail(hcp.getEmail())) {
            throw this.createDuplicateHcpException(hcp);
        } else {
            hcpCreated = this.save(hcp);
        }

        return hcpCreated;
    }

    /**
     * Updates a HealthCareProfessional in the database.
     * @param hcp HealthCareProfessional.
     * @return The updated HealthCareProfessional.
     * @throws IllegalArgumentException if hcp is null or if another HealthCareProfessional was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    public HealthCareProfessional update(HealthCareProfessional hcp) {
        HealthCareProfessional hcpUpdated;
        HealthCareProfessional hcpFound = this.findById(hcp.getId());

        ObjectValidator.validateObject(hcp);
        ObjectValidator.validateEmail(hcp.getEmail());
        ObjectValidator.validateString(hcp.getName());
        ObjectValidator.validateString(hcp.getContactInformation());

        // The email should be the same or unique
        if (hcpFound.getEmail().equalsIgnoreCase(hcp.getEmail())) {
            hcpUpdated = this.save(hcp);
        } else {
            if (existsByEmail(hcp.getEmail())) {
                throw this.createDuplicateHcpException(hcp);
            } else {
                hcpUpdated = this.save(hcp);
            }
        }

        logger.info("HealthCareProfessional updated: {}", hcpUpdated);
        return hcpUpdated;
    }

    /**
     * Saves the given HealthCareProfessional in the database.
     * @param hcp HealthCareProfessional.
     * @return The saved HealthCareProfessional.
     * @throws IllegalArgumentException if hcp is null or if another HealthCareProfessional was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public HealthCareProfessional save(HealthCareProfessional hcp) {
        return this.hcpRepository.save(hcp);
    }

    /**
     * Deletes a HealthCareProfessional by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        hcpRepository.deleteById(id);
        logger.info("HealthCareProfessional deleted {}", id);
    }

    /**
     * Finds all HealthCareProfessional entities.
     * @return List<HealthCareProfessional>.
     */
    @Override
    public List<HealthCareProfessional> findAll() {
        return hcpRepository.findAll();
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id Long.
     * @return HealthCareProfessional with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public HealthCareProfessional findById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("HealthCareProfessional not found"));
    }

    /**
     * Finds HealthCareProfessional entities by their name.
     * @param name String.
     * @return HealthCareProfessional with the given name.
     * @throws IllegalArgumentException if name is null or blank.
     */
    public List<HealthCareProfessional> findByName(String name) {
        ObjectValidator.validateString(name);
        return hcpRepository.findByName(name);
    }

    /**
     * Finds a HealthCareProfessional by its email.
     * @param email String.
     * @return HealthCareProfessional with the given email.
     * @throws IllegalArgumentException if email is null or blank.
     */
    public HealthCareProfessional findByEmail(String email) {
        ObjectValidator.validateEmail(email);
        return hcpRepository.findByEmail(email);
    }

    /**
     * Finds HealthCareProfessional entities by their contactInformation.
     * @param contactInformation String.
     * @return List<HealthCareProfessional> with the given contactInformation.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    public List<HealthCareProfessional> findByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return hcpRepository.findByContactInformation(contactInformation);
    }

    /**
     * Finds HealthCareProfessional entities by their Role.
     * @param role Role.
     * @return List<HealthCareProfessional> with the specified Role.
     * @throws IllegalArgumentException if role is null.
     */
    public List<HealthCareProfessional> findByRole(Role role) {
        ObjectValidator.validateObject(role);
        return hcpRepository.findByRole(role);
    }

    /**
     * Finds HealthCareProfessional entities by their Role id.
     * @param id Long.
     * @return List<HealthCareProfessional> with the specified Role id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<HealthCareProfessional> findByRoleId(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.findByRoleId(id);
    }

    /**
     * Finds HealthCareProfessional entities by their position.
     * @param position String.
     * @return List<HealthCareProfessional> with the given position.
     * @throws IllegalArgumentException if position is null or blank.
     */
    public List<HealthCareProfessional> findByPosition(String position) {
        ObjectValidator.validateString(position);
        return hcpRepository.findByPosition(position);
    }

    /**
     * Finds HealthCareProfessional entities by their affiliation.
     * @param affiliation String.
     * @return List<HealthCareProfessional> with the given affiliation.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    public List<HealthCareProfessional> findByAffiliation(String affiliation) {
        ObjectValidator.validateString(affiliation);
        return hcpRepository.findByAffiliation(affiliation);
    }

    /**
     * Finds HealthCareProfessional entities by their specialities.
     * @param specialities String.
     * @return List<HealthCareProfessional> with the given specialities.
     * @throws IllegalArgumentException if specialities is null or blank.
     */
    public List<HealthCareProfessional> findBySpecialties(String specialities) {
        ObjectValidator.validateString(specialities);
        return hcpRepository.findBySpecialties(specialities);
    }

    /**
     * Checks if a HealthCareProfessional exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.existsById(id);
    }

    /**
     * Checks if a HealthCareProfessional exists in the database by its email
     * @param email String
     * @return boolean
     * @throws IllegalArgumentException if email is null or blank.
     */
    public boolean existsByEmail(String email) {
        ObjectValidator.validateString(email);
        return this.hcpRepository.existsByEmail(email);
    }

    /**
     * Creates an IllegalArgumentException with a message indicating that a HealthCareProfessional with the same email was found.
     * @param hcp HealthCareProfessional.
     * @return IllegalArgumentException.
     */
    private IllegalArgumentException createDuplicateHcpException(HealthCareProfessional hcp) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
                " HealthCareProfessional Name: " + hcp.getName() +
                ". HealthCareProfessional Email: " + hcp.getEmail());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }
}
