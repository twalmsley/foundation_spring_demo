package uk.co.aosd.demo;

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
public record User(String identifier, String username, Class<Signifier<String>> names, Language nativeLanguage, Class<Language> languages, DNA dna,
    Birth beginning,
    Death ending) implements Human {

}
