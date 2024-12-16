package uk.co.aosd.demo;

import static uk.co.aosd.demo.Utils.randId;

import java.util.Set;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.aosd.onto.jpa.ClassJpa;
import uk.co.aosd.onto.jpa.DNAJpa;
import uk.co.aosd.onto.jpa.LanguageJpa;
import uk.co.aosd.onto.jpa.SignifierJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A REST Controller for the User entity type.
 *
 * @author Tony Walmsley
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public final LanguageJpa english = new LanguageJpa("BritishEnglish", "British English");

    private final UserService userService;

    /**
     * A GET method to return a user given its ID in a path variable.
     */
    @GetMapping(path = "{id}")
    @Transactional
    public ResponseEntity<UserDetails> getUser(@PathVariable final String id) {
        final var user = userService.getUser(id);

        return user
            .map(u -> ResponseEntity
                .ok(new UserDetails(u.getIdentifier(), u.getUsername(), u.getNames().getMembers().iterator().next().getName(), u.getBeginning().getFrom())))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Add a new User if not already present.
     *
     * @param userDetails
     *            UserDetails
     * @return UserDetails
     */
    @PostMapping
    @Transactional
    public ResponseEntity<UserDetails> addUser(@RequestBody final UserDetails userDetails) {
        // First check whether a user exists with the username.
        if (userExists(userDetails.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        final var beginning = new BirthJpa(randId(), userDetails.birth(), userDetails.birth());
        final var ending = new DeathJpa(randId(), null, null);
        final var named = new ResignifiedJpa(randId(), userDetails.birth(), userDetails.birth());
        final var renamed = new ResignifiedJpa(randId(), null, null);

        final var name = new SignifierJpa(randId(), userDetails.fullName(), english, named, renamed);
        final var names = new ClassJpa<Signifier<String, ResignifiedJpa>>(randId(), Set.of(name));
        final var languages = new ClassJpa<LanguageJpa>(randId(), Set.of(english));
        final var dna = new DNAJpa(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), beginning, ending, names, english, languages, dna);

        userService.addUser(user, english, beginning, ending, named, renamed, name, names, languages, dna);

        return ResponseEntity.ok(new UserDetails(user.getIdentifier(), userDetails.username(), userDetails.fullName(), userDetails.birth()));
    }

    private boolean userExists(final String username) {
        return userService.usernameExists(username);
    }
}
