package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.Customer;
import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.PersonInfo;
import it.softengunina.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    private static final String TEST_EMAIL = "user_repository@test.com";
    private static final String TEST_COGNITO_SUB = "test_cognito_sub";
    private static final String TEST_FIRST_NAME = "TestFirstName";
    private static final String TEST_LAST_NAME = "TestLastName";

    private final PersonInfo info = new PersonInfo(TEST_FIRST_NAME, TEST_LAST_NAME);
    private final LoginCredentials credentials = new LoginCredentials(TEST_EMAIL, TEST_COGNITO_SUB);

    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.save(new Customer(credentials, info));
    }

    @Test
    void testFindByCredentialsWhenUserExists() {
        Optional<User> user = userRepository.findByCredentials(credentials);
        assertAll(
            () -> assertTrue(user.isPresent(), "User should not be null"),
            () -> assertEquals(TEST_EMAIL, user.get().getCredentials().getEmail(), "Email should match"),
            () -> assertEquals(TEST_COGNITO_SUB, user.get().getCredentials().getCognitoSub(), "Cognito sub should match"),
            () -> assertEquals(TEST_FIRST_NAME, user.get().getInfo().getFirstName(), "First name should match"),
            () -> assertEquals(TEST_LAST_NAME, user.get().getInfo().getLastName(), "Last name should match")
        );
    }

    @Test
    void testFindByCredentialsWithNull() {
        Optional<User> user = userRepository.findByCredentials(null);
        assertTrue(user.isEmpty(), "User should be null");
    }

    @Test
    void testFindByEmailWhenUserExists() {
        Optional<User> user = userRepository.findByEmail(TEST_EMAIL);
        assertAll(
            () -> assertTrue(user.isPresent(), "User should not be null"),
            () -> assertEquals(TEST_EMAIL, user.get().getCredentials().getEmail(), "Email should match"),
            () -> assertEquals(TEST_COGNITO_SUB, user.get().getCredentials().getCognitoSub(), "Cognito sub should match"),
            () -> assertEquals(TEST_FIRST_NAME, user.get().getInfo().getFirstName(), "First name should match"),
            () -> assertEquals(TEST_LAST_NAME, user.get().getInfo().getLastName(), "Last name should match")
        );
    }

    @Test
    void testFindByEmailWithEmptyString() {
        Optional<User> user = userRepository.findByEmail("");
        assertTrue(user.isEmpty(), "User should be null");
    }

    @Test
    void testFindByEmailWithNull() {
        Optional<User> user = userRepository.findByEmail(null);
        assertTrue(user.isEmpty(), "User should be null");
    }
}
