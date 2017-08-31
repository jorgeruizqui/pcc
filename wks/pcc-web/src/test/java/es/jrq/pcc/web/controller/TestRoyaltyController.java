package es.jrq.pcc.web.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;
import es.jrq.pcc.core.service.RoyaltyManagerService;
import es.jrq.pcc.web.controller.json.RoyaltyManagerController;
import es.jrq.pcc.web.http.json.JSONHttpMessageConverter;
import es.jrq.pcc.web.http.json.JSONViewing;

/**
 * Main test class for Royalty Controller based in JSON type requests.
 * This test suite is in charge of testing all Royalty Manager endpoints.
 *
 * @author JRQ
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RoyaltyManagerController.class)
public class TestRoyaltyController {

    /** HTTP Response ACCEPTED code. */
    private static final int RESPONSE_ACCEPTED_CODE = 202;

    /** HTTP Response OK code. */
    private static final int RESPONSE_OK_CODE = 200;

    /** HTTP Response NOT_FOUND code. */
    private static final int RESPONSE_NOT_FOUND_CODE = 404;

    /** Number of iterations for Payments test. */
    private static final int NUMBER_OF_PAYMENTS_TEST = 100;

    /**
     * Instance of the Spring Mock Model View Controller
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Reset test
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerResetTest() throws Exception {

        // Save a random studio with some random viewings
        Random r = new Random();
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        double royalties = r.nextDouble();

        // Make sure viewgins is not set to zero.
        long viewings = r.nextLong() + 1;

        Studio studio = new Studio(studioId, studioName, new BigDecimal(royalties), BigInteger.valueOf(viewings));
        RoyaltyManagerService.getInstance().saveStudio(studio);

        // Perform the http post call and check return value.
        mockMvc.perform(post("/royaltymanager/reset")).andExpect(status().isAccepted())
                .andExpect(status().is(HttpStatus.ACCEPTED.value())).andExpect(status().is(RESPONSE_ACCEPTED_CODE));

        // Get the manager and check counters are set to 0
        Studio storedStudio = RoyaltyManagerService.getInstance().getStudioById(studioId);
        assertEquals(0, storedStudio.getViewings().intValue());

        RoyaltyManagerService.getInstance().removeStudioById(studioId);

    }

    /**
     * Viewing test
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerViewingTest() throws Exception {

        Random r = new Random();

        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        double royalties = 0.0;
        long viewings = r.nextInt();
        Studio studio = new Studio(studioId, studioName, new BigDecimal(royalties), BigInteger.valueOf(viewings));
        RoyaltyManagerService.getInstance().saveStudio(studio);

        String episodeId = UUID.randomUUID().toString();
        String episodeName = UUID.randomUUID().toString();
        Episode episode = new Episode(episodeId, episodeName, studioId);
        RoyaltyManagerService.getInstance().saveEpisode(episode);

        JSONViewing v = new JSONViewing();
        v.setCustomer(UUID.randomUUID().toString());
        v.setEpisode(episodeId);

        mockMvc.perform(post("/royaltymanager/viewing").contentType(MediaType.APPLICATION_JSON)
                .content(JSONHttpMessageConverter.convertObjectToJsonBytes(v))).andExpect(status().isAccepted())
                .andExpect(status().is(HttpStatus.ACCEPTED.value())).andExpect(status().is(RESPONSE_ACCEPTED_CODE));

        // Check viewings are incremented
        Studio storedStudio = RoyaltyManagerService.getInstance().getStudioById(studioId);
        assertEquals(viewings + 1, storedStudio.getViewings().intValue());

        RoyaltyManagerService.getInstance().removeStudioById(studioId);
        RoyaltyManagerService.getInstance().removeEpisodeById(episodeId);
    }

    /**
     * Payment test. Test the endpoint specific for a concrete Rights Owner.
     * Launch the request just with one instance of Studio and check JSON
     * response is according to requisites
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerPaymentTest() throws Exception {
        Random r = new Random();
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        double royalties = 0.0;
        long viewings = r.nextLong();
        Studio studio = new Studio(studioId, studioName, new BigDecimal(royalties), BigInteger.valueOf(viewings));
        RoyaltyManagerService.getInstance().saveStudio(studio);

        mockMvc.perform(get("/royaltymanager/payments/" + studioId))
                .andExpect(status().isOk()).andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(status().is(RESPONSE_OK_CODE))
                .andExpect(jsonPath("$.rightsownerId", Matchers.is(studioId)))
                .andExpect(jsonPath("$.rightsowner", Matchers.is(studioName)))
                .andExpect(jsonPath("$.royalty", Matchers.is(royalties)))
                .andExpect(jsonPath("$.viewings", Matchers.is(viewings))).andDo(MockMvcResultHandlers.print());

        RoyaltyManagerService.getInstance().removeStudioById(studioId);
    }

    /**
     * Payment Endpoint test for a non existing right Owner. Expected empty body and 404 response
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerPaymentNonExistingStudioTest() throws Exception {

        mockMvc.perform(get("/royaltymanager/payments/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound()).andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(status().is(RESPONSE_NOT_FOUND_CODE))
                .andExpect(content().string(Matchers.isEmptyString()))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * Payments test. Test just with one instance of Studio and check JSON
     * response
     * is according to requisites
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerPaymentsTest() throws Exception {
        Random r = new Random();
        String studioId = UUID.randomUUID().toString();
        String studioName = UUID.randomUUID().toString();
        double royalties = 0.0;
        long viewings = r.nextLong();
        Studio studio = new Studio(studioId, studioName, new BigDecimal(royalties), BigInteger.valueOf(viewings));
        RoyaltyManagerService.getInstance().saveStudio(studio);

        mockMvc.perform(get("/royaltymanager/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(status().is(RESPONSE_OK_CODE)).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].rightsownerId", Matchers.is(studioId)))
                .andExpect(jsonPath("$[0].rightsowner", Matchers.is(studioName)))
                .andExpect(jsonPath("$[0].royalty", Matchers.is(royalties)))
                .andExpect(jsonPath("$[0].viewings", Matchers.is(viewings))).andDo(MockMvcResultHandlers.print());

        RoyaltyManagerService.getInstance().removeStudioById(studioId);
    }

    /**
     * Payments test with <code>NUMBER_OF_PAYMENTS</code> cases.
     *
     * @throws Exception If any error occurs during execution
     */
    @Test
    public void royaltyManagerLotsOfPaymentsTest() throws Exception {
        String studioIdPrefix = "STUDIO";
        String studioNamePrefix = "NAME";
        double royaltiesBase = 0.0d;
        long viewingsBase = 0L;

        for (int i = 0; i < NUMBER_OF_PAYMENTS_TEST; i++) {
            Studio studio = new Studio(studioIdPrefix + i, studioNamePrefix + i, new BigDecimal(royaltiesBase + i),
                    BigInteger.valueOf(viewingsBase + i));
            RoyaltyManagerService.getInstance().saveStudio(studio);

        }

        mockMvc.perform(get("/royaltymanager/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(status().is(RESPONSE_OK_CODE))
                .andExpect(jsonPath("$", Matchers.hasSize(NUMBER_OF_PAYMENTS_TEST)))
                .andDo(MockMvcResultHandlers.print());

        for (int i = 0; i < NUMBER_OF_PAYMENTS_TEST; i++) {
            RoyaltyManagerService.getInstance().removeStudioById(studioIdPrefix + i);
        }
    }

}
