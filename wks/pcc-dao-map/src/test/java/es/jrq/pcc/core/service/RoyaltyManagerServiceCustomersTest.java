package es.jrq.pcc.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import es.jrq.pcc.core.model.Customer;

/**
 * Royalty Manager Service Customers Test class.
 * All test methods focused on testing Customers service in Royalty Manager Service
 * @author JRQ
 *
 */
public class RoyaltyManagerServiceCustomersTest {

    /**
     * Instance of the Royalty Manager Service
     */
    private static RoyaltyManagerService royaltyManagerService;

    /**
     * Setting up the royalty manager service
     * @throws Exception if any error occurs during service instantiation.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        royaltyManagerService = RoyaltyManagerService.getInstance();
    }

    /**
     * Save and retrieve several customers test.
     */
    @Test
    public void saveAndRetreiveCustomersTest() {
        String customerId1 = UUID.randomUUID().toString();
        Customer customer1 = new Customer(customerId1);
        royaltyManagerService.saveCustomer(customer1);

        Customer storedCustomer1 = royaltyManagerService.getCustomerById(customerId1);
        assertNotNull(storedCustomer1);
        assertEquals(storedCustomer1.getCustomerId(), customerId1);

        // Check list of elements has just one element
        assertEquals(royaltyManagerService.getCustomers().size(), 1);

        String customerId2 = UUID.randomUUID().toString();
        Customer customer2 = new Customer(customerId2);
        royaltyManagerService.saveCustomer(customer2);

        Customer storedCustomer2 = royaltyManagerService.getCustomerById(customerId2);
        assertNotNull(storedCustomer2);
        assertEquals(storedCustomer2.getCustomerId(), customerId2);

        // Check list of elements has two element
        assertEquals(royaltyManagerService.getCustomers().size(), 2);

        // Leave Data Store in the initial state
        royaltyManagerService.removeCustomerById(customerId1);
        storedCustomer1 = royaltyManagerService.getCustomerById(customerId1);
        assertNull(storedCustomer1);
        royaltyManagerService.removeCustomerById(customerId2);
        storedCustomer2 = royaltyManagerService.getCustomerById(customerId2);
        assertNull(storedCustomer2);

        // Check list of elements is empty
        assertEquals(royaltyManagerService.getCustomers().size(), 0);
    }

    /**
     * Save a new customer test.
     */
    @Test
    public void saveAndRetreiveCustomerTest() {
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId);
        royaltyManagerService.saveCustomer(customer);
        Customer storedCustomer = royaltyManagerService.getCustomerById(customerId);
        assertNotNull(storedCustomer);
        assertEquals(storedCustomer.getCustomerId(), customerId);

        // Leave Data Store in the initial state
        royaltyManagerService.removeCustomerById(customerId);
        storedCustomer = royaltyManagerService.getCustomerById(customerId);
        assertNull(storedCustomer);
    }

    /**
     * Retrieve non existing customer test.
     */
    @Test
    public void retrieveNonExistingCustomerTest() {
        String customerId = UUID.randomUUID().toString();
        Customer nonExistingCustomer = royaltyManagerService.getCustomerById(customerId);
        assertNull(nonExistingCustomer);
    }

}
