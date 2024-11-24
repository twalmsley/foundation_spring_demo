package uk.co.aosd.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.aosd.demo.entities.UserEntity;

/**
 * Services related to the User entity.
 *
 * @author Tony Walmsley
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRespository repo;

    public boolean usernameExists(final String username) {
        return repo.findByUsername(username).isPresent();
    }

    public void addUser(final User user) {
        final var userEntity = new UserEntity(user.identifier(), user.username(), user.names().members().iterator().next().name(), user.beginning().from());
        repo.save(userEntity);
    }

}
