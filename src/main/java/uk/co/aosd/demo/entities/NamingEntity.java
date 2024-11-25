package uk.co.aosd.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA representation for Naming entities.
 *
 * @author Tony Walmsley
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamingEntity {

    @Id
    String identifier;

    String name;

    @OneToOne
    LanguageEntity language;

    @OneToOne(cascade = CascadeType.ALL)
    ResignifiedEntity beginning;

    @OneToOne(cascade = CascadeType.ALL)
    ResignifiedEntity ending;
}
