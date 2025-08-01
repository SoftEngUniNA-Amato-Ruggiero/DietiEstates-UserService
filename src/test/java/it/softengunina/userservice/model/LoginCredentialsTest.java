package it.softengunina.userservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LoginCredentialsTest {
    LoginCredentials credentials;
    String email;
    String cognitoSub;

    @BeforeEach
    void setUp() {
        email = "testemail@example.com";
        cognitoSub = "testCognitoSub";
        credentials = new LoginCredentials(email, cognitoSub);
    }

    @Test
    void testSetEmail() {
        String newEmail = "newemail@test.com";
        credentials.setEmail(newEmail);
        assertEquals(newEmail, credentials.getEmail());
    }

    @Test
    void testSetCognitoSub() {
        String newCognitoSub = "newCognitoSub";
        credentials.setCognitoSub(newCognitoSub);
        assertEquals(newCognitoSub, credentials.getCognitoSub());
    }


    @Test
    void testEqualsWhenTrue() {
        LoginCredentials sameCredentials = new LoginCredentials(email, cognitoSub);
        assertEquals(credentials, sameCredentials);
    }

    @Test
    void testEqualsWhenFalse() {
        LoginCredentials otherCredentials = new LoginCredentials("wrongmail@test.com", "wrong cognitosub");
        assertNotEquals(credentials, otherCredentials);
    }

    @Test
    void testEqualsWhenNull() {
        assertNotEquals(null, credentials);
    }

    @Test
    void testEqualsWhenDifferentClass() {
        Object notCredentials = new Object();
        assertNotEquals(notCredentials, credentials);
    }

    @Test
    void testHashCodeWhenEquals() {
        LoginCredentials sameCredentials = new LoginCredentials(email, cognitoSub);
        assertEquals(credentials.hashCode(), sameCredentials.hashCode());
    }

    @Test
    void testHashCodeWhenDifferent() {
        LoginCredentials otherCredentials = new LoginCredentials("wrongmail@test.com", "wrong cognitosub");
        assertNotEquals(credentials.hashCode(), otherCredentials.hashCode());
    }
}
