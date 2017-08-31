package es.jrq.pcc.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Royalty Manager Spring Boot application, instantiating the web
 * layer, controllers and HTTP Converters
 * It also initializes the Backend Royalty Manager Service.
 *
 * @author JRQ
 *
 */
@SpringBootApplication(scanBasePackages = {"es.jrq.pcc.web"})
public class RoyaltyManagerSpringBootApp {

    /**
     * Default Constructor.
     */
    public RoyaltyManagerSpringBootApp() {
    }
}
