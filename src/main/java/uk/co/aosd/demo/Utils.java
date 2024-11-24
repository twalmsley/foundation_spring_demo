package uk.co.aosd.demo;

import java.util.UUID;

/**
 * Some useful functions.
 *
 * @author Tony Walmsley
 */
public class Utils {
    private Utils() {
    }

    public static String randId() {
        return UUID.randomUUID().toString();
    }
}
