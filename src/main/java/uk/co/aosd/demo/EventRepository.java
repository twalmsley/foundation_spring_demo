package uk.co.aosd.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.onto.jpa.events.EventJpa;

/**
 * A JPA Repository for Event.
 *
 * @author Tony Walmsley
 */
public interface EventRepository extends JpaRepository<EventJpa, String> {

}
