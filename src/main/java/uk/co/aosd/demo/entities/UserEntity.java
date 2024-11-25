package uk.co.aosd.demo.entities;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.MERGE)
    List<NamingEntity> names;

    @OneToOne
    LanguageEntity nativeLanguage;

    @OneToMany
    List<LanguageEntity> languages;

    String dna;

    Instant dateOfBirth;

    Instant dateOfDeath;
}
