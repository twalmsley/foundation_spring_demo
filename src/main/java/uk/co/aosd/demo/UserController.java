package uk.co.aosd.demo;

import static uk.co.aosd.demo.Utils.randId;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.events.Resignified;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.ClassJpa;
import uk.co.aosd.onto.jpa.DNAJpa;
import uk.co.aosd.onto.jpa.LanguageJpa;
import uk.co.aosd.onto.jpa.SignifierJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A REST Controller for the User entity type.
 *
 * @author Tony Walmsley
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    public final Language english = new LanguageJpa("BritishEnglish", "British English");

    private final UserService userService;

    /**
     * Add a new User if not already present.
     *
     * @param userDetails
     *            UserDetails
     * @return UserDetails
     */
    @PostMapping(name = "/add-user")
    public ResponseEntity<UserDetails> addUser(@RequestBody final UserDetails userDetails) {
        // First check whether a user exists with the username.
        if (userExists(userDetails.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        final Birth beginning = new BirthJpa(randId(), userDetails.birth(), userDetails.birth());
        final Death ending = new DeathJpa(randId(), null, null);
        final Resignified named = new ResignifiedJpa(randId(), userDetails.birth(), userDetails.birth());
        final Resignified renamed = new ResignifiedJpa(randId(), null, null);

        final Signifier<String> name = new SignifierJpa(randId(), userDetails.fullName(), english, named, renamed);
        final Class<Signifier<String>> names = new ClassJpa<>(randId(), Set.of(name));
        final Class<Language> languages = new ClassJpa<>(randId(), Set.of(english));
        final DNA dna = new DNAJpa(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), beginning, ending, names, english, languages, dna);

        userService.addUser(user);

        return ResponseEntity.ok(new UserDetails(user.getIdentifier(), userDetails.username(), userDetails.fullName(), userDetails.birth()));
    }

    private boolean userExists(final String username) {
        return userService.usernameExists(username);
    }
}
