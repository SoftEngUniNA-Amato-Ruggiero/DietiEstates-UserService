package it.softengunina.userservice.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserTest {
    User user;

    @BeforeEach
    void createCustomer() {
        LoginCredentials loginCredentials = new LoginCredentials("johndoe@example.com", "password123");
        PersonInfo personInfo = new PersonInfo("John", "Doe");
        user = new User(loginCredentials, personInfo);
    }

    @Test
    void verifyPermissions() {
//        assertEquals(Role.CUSTOMER, user.getPermissions());
    }
}
