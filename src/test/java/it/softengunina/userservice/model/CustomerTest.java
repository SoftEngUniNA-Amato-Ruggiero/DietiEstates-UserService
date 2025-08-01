package it.softengunina.userservice.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CustomerTest {
    Customer customer;

    @BeforeEach
    void createCustomer() {
        LoginCredentials loginCredentials = new LoginCredentials("johndoe@example.com", "password123");
        PersonInfo personInfo = new PersonInfo("John", "Doe");
        customer = new Customer(loginCredentials, personInfo);
    }

    @Test
    void verifyPermissions() {
        assertEquals(Role.CUSTOMER, customer.getPermissions());
    }

    @Test
    void testSetCredentials() {
        LoginCredentials newCredentials = new LoginCredentials("newemail@test.com", "newpassword");
        customer.setCredentials(newCredentials);
        assertEquals(newCredentials, customer.getCredentials());
    }

    @Test
    void testSetInfo() {
        PersonInfo newInfo = new PersonInfo("Jane", "Dane");
        customer.setInfo(newInfo);
        assertEquals(newInfo, customer.getInfo());
    }
}
