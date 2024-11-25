package uk.co.aosd.demo.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Set of LanguageEntities.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguagesClassEntity {

    @Id
    String identifier;

    @OneToMany(cascade = CascadeType.ALL)
    List<LanguageEntity> languages;
}
