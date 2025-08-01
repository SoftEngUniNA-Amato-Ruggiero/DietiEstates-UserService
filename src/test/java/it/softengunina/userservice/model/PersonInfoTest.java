package it.softengunina.userservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonInfoTest {
    PersonInfo info;
    String firstName;
    String lastName;

    @BeforeEach
    void setUp() {
        firstName = "testFirstName";
        lastName = "testLastName";
        info = new PersonInfo(firstName, lastName);
    }

    @Test
    void testSetFirstName() {
        String otherName = "otherName";
        info.setFirstName(otherName);
        assertEquals(otherName, info.getFirstName());
    }

    @Test
    void testSetLastName() {
        String otherLastName = "otherLastName";
        info.setLastName(otherLastName);
        assertEquals(otherLastName, info.getLastName());
    }


    @Test
    void testEqualsWhenTrue() {
        PersonInfo sameInfo = new PersonInfo(firstName, lastName);
        assertEquals(info, sameInfo);
    }

    @Test
    void testEqualsWhenFalse() {
        PersonInfo otherInfo = new PersonInfo("otherName", "otherLastName");
        assertNotEquals(info, otherInfo);
    }

    @Test
    void testEqualsWhenNull() {
        assertNotEquals(null, info);
    }

    @Test
    void testEqualsWhenDifferentClass() {
        Object notInfo = new Object();
        assertNotEquals(notInfo, info);
    }

    @Test
    void testHashCodeWhenEquals() {
        PersonInfo sameInfo = new PersonInfo(firstName, lastName);
        assertEquals(info.hashCode(), sameInfo.hashCode());
    }

    @Test
    void testHashCodeWhenDifferent() {
        PersonInfo otherInfo = new PersonInfo("wrongmail@test.com", "wrong cognitosub");
        assertNotEquals(info.hashCode(), otherInfo.hashCode());
    }
}
