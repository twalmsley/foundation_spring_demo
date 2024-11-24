package uk.co.aosd.demo.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    String id;

    String username;

    String fullName;

    Instant dateOfBirth;
}
