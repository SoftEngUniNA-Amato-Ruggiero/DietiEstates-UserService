package it.softengunina.userservice.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RealEstateManagerTest {
    RealEstateManager realEstateManager;

    @BeforeEach
    void createRealEstateAgent() {
        LoginCredentials loginCredentials = new LoginCredentials("johndoe@example.com", "password123");
        PersonInfo personInfo = new PersonInfo("John", "Doe");
        RealEstateAgency realEstateAgency = new RealEstateAgency("Test Agency", "3331234567890");
        realEstateManager = new RealEstateManager(loginCredentials, personInfo, realEstateAgency);
    }

    @Test
    void verifyPermissions() {
        assertEquals(Role.AGENCY_MANAGER, realEstateManager.getPermissions());
    }
}
