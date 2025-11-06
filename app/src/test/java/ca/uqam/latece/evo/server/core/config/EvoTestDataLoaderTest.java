package ca.uqam.latece.evo.server.core.config;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.ComposedOf;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
import ca.uqam.latece.evo.server.core.service.ComposedOfService;
import ca.uqam.latece.evo.server.core.service.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The EvoTestDataLoaderTest class is a Spring Boot test class designed to verify the functionality of the
 * {@link EvoDataLoader} class and its related services. It validates the behavior of the data loading process, database
 * state, and interactions with various services such as {@link RoleService}, {@link BehaviorChangeInterventionService},
 * and {@link ComposedOfService}.
 * <p>
 * This test class is annotated with @SpringBootTest to enable Spring Boot's testing support and
 * {@code @TestMethodOrder(MethodOrderer.OrderAnnotation.class)} to control the order in which test methods are executed.
 * <p>
 * Test cases include:
 * - Verifying the PostgreSQL container status.
 * - Testing the data loading process and ensuring that certain entities are properly loaded.
 * - Checking the behavior and relationships of specific entities such as Role, BehaviorChangeIntervention, and related
 * phases or blocks.
 * <p>
 * Annotations:
 * - {@code @SpringBootTest}: Enables Spring Boot's testing support.
 * - {@code @TestMethodOrder(MethodOrderer.OrderAnnotation.class)}: Specifies the execution order for test methods.
 * - {@code @Autowired}: Injects required services such as {@link RoleService}, {@link BehaviorChangeInterventionService},
 * and {@link ComposedOfService}.
 * - {@code @Test}: Marks methods as test cases.
 * - {@code @Order}: Specifies the execution order of each test method.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EvoTestDataLoaderTest extends EvoDataLoader {

    @Autowired
    private RoleService roleService;

    @Autowired
    private BehaviorChangeInterventionService bciService;

    @Autowired
    private ComposedOfService composedOfService;

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final int SIZE_1 = 1;
    private static final int SIZE_2 = 2;
    private static final int SIZE_7 = 7;
    private static BehaviorChangeIntervention bciPoc1;
    private static BehaviorChangeIntervention bciPoc2;


    @Test
    @Order(1)
    public void testCheckPostgresContainerStatus() {
        assertTrue(this.isPostgresContainerRunning());
    }

    @Test
    @Order(2)
    public void testDataLoad(){
        this.checkPostgresContainerStatus();
        List<BehaviorChangeIntervention> bciList = this.bciService.findAll();
        bciPoc1 = getBciByIdFromList(bciList, ID_1);
        bciPoc2 = getBciByIdFromList(bciList, ID_2);
        assertNotNull(bciPoc1);
        assertNotNull(bciPoc2);
    }

    private static BehaviorChangeIntervention getBciByIdFromList(List<BehaviorChangeIntervention> bciList, Long id) {
        return bciList
                .stream()
                .filter(
                        bci -> bci.getId().equals(id)
                )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("BehaviorChangeIntervention with id " + id + " not found!")
                );
    }

    @Test
    @Order(3)
    public final void testFindAll() {
        assertFalse(roleService.findAll().isEmpty());
        assertFalse(composedOfService.findAll().isEmpty());
        assertFalse(bciService.findAll().isEmpty());
    }

    @Test
    @Order(4)
    public void testBehaviorChangeInterventionById() {
        assertEquals(ID_1, bciPoc1.getId());
        assertEquals(ID_2, bciPoc2.getId());
    }

    @Test
    @Order(5)
    public void testBehaviorChangeInterventionIdOneHasSevenPhases() {
        assertEquals(SIZE_7, bciPoc1.getBehaviorChangeInterventionPhases().size());
    }

    @Test
    @Order(6)
    public void testBehaviorChangeInterventionIdTwoHasTwoPhases() {
        assertEquals(SIZE_2, bciPoc2.getBehaviorChangeInterventionPhases().size());
    }

    @Test
    @Order(7)
    public void testBehaviorChangeInterventionIdTwoPhaseOneHasOneBlock() {
        assertEquals(SIZE_1, bciPoc2.getBehaviorChangeInterventionPhases().getFirst().getBehaviorChangeInterventionBlocks().size());
    }

    @Test
    @Order(8)
    public void testBehaviorChangeInterventionIdTwoPhaseOneHasOneBlockAndOneActivity() {
        BehaviorChangeInterventionBlock bciPoc2Phase1Block1 = bciPoc2.getBehaviorChangeInterventionPhases().getFirst().getBehaviorChangeInterventionBlocks().getFirst();
        List<ComposedOf> composedOfList = composedOfService.findByBciActivityComposedOfIdAndBciBlockComposedOfId(ID_1, bciPoc2Phase1Block1.getId());
        assertEquals(SIZE_1, composedOfList.size());
    }

    @Test
    @Order(9)
    public void testFindRoleByBCIActivityId() {
        assertFalse(roleService.findByBCIActivityId(ID_1).getFirst().getBCIActivity().isEmpty());
    }

}
