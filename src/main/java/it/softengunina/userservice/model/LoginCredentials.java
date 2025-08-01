package it.softengunina.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Embeddable
public class LoginCredentials{
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String cognitoSub;

    public LoginCredentials(String email, String cognitoSub) {
        this.email = email;
        this.cognitoSub = cognitoSub;
    }

    protected LoginCredentials(){}

    public String getEmail() {
        return email;
    }

    public String getCognitoSub() {
        return cognitoSub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginCredentials that)) return false;
        return Objects.equals(email, that.email) &&
                Objects.equals(cognitoSub, that.cognitoSub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, cognitoSub);
    }

    @Override
    public String toString() {
        return "LoginCredentials[email=" + email + ", password=" + cognitoSub + "]";
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setCognitoSub(String password) {
        this.cognitoSub = password;
    }
}

