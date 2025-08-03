package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.RealEstateAgencyRequest;
import it.softengunina.userservice.dto.RealEstateAgencyResponse;
import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.model.RealEstateAgent;
import it.softengunina.userservice.model.RealEstateManager;
import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateAgentRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import it.softengunina.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static it.softengunina.userservice.utils.TokenUtils.*;

@RestController
@RequestMapping("/agencies")
public class RealEstateAgencyController {
    static final String NOT_FOUND_MESSAGE = "Agency not found with id ";
    private final UserRepository userRepository;
    private final RealEstateAgentRepository agentRepository;
    private final RealEstateManagerRepository managerRepository;
    private final RealEstateAgencyRepository repository;

    public RealEstateAgencyController(UserRepository userRepository, RealEstateManagerRepository managerRepository, RealEstateAgencyRepository repository, RealEstateAgentRepository agentRepository) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.managerRepository = managerRepository;
        this.repository = repository;
    }

    @GetMapping
    public List<RealEstateAgency> getAllAgencies() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    public RealEstateAgency createAgency(@Valid @RequestBody RealEstateAgencyRequest agencyRequest) {
        Jwt jwt = getJwt();
        String cognitoSub = getCognitoSubFromToken(jwt);
        User user = userRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with cognito sub: " + cognitoSub));

        RealEstateAgency agency = new RealEstateAgency(agencyRequest.getIban(), agencyRequest.getName());
        RealEstateAgency savedAgency = repository.save(agency);

        agentRepository.promoteUser(user.getId(), savedAgency.getId());
        managerRepository.promoteAgent(user.getId());

        return savedAgency;
    }

    @GetMapping("/{id}")
    public RealEstateAgency getAgencyById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }

    @PutMapping("/{id}")
    public RealEstateAgency updateAgency(@PathVariable Long id, @Valid @RequestBody RealEstateAgency agency) {
        return repository.findById(id)
                .map(existingAgency -> {
                    existingAgency.setName(agency.getName());
                    return repository.save(existingAgency);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }

    @DeleteMapping("/{id}")
    public void deleteAgency(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id);
        }
        repository.deleteById(id);
    }
}
