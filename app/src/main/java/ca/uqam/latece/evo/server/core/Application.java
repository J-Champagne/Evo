package ca.uqam.latece.evo.server.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Application class serves as the entry point for the Spring Boot application.
 * It is configured to scan core packages for component scanning and enables JPA repositories
 * through annotations @SpringBootApplication and @EnableJpaRepositories.
 * Annotations:
 * - @SpringBootApplication: Indicates a Spring Boot application and enables component scanning.
 * - @EnableJpaRepositories: Enables the creation of JPA repositories based on the defined interfaces.
 * - @EntityScan: Configures the base packages used by auto-configuration when scanning for entity classes.
 * <p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootApplication(scanBasePackages={
		"ca.uqam.latece.evo.server.core",
		"ca.uqam.latece.evo.server.core.application",
		"ca.uqam.latece.evo.server.core.controller",
		"ca.uqam.latece.evo.server.core.service"})
@EnableJpaRepositories("ca.uqam.latece.evo.server.core.repository")
@EntityScan("ca.uqam.latece.evo.server.core.model")
@EnableTransactionManagement
public class Application implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger(Application.class);

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running Evo+ application...");
		logger.info("Running Evo+ application...");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}