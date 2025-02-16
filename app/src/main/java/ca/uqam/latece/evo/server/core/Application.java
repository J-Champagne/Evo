package ca.uqam.latece.evo.server.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Application class serves as the entry point for the Spring Boot application.
 * It is configured to scan core packages for component scanning and enables JPA repositories
 * through annotations @SpringBootApplication and @EnableJpaRepositories.
 * Annotations:
 * - @SpringBootApplication: Indicates a Spring Boot application and enables component scanning.
 * - @EnableJpaRepositories: Enables the creation of JPA repositories based on the defined interfaces.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootApplication(scanBasePackages={
		"ca.uqam.latece.evo.server.core",
		"ca.uqam.latece.evo.server.core.application",
		"ca.uqam.latece.evo.server.core.controller",
		"ca.uqam.latece.evo.server.core.model",
		"ca.uqam.latece.evo.server.core.repository",
		"ca.uqam.latece.evo.server.core.service"})
@EnableJpaRepositories
public class Application implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starts Testcontainers application...");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}