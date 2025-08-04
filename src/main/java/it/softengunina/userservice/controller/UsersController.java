package it.softengunina.userservice.controller;

import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
public class UsersController {
    private static final String NOT_FOUND_MESSAGE = "User not found with id ";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "User not found with email ";
    private final UserRepository<User> repository;

    public UsersController(UserRepository<User> repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getUsers(@Email @RequestParam(value = "email", required = false) String email) {
        if (email != null) {
            Optional<User> optionalUser = repository.findByEmail(email);
            User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND_MESSAGE + email));
            return List.of(user);
        } else {
            return repository.findAll();
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }

    @PostMapping
    //TODO: Only the manager should be able to create a user for their agency
    // And the users that can be created should be limited to agents and managers
    public User createUser(@Valid @RequestBody User user) {
        return repository.save(user);
    }

    @PostMapping("/self")
    public User createSelf() {
        //TODO: Get User from token and save it
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Self-creation of user is not implemented yet.");
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return repository.findById(id)
                .map(existingUser -> {
                    existingUser.setInfo(user.getInfo());
                    existingUser.setCredentials(user.getCredentials());
                    return repository.save(existingUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }
        repository.deleteById(id);
    }
}
