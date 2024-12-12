package uk.co.aosd.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.onto.jpa.DNAJpa;

/**
 * A JPA Repository for DnaEntity.
 */
public interface DnaRepository extends JpaRepository<DNAJpa, String> {

}
