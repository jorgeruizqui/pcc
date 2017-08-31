package es.jrq.pcc.web.http.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;

/**
 * JSON Utility class. Used for loading JSON example files and converting into Model entities.
 * @author Jor
 *
 */
public final class JSONUtils {

    /** Studios array field in json file. */
    private static final String STUDIO_ARRAY_FIELD = "studios";

    /** Studio ID field in json file. */
    private static final String STUDIO_ID_FIELD = "id";

    /** Studio Name field in json file. */
    private static final String STUDIO_NAME_FIELD = "name";

    /** Studio payment field in json file. */
    private static final String STUDIO_PAYMENT_FIELD = "payment";

    /** Episodes array field in json file. */
    private static final String EPISODE_ARRAY_FIELD = "episodes";

    /** Episode ID field in json file. */
    private static final String EPISODE_ID_FIELD = "id";

    /** Episode Name field in json file. */
    private static final String EPISODE_NAME_FIELD = "name";

    /** Episode rights owner field in json file. */
    private static final String EPISODE_RIGHTS_OWNER_FIELD = "rightsowner";

    /**
     * Avoid public constructor for utility classes.
     */
    private JSONUtils() {
    }

    /**
     * @param jsonFile JSON studios file
     * @return List of {@link Studio} according to the studios JSON data file
     */
    @SuppressWarnings("unchecked")
    public static List<Studio> parseJsonStudiosFile(String jsonFile) {

        List<Studio> rto = new ArrayList<Studio>();

        InputStream is = JSONUtils.class.getResourceAsStream(jsonFile);
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(is));
            JSONArray jsonArray = (JSONArray) jsonObject.get(STUDIO_ARRAY_FIELD);
            jsonArray.forEach(new Consumer<JSONObject>() {

                public void accept(JSONObject t) {
                    String studioName = (String) t.get(STUDIO_NAME_FIELD);
                    String studioId = (String) t.get(STUDIO_ID_FIELD);
                    Number payment = (Number) t.get(STUDIO_PAYMENT_FIELD);

                    Studio studio = new Studio(studioId, studioName, new BigDecimal(payment.doubleValue()),
                            BigInteger.ZERO);
                    rto.add(studio);
                };
            });
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return rto;
    }

    /**
     * @param jsonFile JSON studios file
     * @return List of {@link Episode} according to the studios JSON data file
     */
    @SuppressWarnings("unchecked")
    public static List<Episode> parseJsonEpisodesFile(String jsonFile) {

        List<Episode> rto = new ArrayList<Episode>();

        InputStream is = JSONUtils.class.getResourceAsStream(jsonFile);
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(is));
            JSONArray jsonArray = (JSONArray) jsonObject.get(EPISODE_ARRAY_FIELD);
            jsonArray.forEach(new Consumer<JSONObject>() {

                public void accept(JSONObject t) {
                    String episodeName = (String) t.get(EPISODE_NAME_FIELD);
                    String episodeId = (String) t.get(EPISODE_ID_FIELD);
                    String studioId = (String) t.get(EPISODE_RIGHTS_OWNER_FIELD);

                    Episode episode = new Episode(episodeId, episodeName, studioId);
                    rto.add(episode);
                };
            });
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return rto;
    }
}
