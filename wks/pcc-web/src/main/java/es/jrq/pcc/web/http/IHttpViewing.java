package es.jrq.pcc.web.http;

/**
 * HTTP Interface for Viewing data
 * @author JRQ
 *
 */
public interface IHttpViewing {

    /**
     *
     * @return the Customer Global Unique Identifier
     */
    String getCustomer();

    /**
     *
     * @return the Episode Global Unique Identifier
     */
    String getEpisode();
}
