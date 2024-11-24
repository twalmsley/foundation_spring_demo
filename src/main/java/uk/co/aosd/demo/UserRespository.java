package uk.co.aosd.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.aosd.demo.entities.UserEntity;

/**
 * A repository for accessing User entities.
 *
 * @author Tony Walmsley
 */
public interface UserRespository extends JpaRepository<UserEntity, String> {
    Optional<User> findByUsername(final String username);
}
