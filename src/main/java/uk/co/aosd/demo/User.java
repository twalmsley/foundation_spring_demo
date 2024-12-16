package uk.co.aosd.demo;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.jpa.HumanJpa;
import uk.co.aosd.onto.jpa.LanguageJpa;
import uk.co.aosd.onto.jpa.events.BirthJpa;
import uk.co.aosd.onto.jpa.events.DeathJpa;
import uk.co.aosd.onto.jpa.events.ResignifiedJpa;
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
    public User(final String identifier, final String username, final BirthJpa beginning, final DeathJpa ending,
        final Class<Signifier<String, ResignifiedJpa>> names, final LanguageJpa nativeLanguage, final Class<LanguageJpa> languages, final DNA dna) {
        super(identifier, beginning, ending, names, nativeLanguage, languages, dna);
        this.username = username;
    }
}
