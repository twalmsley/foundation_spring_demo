package uk.co.aosd.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.aosd.demo.Utils.randId;

import java.time.Instant;
import java.util.Set;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.jpa.ClassJpa;
import uk.co.aosd.onto.jpa.DNAJpa;
import uk.co.aosd.onto.jpa.LanguageJpa;
import uk.co.aosd.onto.jpa.SignifierJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * Test the User Service.
 */
@SpringBootTest
public class UserServiceTest {

    private final LanguageJpa english = new LanguageJpa("BritishEnglish", "British English");

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void test() {
        final var userDetails = new UserDetails(randId(), "user1", "Alice Cooper", Instant.parse("1946-01-01T12:00:00.00Z"));

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

        final var byUsername = userService.getUser(user.getIdentifier());
        assertTrue(byUsername.isPresent());
        assertTrue(byUsername.get().getBeginning() instanceof BirthJpa);
        assertTrue(byUsername.get().getEnding() instanceof DeathJpa);

        final Birth birth = (Birth) byUsername.get().getBeginning();
        final Death death = (Death) byUsername.get().getEnding();

        assertEquals(birth, beginning);
        assertEquals(death, ending);
    }
}
