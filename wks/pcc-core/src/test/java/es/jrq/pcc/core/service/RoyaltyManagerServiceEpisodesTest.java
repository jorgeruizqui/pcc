package es.jrq.pcc.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import es.jrq.pcc.core.model.Episode;

/**
 * Royalty Manager Service episodes Test class.
 * All test methods focused on testing episodes service in Royalty Manager Service
 * @author JRQ
 *
 */
public class RoyaltyManagerServiceEpisodesTest {

    /**
     * Instance of the Royalty Manager Service
     */
    private static RoyaltyManagerService royaltyManagerService;

    /**
     * Setting up the royalty manager service
     * @throws Exception if any error occurs during service instantiation.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        royaltyManagerService = RoyaltyManagerService.getInstance();
    }

    /**
     * Save and retrieve several episodes test.
     */
    @Test
    public void saveAndRetreiveEpisodesTest() {

        String studioId = UUID.randomUUID().toString();

        String episodeId1 = UUID.randomUUID().toString();
        String episodeName1 = UUID.randomUUID().toString();
        Episode episode1 = new Episode(episodeId1, episodeName1, studioId);
        royaltyManagerService.saveEpisode(episode1);

        Episode storedEpisode1 = royaltyManagerService.getEpisodeById(episodeId1);
        assertNotNull(storedEpisode1);
        assertEquals(storedEpisode1.getEpisodeId(), episodeId1);

        // Check list of elements has just one element
        assertEquals(1, royaltyManagerService.getEpisodes().size());

        String episodeId2 = UUID.randomUUID().toString();
        String episodeName2 = UUID.randomUUID().toString();
        Episode episode2 = new Episode(episodeId2, episodeName2, studioId);
        royaltyManagerService.saveEpisode(episode2);

        Episode storedEpisode2 = royaltyManagerService.getEpisodeById(episodeId2);
        assertNotNull(storedEpisode2);
        assertEquals(storedEpisode2.getEpisodeId(), episodeId2);

        // Check list of elements has two element
        assertEquals(royaltyManagerService.getEpisodes().size(), 2);

        // Leave Data Store in the initial state
        royaltyManagerService.removeEpisodeById(episodeId1);
        storedEpisode1 = royaltyManagerService.getEpisodeById(episodeId1);
        assertNull(storedEpisode1);
        royaltyManagerService.removeEpisodeById(episodeId2);
        storedEpisode2 = royaltyManagerService.getEpisodeById(episodeId2);
        assertNull(storedEpisode2);

        // Check list of elements is empty
        assertEquals(royaltyManagerService.getEpisodes().size(), 0);
    }

    /**
     * Save and retrieve new episode test.
     */
    @Test
    public void saveAndRetreiveEpisodeTest() {
        String studioId = UUID.randomUUID().toString();
        String episodeName = UUID.randomUUID().toString();
        String episodeId = UUID.randomUUID().toString();
        Episode episode = new Episode(episodeId, episodeName, studioId);

        royaltyManagerService.saveEpisode(episode);
        Episode storedEpisode = royaltyManagerService.getEpisodeById(episodeId);
        assertNotNull(storedEpisode);
        assertEquals(storedEpisode.getEpisodeId(), episodeId);

        // Leave Data Store in the initial state
        royaltyManagerService.removeEpisodeById(episodeId);
        storedEpisode = royaltyManagerService.getEpisodeById(episodeId);
        assertNull(storedEpisode);
    }

    /**
     * Retrieve non existing episode test.
     */
    @Test
    public void retrieveNonExistingepisodeTest() {
        String episodeId = UUID.randomUUID().toString();
        Episode nonExistingEpisode = royaltyManagerService.getEpisodeById(episodeId);
        assertNull(nonExistingEpisode);
    }

}
