package es.jrq.pcc.core.dao;

import java.util.Properties;

/**
 * Royalty Manager DAO Factory.
 *
 * It is in charge of create DAOs according to the current application configuration.
 *
 * @author JRQ
 *
 */
public final class RoyaltyManagerDAOFactory {

    /** DAO configuration resource. */
    public static final String DAO_CFG_RESOURCE = "/es/jrq/pcc/core/dao/dao-configuration.properties";

    /** Customer DAO configuration key. */
    public static final String CUSTOMER_DAO_KEY = "customerDao";

    /** Episode DAO configuration key. */
    public static final String EPISODE_DAO_KEY = "episodeDao";

    /** Studio DAO configuration key. */
    public static final String STUDIO_DAO_KEY = "studioDao";

    /** Dao configuration properties. */
    private static Properties properties = new Properties();

    static {
        try {
          properties.load(RoyaltyManagerDAOFactory.class.getResourceAsStream(DAO_CFG_RESOURCE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Service utility class do not have public constructors.
     */
    private RoyaltyManagerDAOFactory() {
    }

    /**
     * @return the configured Customer DAO instance
     * <code>null</code> if any error during instantiation DAO class.
     */
    public static ICustomerDAO getCustomerDAO() {
        String customerDaoClassName = properties.getProperty(CUSTOMER_DAO_KEY);
        ICustomerDAO customerDao = null;
        try {
            customerDao = (ICustomerDAO) Class.forName(customerDaoClassName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return customerDao;
    }

    /**
     * @return the configured Episode DAO instance
     * <code>null</code> if any error during instantiation DAO class.
     */
    public static IEpisodeDAO getEpisodeDAO() {
        String episodeDaoClassName = properties.getProperty(EPISODE_DAO_KEY);
        IEpisodeDAO episodeDao = null;
        try {
            episodeDao = (IEpisodeDAO) Class.forName(episodeDaoClassName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return episodeDao;
    }

    /**
     * @return the configured Studio DAO instance
     * <code>null</code> if any error during instantiation DAO class.
     */
    public static IStudioDAO getStudioDAO() {
        String studioDaoClassName = properties.getProperty(STUDIO_DAO_KEY);
        IStudioDAO studioDao = null;
        try {
            studioDao = (IStudioDAO) Class.forName(studioDaoClassName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return studioDao;
    }

}
