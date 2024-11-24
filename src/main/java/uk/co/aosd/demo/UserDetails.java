package uk.co.aosd.demo;

import java.time.Instant;

/**
 * Information about a User.
 *
 * @author Tony Walmsley
 */
public record UserDetails(String identifier, String username, String fullName, Instant birth) {

}
