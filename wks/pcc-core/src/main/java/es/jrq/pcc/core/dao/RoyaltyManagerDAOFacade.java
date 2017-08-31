package es.jrq.pcc.core.dao;

/**
 * Royalty Manager Data Access Object.
 *
 * Is a Façade in charge of providing access to all registered DAOs in the system.
 *
 * -{@link ICustomerDAO}
 * -{@link IEpisodeDAO}
 * -{@link IStudioDAO}
 *
 * @author JRQ
 *
 */
public class RoyaltyManagerDAOFacade {

    /** Instance of the customer DAO. */
    private ICustomerDAO customerDAO;

    /** Instance of the episode DAO. */
    private IEpisodeDAO episodeDAO;

    /** Instance of the studio DAO. */
    private IStudioDAO studioDAO;

    /**
     * Constructs and initializes DAOs in the system.
     */
    public RoyaltyManagerDAOFacade() {
        this.customerDAO = RoyaltyManagerDAOFactory.getCustomerDAO();
        this.episodeDAO = RoyaltyManagerDAOFactory.getEpisodeDAO();
        this.studioDAO = RoyaltyManagerDAOFactory.getStudioDAO();
    }

    /**
     * Access to Customer DAO.
     * @return {@link ICustomerDAO}
     */
    public ICustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    /**
     * Access to Episode DAO.
     * @return {@link IEpisodeDAO}
     */
    public IEpisodeDAO getEpisodeDAO() {
        return episodeDAO;
    }

    /**
     * Access to Studio DAO.
     * @return {@link IStudioDAO}
     */
    public IStudioDAO getStudioDAO() {
        return studioDAO;
    }

}
