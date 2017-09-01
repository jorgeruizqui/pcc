package es.jrq.pcc.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;
import es.jrq.pcc.core.model.Viewing;
import es.jrq.pcc.core.model.royalty.DefaultRoyaltyCalculator;

/**
 * Royalty Manager Service studios Test class.
 * All test methods focused on testing studios service in Royalty Manager
 * Service
 *
 * @author JRQ
 *
 */
public class RoyaltyManagerServiceStudiosTest {

    /**
     * Instance of the Royalty Manager Service
     */
    private static RoyaltyManagerService royaltyManagerService;

    /**
     * Setting up the royalty manager service
     *
     * @throws Exception if any error occurs during service instantiation.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        royaltyManagerService = RoyaltyManagerService.getInstance();
    }

    /**
     * Save and retrieve several studios test.
     */
    @Test
    public void saveAndRetreivestudiosTest() {
        String studioId1 = UUID.randomUUID().toString();
        String studioName1 = UUID.randomUUID().toString();
        Studio studio1 = new Studio(studioId1, studioName1, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio1);

        Studio storedstudio1 = royaltyManagerService.getStudioById(studioId1);
        assertNotNull(storedstudio1);
        assertEquals(storedstudio1.getId(), studioId1);

        // Check list of elements has just one element
        assertEquals(1, royaltyManagerService.getStudios().size());

        String studioId2 = UUID.randomUUID().toString();
        String studioName2 = UUID.randomUUID().toString();
        Studio studio2 = new Studio(studioId2, studioName2, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio2);

        Studio storedstudio2 = royaltyManagerService.getStudioById(studioId2);
        assertNotNull(storedstudio2);
        assertEquals(storedstudio2.getId(), studioId2);

        // Check list of elements has two element
        assertEquals(2, royaltyManagerService.getStudios().size());

        // Leave Data Store in the initial state
        royaltyManagerService.removeStudioById(studioId1);
        storedstudio1 = royaltyManagerService.getStudioById(studioId1);
        assertNull(storedstudio1);
        royaltyManagerService.removeStudioById(studioId2);
        storedstudio2 = royaltyManagerService.getStudioById(studioId2);
        assertNull(storedstudio2);

        // Check list of elements is empty
        assertEquals(0, royaltyManagerService.getStudios().size());
    }

    /**
     * Save and retrieve new studio test.
     */
    @Test
    public void saveAndRetreivestudioTest() {
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        Studio studio = new Studio(studioId, studioName, BigDecimal.ZERO, BigInteger.ZERO);

        royaltyManagerService.saveStudio(studio);
        Studio storedStudio = royaltyManagerService.getStudioById(studioId);
        assertNotNull(storedStudio);
        assertEquals(storedStudio.getId(), studioId);

        // Leave Data Store in the initial state
        royaltyManagerService.removeStudioById(studioId);
        storedStudio = royaltyManagerService.getStudioById(studioId);
        assertNull(storedStudio);
    }

    /**
     * Retrieve non existing studio test.
     */
    @Test
    public void retrieveNonExistingstudioTest() {
        String studioId = UUID.randomUUID().toString();
        Studio nonExistingStudio = royaltyManagerService.getStudioById(studioId);
        assertNull(nonExistingStudio);
    }

    /**
     * Register a new viewing Test.
     */
    @Test
    public void registerViewingTest() {
        String episodeId = UUID.randomUUID().toString();
        String episodeName = UUID.randomUUID().toString();
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        String customerId = UUID.randomUUID().toString();

        // Save the episode to pass the episode validation
        royaltyManagerService.saveEpisode(new Episode(episodeId, episodeName, studioId));

        // Check studio has been saved correctly
        Studio studio = new Studio(studioId, studioName, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio);
        Studio storedStudio = royaltyManagerService.getStudioById(studioId);

        assertNotNull(storedStudio);
        assertEquals(storedStudio.getId(), studioId);

        // Obtain previous viewings and royalties of this studio
        Integer previousViewings = storedStudio.getViewings().intValue();
        BigDecimal previousRoyalties = storedStudio.getPayment();

        // Register the viewing
        royaltyManagerService.viewing(new Viewing(episodeId, customerId));

        // Check studio viewings has been incremented correctly
        storedStudio = royaltyManagerService.getStudioById(studioId);
        assertNotNull(storedStudio);
        assertEquals(storedStudio.getId(), studioId);
        assertEquals(previousViewings + 1, storedStudio.getViewings().intValue());
        assertEquals(previousRoyalties.add(new BigDecimal(DefaultRoyaltyCalculator.DEFAULT_AMOUNT)),
                storedStudio.getPayment());

        royaltyManagerService.removeStudioById(studioId);
        royaltyManagerService.removeEpisodeById(episodeId);
    }

    /**
     * Register an invalid viewing Test.
     */
    @Test
    public void registerInvalidViewingTest() {
        String episodeId = UUID.randomUUID().toString();
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        String customerId = UUID.randomUUID().toString();

        // Do not save the episode to avoid passing the episode validation

        // Check studio has been saved correctly
        Studio studio = new Studio(studioId, studioName, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio);
        Studio storedStudio = royaltyManagerService.getStudioById(studioId);
        assertNotNull(storedStudio);
        assertEquals(storedStudio.getId(), studioId);

        // Obtain previous viewings of this studio
        Integer previousViewings = storedStudio.getViewings().intValue();

        // Register the viewing
        royaltyManagerService.viewing(new Viewing(episodeId, customerId));

        // Check studio viewings has been incremented correctly
        storedStudio = royaltyManagerService.getStudioById(studioId);
        assertNotNull(storedStudio);
        assertEquals(storedStudio.getId(), studioId);
        assertEquals(previousViewings.intValue(), storedStudio.getViewings().intValue());

        royaltyManagerService.removeStudioById(studioId);
    }

    /**
     * Reset counters test
     */
    @Test
    public void resetCountersTest() {
        String studioId1 = UUID.randomUUID().toString();
        String studioName1 = UUID.randomUUID().toString();
        Studio studio1 = new Studio(studioId1, studioName1, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio1);
        String studioId2 = UUID.randomUUID().toString();
        String studioName2 = UUID.randomUUID().toString();
        Studio studio2 = new Studio(studioId2, studioName2, BigDecimal.ZERO, BigInteger.ZERO);
        royaltyManagerService.saveStudio(studio2);
        // Save episodes for both studios
        String episodeId1 = UUID.randomUUID().toString();
        String episodeName1 = UUID.randomUUID().toString();
        Episode episode1 = new Episode(episodeId1, episodeName1, studioId1);
        royaltyManagerService.saveEpisode(episode1);
        String episodeId2 = UUID.randomUUID().toString();
        String episodeName2 = UUID.randomUUID().toString();
        Episode episode2 = new Episode(episodeId2, episodeName2, studioId2);
        royaltyManagerService.saveEpisode(episode2);
        // Register viewings for both studies
        royaltyManagerService.viewing(new Viewing(episodeId1, UUID.randomUUID().toString()));
        royaltyManagerService.viewing(new Viewing(episodeId2, UUID.randomUUID().toString()));

        // Assert viewings have been registered correctly.
        Studio storedstudio1 = royaltyManagerService.getStudioById(studioId1);
        assertNotNull(storedstudio1);
        assertEquals(storedstudio1.getId(), studioId1);
        assertEquals(1, storedstudio1.getViewings().intValue());
        Studio storedstudio2 = royaltyManagerService.getStudioById(studioId2);
        assertNotNull(storedstudio2);
        assertEquals(storedstudio2.getId(), studioId2);
        assertEquals(1, storedstudio2.getViewings().intValue());

        // Force reset counters and assert counters are set to Zero
        royaltyManagerService.reset();
        storedstudio1 = royaltyManagerService.getStudioById(studioId1);
        assertNotNull(storedstudio1);
        assertEquals(storedstudio1.getId(), studioId1);
        assertEquals(0, storedstudio1.getViewings().intValue());
        storedstudio2 = royaltyManagerService.getStudioById(studioId2);
        assertNotNull(storedstudio2);
        assertEquals(storedstudio2.getId(), studioId2);
        assertEquals(0, storedstudio2.getViewings().intValue());

        // Leave Data Store in the initial state
        royaltyManagerService.removeEpisodeById(episodeId1);
        royaltyManagerService.removeEpisodeById(episodeId2);
        royaltyManagerService.removeStudioById(studioId1);
        royaltyManagerService.removeStudioById(studioId2);

    }

}
