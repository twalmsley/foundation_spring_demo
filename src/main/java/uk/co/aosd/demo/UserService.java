package uk.co.aosd.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        repo.save(user);
    }

}
