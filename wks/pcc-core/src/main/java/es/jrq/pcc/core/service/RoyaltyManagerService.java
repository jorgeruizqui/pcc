package es.jrq.pcc.core.service;

import java.util.List;
import java.util.function.Consumer;

import es.jrq.pcc.core.dao.RoyaltyManagerDAOFacade;
import es.jrq.pcc.core.model.Customer;
import es.jrq.pcc.core.model.Episode;
import es.jrq.pcc.core.model.Studio;
import es.jrq.pcc.core.model.Viewing;

/**
 * Royalty Manager Service.
 * Main Back-end implementation of Royalty Manager Service providing:
 * - Singleton instance of the class.
 * - Service layer Interface methods to interact with configured Data Source
 *
 * DAO access is encapsulated in this class and is not accessible outside this framework
 *
 * @author JRQ
 *
 */
public final class RoyaltyManagerService {

    /** Singleton instance of the class. */
    private static RoyaltyManagerService instance;

    /** Royalty Manager DAO Façade instance. */
    private RoyaltyManagerDAOFacade royaltyManagerDAO;

    /**
     * Private constructor for instantiating the instance.
     */
    private RoyaltyManagerService() {
        this.royaltyManagerDAO = new RoyaltyManagerDAOFacade();
    }

    /**
     * Singleton access to the Royalty Manager Instance.
     * Make sure just one instance is created, so method is typed as
     * synchronized
     *
     * @return the Royalty Manager singleton instance
     */
    public static synchronized RoyaltyManagerService getInstance() {
        if (instance == null) {
            instance = new RoyaltyManagerService();
        }
        return instance;
    }

    /**
     *
     * @return Retrieves all the registered {@link Customer}.
     *         Empty <code>List</code> if no customers are registered.
     */
    public List<Customer> getCustomers() {
        return this.royaltyManagerDAO.getCustomerDAO().getAllCustomers();
    }

    /**
     *
     * @param customerId Customer identifier
     * @return Retrieves the {@link Customer} for the specified Identifier.
     *         <code>null</code> if no customer found for the given identifier.
     */
    public Customer getCustomerById(String customerId) {
        return this.royaltyManagerDAO.getCustomerDAO().getCustomerById(customerId);
    }

    /**
     * Persists the {@link Customer} in data store
     *
     * @param customer Customer entity to be saved
     */
    public void saveCustomer(Customer customer) {
        this.royaltyManagerDAO.getCustomerDAO().saveCustomer(customer);
    }

    /**
     * Removes the {@link Customer} for the specified Identifier.
     * @param customerId Customer Identifiers
     *
     */
    public void removeCustomerById(String customerId) {
        this.royaltyManagerDAO.getCustomerDAO().removeCustomerById(customerId);
    }

    /**
     *
     * @return Retrieves all the registered {@link Episode}.
     *         Empty <code>List</code> if no episodes are registered.
     */
    public List<Episode> getEpisodes() {
        return this.royaltyManagerDAO.getEpisodeDAO().getAllEpisodes();
    }

    /**
     *
     * @param episodeId Episode Identifier
     * @return Retrieves the {@link Episode} for the specified Identifier.
     *         <code>null</code> if no episode found for the given identifier.
     */
    public Episode getEpisodeById(String episodeId) {
        return this.royaltyManagerDAO.getEpisodeDAO().getEpisodeById(episodeId);
    }

    /**
     * Persists the {@link Episode} in data store
     *
     * @param episode Episode entity to be saved
     */
    public void saveEpisode(Episode episode) {
        this.royaltyManagerDAO.getEpisodeDAO().saveEpisode(episode);
    }

    /**
     *
     * Removes the {@link Episode} for the specified Identifier.
     * @param episodeId Episode identifier
     */
    public void removeEpisodeById(String episodeId) {
        this.royaltyManagerDAO.getEpisodeDAO().removeEpisodeById(episodeId);
    }

    /**
     *
     * @return Retrieves all the registered {@link Studio}.
     *         Empty <code>List</code> if no studios are registered.
     */
    public List<Studio> getStudios() {
        return this.royaltyManagerDAO.getStudioDAO().getAllStudios();
    }

    /**
     *
     * @param studioId Studio identifier
     * @return Retrieves the {@link Studio} for the specified Identifier.
     *         <code>null</code> if no studio found for the given identifier.
     */
    public Studio getStudioById(String studioId) {
        return this.royaltyManagerDAO.getStudioDAO().getStudioById(studioId);
    }

    /**
     * Persists the studio in data store
     *
     * @param studio Studio entity to be saved
     */
    public void saveStudio(Studio studio) {
        this.royaltyManagerDAO.getStudioDAO().saveStudio(studio);
    }

    /**
     * Removes the {@link Studio} for the specified Identifier.
     *
     * @param studioId Studio identifier
     */
    public void removeStudioById(String studioId) {
        this.royaltyManagerDAO.getStudioDAO().removeStudioById(studioId);
    }

    /**
     * Resets all studio viewings to zero.
     */
    public void reset() {
        this.royaltyManagerDAO.getStudioDAO().resetCounters();
    }

    /**
     * Register a new viewing on the system.
     * @param viewing Viewing data containing episode and customer references.
     */
    public void viewing(Viewing viewing) {
        Episode episode = this.royaltyManagerDAO.getEpisodeDAO().getEpisodeById(
                viewing.getEpisode());

        if (episode != null) {
            this.royaltyManagerDAO.getStudioDAO().registerViewing(
                    episode.getStudioId());
        }
    }

    /**
     * Loads Episodes in the system
     * @param episodes List of {@link Episode} to be persisted.
     */
    public void loadEpisodes(List<Episode> episodes) {
        episodes.forEach(new Consumer<Episode>() {
            @Override
            public void accept(Episode episode) {
                saveEpisode(episode);
            }
        });
    }

    /**
     * Loads Studios in the system
     * @param studios List of {@link Studio} to be persisted.
     */
    public void loadStudios(List<Studio> studios) {
        studios.forEach(new Consumer<Studio>() {
            @Override
            public void accept(Studio studio) {
                saveStudio(studio);
            }
        });
    }
}
