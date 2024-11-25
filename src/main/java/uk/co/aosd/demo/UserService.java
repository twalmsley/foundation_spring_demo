package uk.co.aosd.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.aosd.demo.entities.EntityServices;

/**
 * Services related to the User entity.
 *
 * @author Tony Walmsley
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRespository repo;

    private final EntityServices entities;

    public boolean usernameExists(final String username) {
        return repo.findByUsername(username).isPresent();
    }

    public void addUser(final User user) {
        final var userEntity = entities.toUserEntity(user);
        repo.save(userEntity);
    }

}
