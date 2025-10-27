package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
import ca.uqam.latece.evo.server.core.service.ComposedOfService;
import ca.uqam.latece.evo.server.core.service.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * The Role Controller test class for the {@link RoleController}, ensuring proper integration with the database by
 * leveraging the {@link AbstractEvoDataControllerTest} functionality. It is responsible for testing the CRUD operations
 * using data loaded in Evo+ database.
 * <p>
 * The tests in this class are executed in a specific order, as denoted by the {@link MethodOrderer.OrderAnnotation}, to
 * ensure logical workflows are validated.
 * </p>
 * Annotations:
 * - {@code @SpringBootTest}: Enables Spring Boot's testing support.
 * - {@code @TestMethodOrder(MethodOrderer.OrderAnnotation.class)}: Specifies the execution order for test methods.
 * - {@code @Autowired}: Injects required services such as {@link RoleService}, {@link BehaviorChangeInterventionService},
 * and {@link ComposedOfService}.
 * - {@code @Test}: Marks methods as test cases.
 * - {@code @Order}: Specifies the execution order of each test method.
 * <p>
 * Methods:
 * - {@code setUp()}: Sets up the environment before each test execution.
 * - {@code testCreate()}: Validates entity creation.
 * - {@code testUpdate()}: Validates updates to the internal state of an entity.
 * - {@code testCreateRequestBadRequest()}: Handles and validates scenarios causing bad request errors.
 * - {@code testDeleteById()}: Validates the functionality of deleting an entity by its ID.
 * - {@code testFindById()}: Tests entity retrieval by ID.
 * - {@code testFindByName()}: Tests entity retrieval by name.
 * - {@code testFindByBCIActivityId()}: Tests entity retrieval by BCI Activity ID.
 * - {@code testFindAll()}: Tests retrieval of all entities from a data source.
 * - {@code testNotFound()}: Validates scenarios where a resource or entity cannot be found.
 * </p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleControllerDbTest extends AbstractEvoDataControllerTest {

    @Autowired
    private RoleService roleService;

    private static Role role;
    private static Long roleId;


    @Test
    @Order(1)
    void testCreate() {
        role = new Role();
        role.setName("Database Test 0");
        role.setDescription("Role Database Test Description 0!");

        // Create a new role in the database via the controller class.
        roleId = this.performCreateRequest("/roles", role);
        assert roleId > 0;
    }

    @Test
    @Order(2)
    void testUpdate() {
        role.setId(roleId);
        role.setDescription("Role Database Test Description Updated!");
        this.performUpdateRequest("/roles", role);
    }

    @Test
    @Order(3)
    void testCreateRequestBadRequest() {
        this.performCreateRequestWithBadRequest("/roles", new Role());
    }

    @Test
    @Order(4)
    void testDeleteById() {
        this.performDeleteRequest("/roles/{id}", roleId);
    }

    @Test
    @Order(5)
    void testFindById() {
        this.performGetRequest("/roles/find/{id}", 1,"id",1L);
    }

    @Test
    @Order(6)
    void testFindByName() {
        this.performGetRequest("/roles/find/name/{name}","Participant","name","Participant");
    }

    @Test
    @Order(7)
    void testFindByBCIActivityId() {
        this.performGetRequest("/roles/find/{id}",1, "id", 1L);
    }

    @Test
    @Order(8)
    void testFindAll() {
        this.performGetRequest("/roles");
    }

    @Test
    @Order(9)
    void testNotFound() {
        this.performGetRequestNotFound("/roles/find/{id}", 0L);
    }
    
}
