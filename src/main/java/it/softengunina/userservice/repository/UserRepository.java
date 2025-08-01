package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository<T extends User> extends JpaRepository<T,Long> {
    T findByCredentials(LoginCredentials credentials);

    @Query("SELECT u FROM #{#entityName} u WHERE u.credentials.email = :email")
    T findByEmail(String email);
}
