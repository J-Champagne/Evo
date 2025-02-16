package ca.uqam.latece.evo.server.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Evo repository creates CRUD implementation at runtime automatically.
 * @param <T> the Evo model.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@NoRepositoryBean
public interface EvoRepository <T> extends JpaRepository<T, Long> {

}
