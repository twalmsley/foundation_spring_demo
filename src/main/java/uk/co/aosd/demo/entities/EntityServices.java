package uk.co.aosd.demo.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import uk.co.aosd.demo.User;
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
 * Services for transforming entities.
 *
 * @author Tony Walmsley
 */
@Service
public class EntityServices {

    /**
     * Convert a User to a UserEntity.
     *
     * @param user
     *            User
     * @return UserEntity
     */
    public UserEntity toUserEntity(final User user) {
        final List<NamingEntity> namesList = user.names().members().stream().map(this::toNamingEntity).toList();
        final NamesClassEntity names = new NamesClassEntity(user.names().identifier(), namesList);
        final LanguageEntity language = toLanguageEntity(user.nativeLanguage());
        final List<LanguageEntity> languagesList = user.languages().members().stream().map(this::toLanguageEntity).toList();
        final LanguagesClassEntity languages = new LanguagesClassEntity(user.languages().identifier(), languagesList);
        final DnaEntity dna = new DnaEntity(user.dna().identifier(), user.dna().dna());
        final BirthEntity dateOfBirth = new BirthEntity(user.beginning().identifier(), user.beginning().from(), user.beginning().to());
        final DeathEntity dateOfDeath = new DeathEntity(user.ending().identifier(), user.ending().from(), user.ending().to());
        return new UserEntity(user.identifier(), user.username(), names, language, languages, dna, dateOfBirth, dateOfDeath);
    }

    /**
     * Convert a Signifier of String to a NamingEntity.
     *
     * @param s
     *            Signifier of String
     * @return NamingEntity
     */
    public NamingEntity toNamingEntity(final Signifier<String> s) {
        final var from = new ResignifiedEntity(s.beginning().identifier(), s.beginning().from(), s.beginning().to());
        final var to = new ResignifiedEntity(s.ending().identifier(), s.ending().from(), s.ending().to());
        return new NamingEntity(s.identifier(), s.name(), new LanguageEntity(s.language().identifier(), s.language().name()), from, to);
    }

    public LanguageEntity toLanguageEntity(final Language l) {
        return new LanguageEntity(l.identifier(), l.name());
    }

    /**
     * Convert a UserEntity to a User.
     *
     * @param e
     *            UserEntity
     * @return User
     */
    public User fromUserEntity(final UserEntity e) {
        final Class<Language> languages = toLanguagesClassEntities(e.getLanguages());
        final DNA dna = new DNAImpl(e.getDna().getIdentifier(), e.getDna().getDna());
        final Birth beginning = new Birth(e.getBeginning().getIdentifier(), e.getBeginning().getBeginning(), e.getBeginning().getEnding());
        final Death ending = new Death(e.getEnding().getIdentifier(), e.getEnding().getBeginning(), e.getEnding().getEnding());
        return new User(e.getIdentifier(), e.getUsername(), toSignifierClass(e.getNames()), toLanguage(e.getNativeLanguage()), languages, dna, beginning,
            ending);
    }

    private Class<Language> toLanguagesClassEntities(final LanguagesClassEntity languages) {
        final var members = languages.getLanguages().stream().map(this::toLanguage).collect(Collectors.toSet());
        return new ClassImpl<>(languages.getIdentifier(), members);
    }

    private Language toLanguage(final LanguageEntity l) {
        return new LanguageImpl(l.getIdentifier(), l.getName());
    }

    private Class<Signifier<String>> toSignifierClass(final NamesClassEntity names) {
        final var members = names.getNames().stream().map(this::toSignifier).collect(Collectors.toSet());
        return new ClassImpl<>(names.getIdentifier(), members);
    }

    private Signifier<String> toSignifier(final NamingEntity n) {
        return new SignifierImpl<String>(n.getIdentifier(), n.getName(), toLanguage(n.getLanguage()), toResignified(n.getBeginning()),
            toResignified(n.getEnding()));
    }

    private Resignified toResignified(final ResignifiedEntity r) {
        return new Resignified(r.getIdentifier(), r.getBeginning(), r.getEnding());
    }
}
