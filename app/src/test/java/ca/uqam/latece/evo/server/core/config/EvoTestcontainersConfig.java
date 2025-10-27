package ca.uqam.latece.evo.server.core.config;


import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



/**
 * Configuration class for managing Testcontainers-based PostgreSQL integration during tests.
 * <p>
 * EvoTestcontainersConfig integrates Dockerized PostgreSQL with Spring Boot tests using the Testcontainers library.
 * It provides a consistent, isolated environment for database testing, allowing seamless interaction with a PostgreSQL
 * container. This class simplifies setup and ensures proper management of the lifecycle for the container during
 * integration testing.
 * <p>
 * Key Features:
 * - Pre-configured Docker image for PostgreSQL, customizable via system property.
 * - Automatic setup and teardown of the PostgreSQL container.
 * - Easy integration with the Spring Boot application context via @ServiceConnection.
 * <p>
 * Use this class to streamline the testing process for database-dependent components, ensuring their behavior is validated
 * in an isolated, repeatable environment.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Testcontainers
public class EvoTestcontainersConfig {
    /**
     * Specifies the Docker image name used for initializing the PostgreSQL container in integration tests.
     * <p>
     * This constant defines the name and version of the Docker image for the PostgreSQL database.
     * It can be configured using the system property `evo.data.postgres.docker.image.name` or defaults to postgres:17.2
     * if the property is not specified. The value of this variable is used by the Testcontainers library to create and
     * manage a PostgreSQL container instance.
     * <p>
     * Key Details:
     * - System Property: evo.data.postgres.docker.image.name
     * - Default Value: postgres:17.2
     * <p>
     * Purpose:
     * - Facilitates consistent configuration of the Docker image for PostgreSQL integration tests.
     * - Ensures flexibility by allowing the image name to be overridden via a system property.
     */
    private static final String DOCKER_IMAGE_NAME = System.getProperty(
            "evo.data.postgres.docker.image.name",
            "postgres:17.2"
    );

    /**
     * A static Testcontainers-managed PostgreSQL container instance used for testing purposes in the Spring Boot
     * application context. This container is initialized with a specified Docker image name and provides a consistent
     * and isolated environment for integration and database-related tests.
     * <p>
     * Annotations:
     * - {@code @Container}: Marks this field as a container managed by the Testcontainers framework.
     * - {@code @ServiceConnection}: Indicates that this container provides the database connection for the application
     * during tests in the context of Spring Boot.
     * <p>
     * The container lifecycle is managed automatically by Testcontainers, ensuring a proper startup and teardown during
     * the test execution. This facilitates robust testing of database-dependent components, such as repositories and
     * services, by providing a fully isolated PostgreSQL database.
     */
    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);

    /**
     * Checks if the PostgreSQL container managed by Testcontainers is currently running.
     * @return true if the PostgreSQL container is running; false otherwise.
     */
    public final boolean isPostgresContainerRunning() {
        return postgresContainer.isRunning();
    }

}
