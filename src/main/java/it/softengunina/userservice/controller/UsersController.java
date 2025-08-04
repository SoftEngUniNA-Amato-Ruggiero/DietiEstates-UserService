package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.UserDTO;
import it.softengunina.userservice.dto.UserAndRoleDTO;
import it.softengunina.userservice.model.*;
import it.softengunina.userservice.repository.UserRepository;
import it.softengunina.userservice.utils.TokenUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {
    private static final String NOT_FOUND_MESSAGE = "User not found";
    private final UserRepository<User> repository;

    public UsersController(UserRepository<User> repository) {
        this.repository = repository;
    }

    @GetMapping
    public User getUserByEmail(@NotBlank @Email @RequestParam(value = "email", required = true) String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @PostMapping("/self")
    @Transactional
    public UserAndRoleDTO postSelf(@Valid @RequestBody UserDTO self) {
        Jwt jwt = TokenUtils.getJwt();
        String cognitoSub = TokenUtils.getCognitoSubFromToken(jwt);

        LoginCredentials credentials = new LoginCredentials(self.getEmail(), cognitoSub);
        PersonInfo info = self.getInfo();
        User user;
        try {
            user = repository.findByCognitoSub(cognitoSub)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        } catch (ResponseStatusException e) {
            user = repository.save(new User(credentials, info));
        }

        return new UserAndRoleDTO(new UserDTO(user.getCredentials().getEmail(), user.getInfo()), user.getRole().name());
    }

    @PutMapping("/self")
    @Transactional
    public User updateSelf(@Valid @RequestBody UserDTO user) {
        Jwt jwt = TokenUtils.getJwt();
        String cognitoSub = TokenUtils.getCognitoSubFromToken(jwt);

        return repository.findByCognitoSub(cognitoSub)
                .map(existingUser -> {
                    existingUser.setInfo(user.getInfo());
                    existingUser.getCredentials().setEmail(user.getEmail());
                    return repository.save(existingUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @DeleteMapping("/self")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSelf() {
        Jwt jwt = TokenUtils.getJwt();
        String cognitoSub = TokenUtils.getCognitoSubFromToken(jwt);

        User user = repository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));

        repository.delete(user);
    }
}
