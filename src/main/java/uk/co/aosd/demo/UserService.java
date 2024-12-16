package uk.co.aosd.demo;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.ClassJpa;
import uk.co.aosd.onto.jpa.DNAJpa;
import uk.co.aosd.onto.jpa.LanguageJpa;
import uk.co.aosd.onto.jpa.SignifierJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * Services related to the User entity.
 *
 * @author Tony Walmsley
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRespository repo;
    private final LanguageRepository languageRepository;
    private final EventRepository eventRepository;
    private final SignifierRepository signifierRepository;
    private final ClassRepository classRepository;
    private final DnaRepository dnaRepository;

    public boolean usernameExists(final String username) {
        return repo.findByUsername(username).isPresent();
    }

    /**
     * Get a user from the repository.
     *
     * @param id
     *            the user's ID
     * @return the user
     */
    public Optional<User> getUser(final String id) {
        return repo.findById(id);
    }

    /**
     * Add a user to the repository.
     *
     * @param user
     *            the user to add
     * @param english
     *            the language
     * @param beginning
     *            the birth event
     * @param ending
     *            the death event
     * @param named
     *            the resignified event
     * @param renamed
     *            the resignified event
     * @param name
     *            the signifier
     * @param names
     *            the class of signifiers
     * @param languages
     *            the class of languages
     * @param dna
     *            the dna
     */
    public void addUser(
        final User user,
        final LanguageJpa english,
        final BirthJpa beginning,
        final DeathJpa ending,
        final ResignifiedJpa named,
        final ResignifiedJpa renamed,
        final Signifier<String, ResignifiedJpa> name,
        final Class<Signifier<String, ResignifiedJpa>> names,
        final Class<LanguageJpa> languages,
        final DNAJpa dna) {

        final var nativeLanguage = languageRepository.findById(english.getIdentifier());
        if (nativeLanguage.isEmpty()) {
            languageRepository.save((LanguageJpa) english);
        }

        // Save the events.
        eventRepository.save(beginning);

        eventRepository.save(ending);

        eventRepository.save(named);

        eventRepository.save(renamed);

        // Save the other entities.
        signifierRepository.save((SignifierJpa) name);

        classRepository.save((ClassJpa<Signifier<String, ResignifiedJpa>>) names);

        classRepository.save((ClassJpa<LanguageJpa>) languages);

        dnaRepository.save((DNAJpa) dna);
        repo.save(user);
    }

}
