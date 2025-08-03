package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByCredentials(LoginCredentials credentials);

    @Query("SELECT u FROM #{#entityName} u WHERE u.credentials.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM #{#entityName} u WHERE u.credentials.cognitoSub = :cognitoSub")
    Optional<User> findByCognitoSub(String cognitoSub);
}
