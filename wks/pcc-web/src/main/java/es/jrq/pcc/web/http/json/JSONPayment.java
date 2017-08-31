package es.jrq.pcc.web.http.json;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import es.jrq.pcc.core.model.Studio;

/**
 * JSON implementation for HTTP Payment interface object.
 * This object will be a wrapper for {@link Studio} model entity.
 *
 * @author JRQ
 *
 */
public class JSONPayment {

    /** Rights Owner Identifier (studio identifier). */
    private String rightsownerId;

    /** Rights Owner Name. */
    private String rightsowner;

    /** Royalties for the corresponding studio. */
    private double royalty;

    /** Number of viewings for the corresponding studio. */
    private long viewings;

    /**
     * @return the rightsownerId
     */
    public String getRightsownerId() {
        return rightsownerId;
    }

    /**
     * @param rightsownerId the rightsownerId to set
     */
    public void setRightsownerId(String rightsownerId) {
        this.rightsownerId = rightsownerId;
    }

    /**
     * @return the rightsowner
     */
    public String getRightsowner() {
        return rightsowner;
    }

    /**
     * @param rightsowner the rightsowner to set
     */
    public void setRightsowner(String rightsowner) {
        this.rightsowner = rightsowner;
    }

    /**
     * @return the royalty
     */
    public double getRoyalty() {
        return royalty;
    }

    /**
     * @param royalty the royalty to set
     */
    public void setRoyalty(double royalty) {
        this.royalty = royalty;
    }

    /**
     * @return the viewings
     */
    public long getViewings() {
        return viewings;
    }

    /**
     * @param viewings the viewings to set
     */
    public void setViewings(long viewings) {
        this.viewings = viewings;
    }

    /**
     *
     * @param studio Entity to convert into JSON object
     * @return A JSON Payment object converted from Studio model entity
     */
    public static JSONPayment model2json(Studio studio) {
        // Make sure the format is as required in requisites
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.CEILING);
        double royaltyJsonFormat = studio.getPayment().doubleValue();
        try {
            royaltyJsonFormat = df.parse(df.format(studio.getPayment().doubleValue())).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONPayment payment = new JSONPayment();
        payment.setRightsowner(studio.getName());
        payment.setRightsownerId(studio.getId());
        payment.setRoyalty(royaltyJsonFormat);
        payment.setViewings(studio.getViewings().longValue());
        return payment;
    }

}
