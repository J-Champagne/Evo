package ca.uqam.latece.evo.server.core.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;


/**
 * The EvoDataLoader class extends the functionality of the EvoTestcontainersConfig class to manage PostgreSQL container
 * lifecycles and database setup during test execution. It is configured with SQL scripts for schema and data initialization
 * and provides utility methods for container status checks.
 * <p>
 * The configuration includes:
 * - SQL scripts for setting up the database schema and data.
 * - Isolation of transactions during test execution.
 * <p>
 * Annotations:
 * - {@code @Sql}: Specifies SQL scripts to be executed before the test class execution and configures the transaction
 *   mode as ISOLATED.
 * - {@code @Component}: Indicates that this test class is a Spring-managed component.
 * - {@code @Configuration}: Indicates that this class is a Spring configuration class.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Sql(scripts = {
        "classpath:schema.sql",
        "file:${evo.data.loader.data.location}"
},
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Component
@Configuration
public class EvoDataLoader extends EvoTestcontainersConfig {

    /**
     * Checks whether the PostgreSQL container is running and prints the corresponding status message. This method checks
     * the state of the PostgreSQL container managed by Testcontainers to ensure that it is active and capable of supporting
     * database operations during test execution.
     * <p>
     * If the container is running, the method outputs "Database is running!" to the console.
     * Otherwise, it outputs "Database is not running!".
     */
    public final void checkPostgresContainerStatus() {
        if (this.isPostgresContainerRunning()) {
            System.out.println("Database is running!");
        } else {
            System.out.println("Database is not running!");
        }
    }

}
