package uk.co.aosd.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.onto.jpa.SignifierJpa;

/**
 * A JPA Repository for SignifierEntity.
 */
public interface SignifierRepository extends JpaRepository<SignifierJpa, String> {

}
