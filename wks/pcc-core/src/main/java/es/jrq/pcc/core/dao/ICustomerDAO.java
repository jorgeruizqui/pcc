package es.jrq.pcc.core.dao;

import java.util.List;

import es.jrq.pcc.core.model.Customer;

/**
 * Customer Data Access Object Interface definition
 *
 * @author JRQ
 *
 */
public interface ICustomerDAO {

    /**
     *
     * @return All {@link Customer} entities registered in the system.
     */
    List<Customer> getAllCustomers();

    /**
     *
     * @param customerId Customer Identifier
     * @return {@link Customer} if found by identifier. <code>null</code> if no customer
     *         has been found for the specified identifier.
     */
    Customer getCustomerById(String customerId);

    /**
     * Persists a {@link Customer} entity in the system.
     * @param customer Entity to be persisted
     */
    void saveCustomer(Customer customer);

    /**
     * Removes the customer with the given identifier.
     * Do nothing if the customer does not exists in the system.
     * @param customerId Customer Identifier
     */
    void removeCustomerById(String customerId);

}
