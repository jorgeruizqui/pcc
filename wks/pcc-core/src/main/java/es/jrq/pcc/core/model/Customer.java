package es.jrq.pcc.core.model;

/**
 *
 * Bean class modeling Customer Entity
 *
 * @author JRQ
 */
public class Customer {

    /** Customer Global Unique Identifier. */
    private String customerId;

    /**
     * Default Constructor.
     */
    public Customer() {
        this.customerId = "";
    }

    /**
     * @param customerId Customer Identifier
     */
    public Customer(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + "]";
    }
}
