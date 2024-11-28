package uk.co.aosd.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.aosd.demo.Utils.randId;

import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Test the User Repository.
 *
 * @author Tony Walmsley
 */
@SpringBootTest
public class UserRepositoryTest {

    public final Language english = new LanguageJpa("BritishEnglish", "British English");

    @Autowired
    UserRespository userRespository;

    @Autowired
    LanguageRepository languageRepository;

    @Test
    public void test() {
        final var userDetails = new UserDetails(randId(), "user1", "Alice Cooper", Instant.parse("1946-01-01T12:00:00.00Z"));
        // First check whether a user exists with the username.
        assertFalse(userRespository.findByUsername(userDetails.username()).isPresent());

        final Birth beginning = new BirthJpa(randId(), userDetails.birth(), userDetails.birth());
        final Death ending = new DeathJpa(randId(), null, null);
        final Resignified named = new ResignifiedJpa(randId(), userDetails.birth(), userDetails.birth());
        final Resignified renamed = new ResignifiedJpa(randId(), null, null);

        final Signifier<String> name = new SignifierJpa<String>(randId(), userDetails.fullName(), english, named, renamed);
        final Class<Signifier<String>> names = new ClassJpa<>(randId(), Set.of(name));
        final Class<Language> languages = new ClassJpa<>(randId(), Set.of(english));
        final DNA dna = new DNAJpa(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), names, english, languages, dna, beginning, ending);

        // Save the language.
        languageRepository.save(english);

        // Save the User.
        final var saved = userRespository.save(user);

        // Restore the User and check it is correct.
        assertEquals(user, saved);

        // Confirm that the username now exists in the database.
        assertTrue(userRespository.findByUsername(userDetails.username()).isPresent());
    }

}
