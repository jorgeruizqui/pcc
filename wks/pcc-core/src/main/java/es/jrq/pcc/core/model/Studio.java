package es.jrq.pcc.core.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import es.jrq.pcc.core.model.royalty.DefaultRoyaltyCalculator;
import es.jrq.pcc.core.model.royalty.IRoyaltyCalculator;

/**
 * Bean class modeling Studio entity.
 * It will be referenced by its <code>id</code> field as 'Rights Owner'.
 *
 * @author JRQ
 *
 */
public class Studio {

    /** Studio (Rights Owner) Global Unique Identifier. */
    private String id;

    /** Studio Name. */
    private String name;

    /**
     * Current Studio Payment.
     * As the range is not define, use BigDecimal to make sure the range
     * is wide enough to support all payments.
     */
    private BigDecimal payment;

    /**
     * Number of viewings corresponding to this studio.
     * Requirements do not specifiy that viewing must be associated to a
     * concrete episode, but the studio instead.
     */
    private BigInteger viewings;

    /**
     * Instance of the royalty calculator.
     */
    private IRoyaltyCalculator royaltyCalculator;

    /**
     * Default Constructor.
     */
    public Studio() {
        this.id = "";
        this.name = "";
        this.payment = new BigDecimal(0.0);
        this.viewings = BigInteger.ZERO;
        this.royaltyCalculator = new DefaultRoyaltyCalculator();
    }

    /**
     * @param id Studio Identifier
     * @param name Studio Name
     * @param payment Studio current Payment
     * @param viewings Studio current viewings
     */
    public Studio(String id, String name, BigDecimal payment, BigInteger viewings) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.viewings = viewings;
        this.royaltyCalculator = new DefaultRoyaltyCalculator();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the payment
     */
    public BigDecimal getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    /**
     * @return the viewings
     */
    public BigInteger getViewings() {
        return viewings;
    }

    /**
     * @param viewings the viewings to set
     */
    public void setViewings(BigInteger viewings) {
        this.viewings = viewings;
    }

    /**
     * @return the royaltyCalculator
     */
    public IRoyaltyCalculator getRoyaltyCalculator() {
        return royaltyCalculator;
    }

    /**
     * @param royaltyCalculator the royaltyCalculator to set
     */
    public void setRoyaltyCalculator(IRoyaltyCalculator royaltyCalculator) {
        this.royaltyCalculator = royaltyCalculator;
    }

    /**
     * Registers a new viewing of an episode of this studio
     * @param episode Episode viewed
     */
    public void registerViewing(Episode episode) {
        this.viewings = this.viewings.add(BigInteger.ONE);
        this.royaltyCalculator.calculateRoyalty(episode, this);
    }

    /**
     * Increments the payment for this studio.
     *
     * @param amount Amount to be added
     */
    public void incrementPayment(Double amount) {
        this.payment = new BigDecimal(amount).add(getPayment());
    }

    @Override
    public String toString() {
        return "Studio [id=" + id + ", name=" + name + ", payment=" + payment + ", viewings=" + viewings + "]";
    }
}
