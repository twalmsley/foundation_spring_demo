package uk.co.aosd.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.onto.jpa.LanguageJpa;

/**
 * A JPA Repository for LanguageEntity.
 *
 * @author Tony Walmsley
 */
public interface LanguageRepository extends JpaRepository<LanguageJpa, String> {

}
