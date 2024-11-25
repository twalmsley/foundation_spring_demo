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
import uk.co.aosd.demo.entities.EntityServices;
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
 * Test the User Repository.
 *
 * @author Tony Walmsley
 */
@SpringBootTest
public class UserRepositoryTest {

    public final Language english = new LanguageImpl("BritishEnglish", "British English");

    @Autowired
    UserRespository userRespository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    EntityServices entityServices;

    @Test
    public void test() {
        final var userDetails = new UserDetails(randId(), "user1", "Alice Cooper", Instant.parse("1946-01-01T12:00:00.00Z"));
        // First check whether a user exists with the username.
        assertFalse(userRespository.findByUsername(userDetails.username()).isPresent());

        final Birth beginning = new Birth(randId(), userDetails.birth(), userDetails.birth());
        final Death ending = new Death(randId(), null, null);
        final Resignified named = new Resignified(randId(), userDetails.birth(), userDetails.birth());
        final Resignified renamed = new Resignified(randId(), null, null);

        final Signifier<String> name = new SignifierImpl<String>(randId(), userDetails.fullName(), english, named, renamed);
        final Class<Signifier<String>> names = new ClassImpl<>(randId(), Set.of(name));
        final Class<Language> languages = new ClassImpl<>(randId(), Set.of(english));
        final DNA dna = new DNAImpl(randId(), "unknown");

        final var user = new User(randId(), userDetails.username(), names, english, languages, dna, beginning, ending);

        // Save the language.
        languageRepository.save(entityServices.toLanguageEntity(english));

        // Save the User.
        final var userEntity = entityServices.toUserEntity(user);
        final var saved = userRespository.save(userEntity);

        assertEquals(userEntity, saved);

        assertTrue(userRespository.findByUsername(userDetails.username()).isPresent());
    }

}
