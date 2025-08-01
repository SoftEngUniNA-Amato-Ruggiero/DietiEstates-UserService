package it.softengunina.userservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Embedded
    LoginCredentials credentials;

    @Embedded
    PersonInfo info;

    protected User(LoginCredentials credentials, PersonInfo info) {
        this.credentials = credentials;
        this.info = info;
    }

    protected User(){}

    public LoginCredentials getCredentials(){
        return credentials;
    }

    public PersonInfo getInfo() {
        return info;
    }

    public abstract Role getPermissions();

    @Override
    public String toString() {
        return info.toString() + " " + credentials.toString();
    }

    public void setCredentials(LoginCredentials credentials) {
        this.credentials = credentials;
    }

    public void setInfo(PersonInfo info) {
        this.info = info;
    }
}
