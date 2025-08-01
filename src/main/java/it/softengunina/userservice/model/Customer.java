package it.softengunina.userservice.model;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends User {
    private static final Role ROLE = Role.CUSTOMER;

    public Customer(LoginCredentials credentials, PersonInfo info) {
        super(credentials, info);
    }

    protected Customer() {
        super();
    }

    @Override
    public Role getPermissions() {
        return ROLE;
    }
}
