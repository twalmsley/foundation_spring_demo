package uk.co.aosd.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.aosd.onto.biological.DNA;
import uk.co.aosd.onto.biological.Human;
import uk.co.aosd.onto.events.Birth;
import uk.co.aosd.onto.events.Death;
import uk.co.aosd.onto.foundation.Class;
import uk.co.aosd.onto.language.Language;
import uk.co.aosd.onto.signifying.Signifier;

/**
 * A Human user of the system.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Human {
    @Id
    private String identifier;
    private String username;
    private Class<Signifier<String>> names;
    private Language nativeLanguage;
    private Class<Language> languages;
    private DNA dna;
    private Birth beginning;
    private Death ending;
}
