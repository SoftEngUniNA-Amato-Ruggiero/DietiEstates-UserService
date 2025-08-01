package it.softengunina.userservice.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Embeddable
public class PersonInfo {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public PersonInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected PersonInfo(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonInfo that)) return false;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "PersonInfo[firstName=" + firstName + ", lastName=" + lastName + "]";
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

