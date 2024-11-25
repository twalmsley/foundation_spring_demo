package uk.co.aosd.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A JPA representation of a User.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    String identifier;

    String username;

    @OneToOne(cascade = CascadeType.MERGE)
    NamesClassEntity names;

    @OneToOne
    LanguageEntity nativeLanguage;

    @OneToOne(cascade = CascadeType.ALL)
    LanguagesClassEntity languages;

    @OneToOne(cascade = CascadeType.ALL)
    DnaEntity dna;

    @OneToOne(cascade = CascadeType.ALL)
    BirthEntity beginning;

    @OneToOne(cascade = CascadeType.ALL)
    DeathEntity ending;
}
