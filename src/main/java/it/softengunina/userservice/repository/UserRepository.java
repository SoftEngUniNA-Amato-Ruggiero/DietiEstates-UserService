package it.softengunina.userservice.repository;

import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Primary
public interface UserRepository<T extends User> extends JpaRepository<T,Long> {
    Optional<T> findByCredentials(LoginCredentials credentials);

    @Query("SELECT u FROM #{#entityName} u WHERE u.credentials.email = :email")
    Optional<T> findByEmail(String email);

    @Query("SELECT u FROM #{#entityName} u WHERE u.credentials.cognitoSub = :cognitoSub")
    Optional<T> findByCognitoSub(String cognitoSub);

    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.credentials.cognitoSub = :cognitoSub")
    boolean existsByCognitoSub(String cognitoSub);
}
