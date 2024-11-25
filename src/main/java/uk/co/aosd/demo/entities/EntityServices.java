package uk.co.aosd.demo.entities;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import uk.co.aosd.demo.User;
import uk.co.aosd.onto.language.Language;
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
        final List<NamingEntity> names = user.names().members().stream().map(this::toNamingEntity).toList();
        final LanguageEntity language = toLanguageEntity(user.nativeLanguage());
        final List<LanguageEntity> languages = user.languages().members().stream().map(this::toLanguageEntity).toList();
        final String dna = user.dna().identifier();
        final Instant dateOfBirth = user.beginning().from();
        final Instant dateOfDeath = user.ending().from();
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

}
