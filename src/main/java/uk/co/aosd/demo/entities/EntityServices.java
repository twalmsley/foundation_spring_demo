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
import uk.co.aosd.onto.reference.events.BirthImpl;
import uk.co.aosd.onto.reference.events.DeathImpl;
import uk.co.aosd.onto.reference.events.ResignifiedImpl;
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
        final List<NamingEntity> namesList = user.getNames().getMembers().stream().map(this::toNamingEntity).toList();
        final NamesClassEntity names = new NamesClassEntity(user.getNames().getIdentifier(), namesList);
        final LanguageEntity language = toLanguageEntity(user.getNativeLanguage());
        final List<LanguageEntity> languagesList = user.getLanguages().getMembers().stream().map(this::toLanguageEntity).toList();
        final LanguagesClassEntity languages = new LanguagesClassEntity(user.getLanguages().getIdentifier(), languagesList);
        final DnaEntity dna = new DnaEntity(user.getDna().getIdentifier(), user.getDna().getDna());
        final BirthEntity dateOfBirth = new BirthEntity(user.getBeginning().getIdentifier(), user.getBeginning().getFrom(), user.getBeginning().getTo());
        final DeathEntity dateOfDeath = new DeathEntity(user.getEnding().getIdentifier(), user.getEnding().getFrom(), user.getEnding().getTo());
        return new UserEntity(user.getIdentifier(), user.getUsername(), names, language, languages, dna, dateOfBirth, dateOfDeath);
    }

    /**
     * Convert a Signifier of String to a NamingEntity.
     *
     * @param s
     *            Signifier of String
     * @return NamingEntity
     */
    public NamingEntity toNamingEntity(final Signifier<String> s) {
        final var from = new ResignifiedEntity(s.getBeginning().getIdentifier(), s.getBeginning().getFrom(), s.getBeginning().getTo());
        final var to = new ResignifiedEntity(s.getEnding().getIdentifier(), s.getEnding().getFrom(), s.getEnding().getTo());
        return new NamingEntity(s.getIdentifier(), s.getName(), new LanguageEntity(s.getLanguage().getIdentifier(), s.getLanguage().getName()), from, to);
    }

    public LanguageEntity toLanguageEntity(final Language l) {
        return new LanguageEntity(l.getIdentifier(), l.getName());
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
        final Birth beginning = new BirthImpl(e.getBeginning().getIdentifier(), e.getBeginning().getBeginning(), e.getBeginning().getEnding());
        final Death ending = new DeathImpl(e.getEnding().getIdentifier(), e.getEnding().getBeginning(), e.getEnding().getEnding());
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
        return new ResignifiedImpl(r.getIdentifier(), r.getBeginning(), r.getEnding());
    }
}
