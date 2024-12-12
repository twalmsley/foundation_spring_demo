package uk.co.aosd.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main class.
 */
@SpringBootApplication
@EntityScan(basePackages = { "uk.co.aosd.demo", "uk.co.aosd.onto.jpa", "uk.co.aosd.onto.jpa.events" })
public class DemoApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
