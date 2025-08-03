package it.softengunina.userservice.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RealEstateAgentTest {
    RealEstateAgent realEstateAgent;
    LoginCredentials loginCredentials;
    PersonInfo personInfo;
    RealEstateAgency realEstateAgency;

    @BeforeEach
    void createRealEstateAgent() {
        loginCredentials = new LoginCredentials("johndoe@example.com", "password123");
        personInfo = new PersonInfo("John", "Doe");
        realEstateAgency = new RealEstateAgency("3331234567890", "Test Agency");
        realEstateAgent = new RealEstateAgent(loginCredentials, personInfo, realEstateAgency);
    }

    @Test
    void verifyPermissions() {
//        assertEquals(Role.REAL_ESTATE_AGENT, realEstateAgent.getPermissions());
    }

}
