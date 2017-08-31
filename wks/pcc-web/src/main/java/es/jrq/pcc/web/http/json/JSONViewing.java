package es.jrq.pcc.web.http.json;

import es.jrq.pcc.core.model.Viewing;
import es.jrq.pcc.web.http.IHttpViewing;

/**
 * JSON implementation for HTTP Viewing interface object
 *
 * @author JRQ
 *
 */
public class JSONViewing implements IHttpViewing {

    /** Episode identifier. */
    private String episode;

    /** Customer identifier. */
    private String customer;

    /**
     * Default Constructor.
     */
    public JSONViewing() {
        this.episode = "";
        this.customer = "";
    }

    /**
     * @param episode The episode Identifier
     * @param customer The customer Identifier
     */
    public JSONViewing(String episode, String customer) {
        this.episode = episode;
        this.customer = customer;
    }

    @Override
    public String getCustomer() {
        return episode;
    }

    @Override
    public String getEpisode() {
        return customer;
    }

    /**
     * @param episode the episode to set
     */
    public void setEpisode(String episode) {
        this.episode = episode;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     *
     * @param viewing Viewing entity to convert
     * @return The Viewing entity converted into JSON Model
     */
    public static JSONViewing model2JSON(Viewing viewing) {
        return new JSONViewing(
                viewing.getEpisode(),
                viewing.getCustomer());
    }

    /**
    *
    * @param viewing Viewing entity to convert
    * @return The Viewing entity converted into JSON Model
    */
   public static Viewing json2model(JSONViewing viewing) {
       return new Viewing(
               viewing.getEpisode(),
               viewing.getCustomer());
   }
}
