package es.jrq.pcc.core.dao.map;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import es.jrq.pcc.core.dao.ICustomerDAO;
import es.jrq.pcc.core.model.Customer;

/**
 * Implementation of Customer DAO based in a memory MAP, providing concurrent
 * access to Map elements.
 *
 * @author JRQ
 *
 */
public class MapCustomerDAO implements ICustomerDAO {

    /** Map of customers. */
    private Map<String, Customer> customers;

    /**
     * Initialization of maps. Map is Concurrent to make sure different threads
     * can interact with the DAO service without having concurrent modification
     * exceptions.
     */
    public MapCustomerDAO() {
        this.customers = new ConcurrentHashMap<String, Customer>();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers.values().stream().collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerById(String customerId) {
        return customers.get(customerId);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    @Override
    public void removeCustomerById(String customerId) {
        customers.remove(customerId);
    }
}
