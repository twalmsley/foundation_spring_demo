package uk.co.aosd.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.onto.foundation.UniquelyIdentifiable;
import uk.co.aosd.onto.jpa.ClassJpa;

/**
 * A JPA Repository for ClassEntity.
 */
public interface ClassRepository extends JpaRepository<ClassJpa<? extends UniquelyIdentifiable>, String> {

}
