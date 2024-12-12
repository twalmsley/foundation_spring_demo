package uk.co.aosd.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.aosd.demo.Utils.randId;

import java.time.Instant;
import java.util.Set;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    public final LanguageJpa english = new LanguageJpa("BritishEnglish", "British English");

    @Autowired
    UserRespository userRespository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    SignifierRepository signifierRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    DnaRepository dnaRepository;

    @Test
    @Transactional
    public void test() {
        final var userDetails = new UserDetails(randId(), "user1", "Alice Cooper", Instant.parse("1946-01-01T12:00:00.00Z"));
        // First check whether a user exists with the username.
        assertFalse(userRespository.findByUsername(userDetails.username()).isPresent());

        final var beginning = new BirthJpa(randId(), userDetails.birth(), userDetails.birth());
        final var ending = new DeathJpa(randId(), null, null);
        final var named = new ResignifiedJpa(randId(), userDetails.birth(), userDetails.birth());
        final var renamed = new ResignifiedJpa(randId(), null, null);

        final var name = new SignifierJpa(randId(), userDetails.fullName(), english, named, renamed);
        final var names = new ClassJpa<Signifier<String>>(randId(), Set.of(name));
        final var languages = new ClassJpa<Language>(randId(), Set.of(english));
        final var dna = new DNAJpa(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), beginning, ending, names, english, languages, dna);

        // Save the language.
        languageRepository.save(english);

        // Save the events.
        eventRepository.save(beginning);

        eventRepository.save(ending);

        eventRepository.save(named);

        eventRepository.save(renamed);

        // Save the other entities.
        signifierRepository.save(name);

        classRepository.save(names);

        classRepository.save(languages);

        dnaRepository.save(dna);

        // Save the User.
        final var saved = userRespository.save(user);

        // Restore the User and check it is correct.
        assertEquals(user.getUsername(), saved.getUsername());
        assertEquals(user.getIdentifier(), saved.getIdentifier());
        assertEquals(user.getBeginning(), saved.getBeginning());
        assertEquals(user.getEnding(), saved.getEnding());
        assertEquals(user.getNames(), saved.getNames());
        assertEquals(user.getNativeLanguage(), saved.getNativeLanguage());
        assertEquals(user.getLanguages(), saved.getLanguages());
        assertEquals(user.getDna(), saved.getDna());

        // Confirm that the username now exists in the database.
        assertTrue(userRespository.findByUsername(userDetails.username()).isPresent());
    }

}
