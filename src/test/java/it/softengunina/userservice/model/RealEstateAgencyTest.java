package it.softengunina.userservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RealEstateAgencyTest {
    RealEstateAgency realEstateAgency;
    String iban;
    String name;

    @BeforeEach
    void setUp() {
        iban = "IT60X0542811101000000123456";
        name = "Test Real Estate Agency";
        realEstateAgency = new RealEstateAgency(iban, name);
    }

    @Test
    void testGetIban() {
        assertEquals(iban, realEstateAgency.getIban());
    }

    @Test
    void testGetName() {
        assertEquals(name, realEstateAgency.getName());
    }

    @Test
    void testSetName() {
        String newName = "Updated Real Estate Agency";
        realEstateAgency.setName(newName);
        assertEquals(newName, realEstateAgency.getName());
    }

    @Test
    void testEqualsWhenTrue() {
        RealEstateAgency anotherAgency = new RealEstateAgency(iban, name);
        assertEquals(realEstateAgency, anotherAgency);
    }

    @Test
    void testEqualsWhenFalse() {
        RealEstateAgency anotherAgency = new RealEstateAgency("999999999999999", "wrong name");
        assertNotEquals(realEstateAgency, anotherAgency);
    }

    @Test
    void testEqualsWhenNull() {
        assertNotEquals(null, realEstateAgency);
    }

    @Test
    void testEqualsWhenDifferentClass() {
        Object notAnAgency = new Object();
        assertNotEquals(notAnAgency, realEstateAgency);
    }
}
