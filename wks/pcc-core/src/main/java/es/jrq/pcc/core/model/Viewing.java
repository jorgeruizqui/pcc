package es.jrq.pcc.core.model;

/**
 * Bean class modeling a viewing of an {@link Episode} from a {@link Customer}.
 *
 * @author JRQ
 *
 */
public class Viewing {

    /** Episode Identifier. */
    private String episode;

    /** Customer Identifier. */
    private String customer;

    /**
     * Default Constructor.
     */
    public Viewing() {
        this.episode = "";
        this.customer = "";
    }

    /**
     * @param episode Episode identifier
     * @param customer Customer identifier
     */
    public Viewing(String episode, String customer) {
        super();
        this.episode = episode;
        this.customer = customer;
    }

    /**
     * @return the episode
     */
    public String getEpisode() {
        return episode;
    }

    /**
     * @param episode the episode to set
     */
    public void setEpisode(String episode) {
        this.episode = episode;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Viewing [episode=" + episode + ", customer=" + customer + "]";
    }
}
