package uk.co.aosd.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository for accessing User entities.
 *
 * @author Tony Walmsley
 */
public interface UserRespository extends JpaRepository<User, String> {
    Optional<User> findByUsername(final String username);
}
