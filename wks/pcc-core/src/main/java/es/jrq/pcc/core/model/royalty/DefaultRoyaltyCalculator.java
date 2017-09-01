package es.jrq.pcc.core.model.royalty;

import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;

/**
 * Default implementation for Royalties calculator.
 * It will search from configuration in the application context to check
 * the amount to be added. If not present, will use the DEFAULT_AMOUNT defined
 * as a constant.
 * @author JRQ
 *
 */
public class DefaultRoyaltyCalculator implements IRoyaltyCalculator {

    /** Default royalty amount. */
    public static final double DEFAULT_AMOUNT = 1.0d;

    /** Default Royalty amount parameter key. */
    private static final String AMOUNT_KEY = "defaultRoyaltyAmount";

    /** Default amount. */
    private static Double calculatedAmount = DEFAULT_AMOUNT;

    static {
        String configuredAmount = System.getProperty(AMOUNT_KEY);
        if (configuredAmount != null && !configuredAmount.isEmpty()) {
            try {
                calculatedAmount = Double.parseDouble(configuredAmount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Make sure, calculated amount is set
                calculatedAmount = DEFAULT_AMOUNT;
            }
        }
    }

    @Override
    public void calculateRoyalty(Episode episode, Studio studio) {
        studio.incrementPayment(calculatedAmount);
    }

}
