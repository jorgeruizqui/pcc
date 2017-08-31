package es.jrq.pcc.web;

import org.springframework.boot.SpringApplication;

import es.jrq.pcc.core.service.RoyaltyManagerService;
import es.jrq.pcc.web.http.json.JSONUtils;

/**
 * Main Royalty Manager Web application startup.
 * It is in charge of running the spring boot application, instantiating the web
 * layer, controllers and HTTP Converters
 * It also initializes the Backend Royalty Manager Service and loads initial data
 * according to JSON configuration files.
 *
 * @author JRQ
 *
 */
public final class RoyaltyManagerWebLauncher {

    /** Episodes initial data JSON file. */
    private static final String EPISODES_JSON_DATA = "/es/jrq/pcc/data/episodes.json";

    /** Studios initial data JSON file. */
    private static final String STUDIOS_JSON_DATA = "/es/jrq/pcc/data/studios.json";

    /**
     * Avoid public constructors in Utility classes.
     */
    private RoyaltyManagerWebLauncher() {
    }

    /**
     * Main launcher
     *
     * @param args Program Arguments.
     *        Currently, no arguments are expected
     */
    public static void main(String[] args) {

        // Creates and launch the springboot app.
        SpringApplication.run(RoyaltyManagerSpringBootApp.class, args);

        // Instantiate the RoyatyManager
        RoyaltyManagerService.getInstance();

        // Initializes Data Base with example data.
        RoyaltyManagerService.getInstance().loadEpisodes(
                JSONUtils.parseJsonEpisodesFile(EPISODES_JSON_DATA));

        RoyaltyManagerService.getInstance().loadStudios(
                JSONUtils.parseJsonStudiosFile(STUDIOS_JSON_DATA));

    }
}
