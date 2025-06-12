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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HealthCareProfessional Service.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
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
     */
    @Override
    @Transactional
    public HealthCareProfessional save(HealthCareProfessional hcp) {
        return this.hcpRepository.save(hcp);
    }

    /**
     * Deletes a HealthCareProfessional by its id.
     * Silently ignored if not found.
     * @param id The HealthCareProfessional id.
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
     * @return List of HealthCareProfessionals.
     */
    @Override
    public List<HealthCareProfessional> findAll() {
        return hcpRepository.findAll();
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id The HealthCareProfessional id.
     * @return HealthCareProfessional with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public HealthCareProfessional findById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("HealthCareProfessional not found!"));
    }

    /**
     * Finds HealthCareProfessional entities by their name.
     * @param name The HealthCareProfessional name.
     * @return HealthCareProfessional with the given name.
     * @throws IllegalArgumentException if name is null or blank.
     */
    public List<HealthCareProfessional> findByName(String name) {
        ObjectValidator.validateString(name);
        return hcpRepository.findByName(name);
    }

    /**
     * Finds a HealthCareProfessional by its email.
     * @param email the HealthCareProfessional email.
     * @return HealthCareProfessional with the given email.
     * @throws IllegalArgumentException if email is null or blank.
     */
    public HealthCareProfessional findByEmail(String email) {
        ObjectValidator.validateEmail(email);
        return hcpRepository.findByEmail(email);
    }

    /**
     * Finds HealthCareProfessional entities by their contactInformation.
     * @param contactInformation the HealthCareProfessional contact information.
     * @return List of HealthCareProfessionals with the given contactInformation.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    public List<HealthCareProfessional> findByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return hcpRepository.findByContactInformation(contactInformation);
    }

    /**
     * Finds HealthCareProfessional entities by their position.
     * @param position the HealthCareProfessional position.
     * @return List of HealthCareProfessionals with the given position.
     * @throws IllegalArgumentException if position is null or blank.
     */
    public List<HealthCareProfessional> findByPosition(String position) {
        ObjectValidator.validateString(position);
        return hcpRepository.findByPosition(position);
    }

    /**
     * Finds HealthCareProfessional entities by their affiliation.
     * @param affiliation the HealthCareProfessional affiliation.
     * @return List of HealthCareProfessionals with the given affiliation.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    public List<HealthCareProfessional> findByAffiliation(String affiliation) {
        ObjectValidator.validateString(affiliation);
        return hcpRepository.findByAffiliation(affiliation);
    }

    /**
     * Finds HealthCareProfessional entities by their specialities.
     * @param specialities the HealthCareProfessional specialities.
     * @return List of HealthCareProfessionals with the given specialities.
     * @throws IllegalArgumentException if specialities are null or blank.
     */
    public List<HealthCareProfessional> findBySpecialties(String specialities) {
        ObjectValidator.validateString(specialities);
        return hcpRepository.findBySpecialties(specialities);
    }

    /**
     * Checks if a HealthCareProfessional exists in the database by its id
     * @param id The HealthCareProfessional id.
     * @return true if exists, otherwise false.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return hcpRepository.existsById(id);
    }

    /**
     * Checks if a HealthCareProfessional exists in the database by its email
     * @param email The HealthCareProfessional email.
     * @return true if exists, otherwise false.
     * @throws IllegalArgumentException if email is null or blank.
     */
    public boolean existsByEmail(String email) {
        ObjectValidator.validateEmail(email);
        return this.hcpRepository.existsByEmail(email);
    }

    /**
     * Creates an IllegalArgumentException with a message indicating that a HealthCareProfessional with the same email was found.
     * @param hcp HealthCareProfessional.
     * @return IllegalArgumentException indicating that a HealthCareProfessional with the same email was found.
     */
    private IllegalArgumentException createDuplicateHcpException(HealthCareProfessional hcp) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
                " HealthCareProfessional Name: " + hcp.getName() +
                ". HealthCareProfessional Email: " + hcp.getEmail());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }
}
