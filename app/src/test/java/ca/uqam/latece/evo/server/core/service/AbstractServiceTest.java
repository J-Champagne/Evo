package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.config.EvoTestcontainersConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


/**
 * Abstract base class for service layer unit tests, designed to standardize the structure and methodology for testing
 * CRUD operations and related functionality within a given service. By extending this class, implementers gain access
 * to a common set of abstract test methods which must be overridden to validate the behavior of specific service
 * implementations.
 * <p>
 * This class provides a foundation for defining tests related to persistence operations on entities, ensuring consistency
 * and correctness across different service layers in the application.
 * <p>
 * The class is annotated with:
 * - @DataJpaTest: To configure a JPA-specific application context for testing.
 * - @AutoConfigureTestDatabase: To configure an external database for tests without replacing it.
 * - @ComponentScan: To locate components, services, and repositories within specified base packages.
 * <p>
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"ca.uqam.latece.evo.server.core.repository",
        "ca.uqam.latece.evo.server.core.service", "ca.uqam.latece.evo.server.core"})
public abstract class AbstractServiceTest extends EvoTestcontainersConfig {

    /**
     * Abstract method to test the functionality of saving an entity in the database.
     * Implementations of this method should verify that the entity is correctly persisted
     * and retrievable after being saved.
     * <p>
     * This method should also handle scenarios like validation constraints, database constraints,
     * and any exception handling related to the save operation.
     */
    abstract void testSave();

    /**
     * Abstract method to test the update functionality in the database.
     * <p>
     * Implementations of this method should verify that updates to an entity
     * are correctly applied and persisted. This includes checking that the
     * modified entity fields reflect the desired changes after the update operation.
     * <p>
     * The method should also consider handling of scenarios such as:
     * - Updating entities with validation or database constraints
     * - Exceptions that may arise during the update process
     * - Proper handling of non-existent entities or invalid update requests
     */
    abstract void testUpdate();

    /**
     * Abstract method to test the functionality of finding entities by their id in the database.
     * <p>
     * Implementations of this method should verify that the correct entities are retrieved
     * when queried by id. This includes ensuring that the query matches the expected records
     * based on their id criteria.
     * <p>
     * The method should also consider handling scenarios such as:
     * - Exact id matching.
     * - Behavior when no entities match the given id.
     * - Handling of invalid or null input values.
     */
    abstract void testFindById();

    /**
     * Abstract method to test the functionality of deleting an entity by its identifier in the database.
     * <p>
     * Implementations of this method should verify that the entity with the given identifier is
     * correctly deleted and no longer retrievable after the delete operation. This includes ensuring
     * that associated data and constraints are managed appropriately.
     * <p>
     * The method should also consider handling scenarios such as:
     * - Attempting to delete a non-existent entity
     * - Validation of the identifier passed to the delete operation
     * - Exceptions that may arise during the delete process
     * - Handling of cascading deletes or related entities, if applicable
     */
    abstract void testDeleteById();

    /**
     * Abstract method to test the functionality of retrieving all entities from the database.
     * <p>
     * Implementations of this method should verify that all entities stored in the database
     * are correctly retrieved. This includes ensuring that the retrieved list matches the
     * expected entities present in the database at the time of the test.
     * <p>
     * The method should also address scenarios such as:
     * - Handling when no entities are present in the database
     * - Ensuring the order of the entities in the result matches the expected order, if applicable
     * - Validating the completeness and correctness of the retrieved data
     * - Handling exceptions or errors during the operation
     */
    abstract void testFindAll();
}
