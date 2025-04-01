package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * HealthCareProfessional Controller.
 * @version 1.0
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/HealthCareProfessional")
public class HealthCareProfessionalController extends AbstractEvoController<HealthCareProfessional> {

    @Autowired
    HealthCareProfessionalService hcpService;

    /**
     * Inserts an HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The saved HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HealthCareProfessional> create(@RequestBody HealthCareProfessional hcp) {
        return Optional.ofNullable(hcpService.create(hcp)).isPresent() ?
                new ResponseEntity<>(hcp, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The updated HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<HealthCareProfessional> update(@RequestBody HealthCareProfessional hcp) {
        return Optional.ofNullable(hcpService.update(hcp)).isPresent() ?
                new ResponseEntity<>(hcp, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes the HealthCareProfessional with the given id.
     * If the HealthCareProfessional is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        hcpService.deleteById(id);
    }

    /**
     * Gets all instances of HealthCareProfessional.
     * @return List<HealthCareProfessional>.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findAll() {
        return Optional.ofNullable(hcpService.findAll()).isPresent() ?
                new ResponseEntity<>(hcpService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return the HealthCareProfessional with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<HealthCareProfessional> findById(@PathVariable Long id) {
        return Optional.ofNullable(hcpService.findById(id)).isPresent() ?
                new ResponseEntity<>(hcpService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds an HealthCareProfessional by its name.
     * @param name must not be null.
     * @return the HealthCareProfessional with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByName(@PathVariable String name) {
        return Optional.ofNullable(hcpService.findByName(name)).isPresent() ?
                new ResponseEntity<>(hcpService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds an HealthCareProfessional by its email.
     * @param email must not be null.
     * @return the HealthCareProfessional with the given email or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    @GetMapping("/find/email/{email}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByEmail(@PathVariable String email) {
        return Optional.ofNullable(hcpService.findByEmail(email)).isPresent() ?
                new ResponseEntity<>(hcpService.findByEmail(email), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds an HealthCareProfessional by its role id.
     * @param id must not be null.
     * @return the HealthCareProfessional with the given role id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByRole(@PathVariable Long id) {
        return Optional.ofNullable(hcpService.findByRole(id)).isPresent() ?
                new ResponseEntity<>(hcpService.findByRole(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds an HealthCareProfessional by its contact information.
     * @param contactInformation must not be null or blank.
     * @return the HealthCareProfessional with the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if the contactInformation is null or blank.
     */
    @GetMapping("/find/contactInformation/{contactInformation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByContactInformation(@PathVariable String contactInformation) {
        return Optional.ofNullable(hcpService.findByContactInformation(contactInformation)).isPresent() ?
                new ResponseEntity<>(hcpService.findByContactInformation(contactInformation), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds all instances of HealthCareProfessional by their position.
     * @param position the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given position or Optional#empty() if none found.
     * @throws IllegalArgumentException if position is null or blank.
     */
    @GetMapping("/find/position/{position}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByPosition(@PathVariable String position) {
        return Optional.ofNullable(hcpService.findByPosition(position)).isPresent() ?
                new ResponseEntity<>(hcpService.findByPosition(position), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds all instances of HealthCareProfessional by their affiliation.
     * @param affiliation the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given affiliation or Optional#empty() if none found.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    @GetMapping("/find/affiliation/{affiliation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByAffiliation(@PathVariable String affiliation) {
        return Optional.ofNullable(hcpService.findByAffiliation(affiliation)).isPresent() ?
                new ResponseEntity<>(hcpService.findByAffiliation(affiliation), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds all instances of HealthCareProfessional by their specialties.
     * @param specialties the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given specialties or Optional#empty() if none found.
     * @throws IllegalArgumentException if specialties is null or blank.
     */
    @GetMapping("/find/specialties/{specialties}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findBySpecialties(@PathVariable String specialties) {
        return Optional.ofNullable(hcpService.findBySpecialties(specialties)).isPresent() ?
                new ResponseEntity<>(hcpService.findBySpecialties(specialties), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
