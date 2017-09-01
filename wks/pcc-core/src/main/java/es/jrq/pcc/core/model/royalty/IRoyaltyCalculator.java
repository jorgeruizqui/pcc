package es.jrq.pcc.core.model.royalty;

import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;

/**
 * Interface defining methods for Royalty calculations, applied to a concrete {@link Studio}
 * @author JRQ
 *
 */
public interface IRoyaltyCalculator {

    /**
     * Calculations of royalties for an studio.
     * May be a simple implementation or may depend on the Episode, so this is
     * passed as parameter as well.
     * @param episode Episode instance
     * @param studio Studio instance
     */
    void calculateRoyalty(Episode episode, Studio studio);
}
