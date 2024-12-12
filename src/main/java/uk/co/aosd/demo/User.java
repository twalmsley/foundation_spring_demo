package uk.co.aosd.demo;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.HumanJpa;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A Human user of the system.
 *
 * @author Tony Walmsley
 */
@Entity(name = "SYSTEM_USERS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends HumanJpa {

    private String username;

    /**
     * Constructor.
     */
    public User(final String identifier, final String username, final Birth beginning, final Death ending, final Class<Signifier<String>> names,
        final Language nativeLanguage, final Class<Language> languages, final DNA dna) {
        super(identifier, beginning, ending, names, nativeLanguage, languages, dna);
        this.username = username;
    }
}
