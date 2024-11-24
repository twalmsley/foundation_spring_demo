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
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.reference.ClassImpl;
import uk.co.aosd.onto.reference.DNAImpl;
import uk.co.aosd.onto.reference.LanguageImpl;
import uk.co.aosd.onto.reference.SignifierImpl;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A REST Controller for the User entity type.
 *
 * @author Tony Walmsley
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    public final Language english = new LanguageImpl("BritishEnglish", "British English");

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

        final Birth beginning = new Birth(randId(), userDetails.birth(), userDetails.birth());
        final Death ending = new Death(randId(), null, null);
        final Resignified named = new Resignified(randId(), userDetails.birth(), userDetails.birth());
        final Resignified renamed = new Resignified(randId(), null, null);

        final Signifier<String> name = new SignifierImpl<String>(randId(), userDetails.fullName(), english, named, renamed);
        final Class<Signifier<String>> names = new ClassImpl<>(randId(), Set.of(name));
        final Class<Language> languages = new ClassImpl<>(randId(), Set.of(english));
        final DNA dna = new DNAImpl(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), names, english, languages, dna, beginning, ending);

        userService.addUser(user);

        return ResponseEntity.ok(new UserDetails(user.identifier(), userDetails.username(), userDetails.fullName(), userDetails.birth()));
    }

    private boolean userExists(final String username) {
        return userService.usernameExists(username);
    }
}
