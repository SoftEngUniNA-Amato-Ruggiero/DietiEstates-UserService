package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.RealEstateAgencyRequest;
import it.softengunina.userservice.dto.RealEstateAgencyPostResponse;
import it.softengunina.userservice.dto.RealEstateAgentDTO;
import it.softengunina.userservice.model.*;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateAgentRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import it.softengunina.userservice.repository.UserRepository;
import it.softengunina.userservice.services.PromotionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static it.softengunina.userservice.utils.TokenUtils.*;

@Slf4j
@RestController
@RequestMapping("/agencies")
public class RealEstateAgencyController {
    static final String NOT_FOUND_MESSAGE = "Agency not found with id ";

    private final RealEstateAgencyRepository agencyRepository;
    private final UserRepository<User> userRepository;
    private final RealEstateAgentRepository<RealEstateAgent> agentRepository;
    private final RealEstateManagerRepository managerRepository;
    private final PromotionService promotionService;

    public RealEstateAgencyController(RealEstateAgencyRepository agencyRepository,
                                      UserRepository<User> userRepository,
                                      RealEstateAgentRepository<RealEstateAgent> agentRepository,
                                      RealEstateManagerRepository managerRepository, PromotionService promotionService) {
        this.agencyRepository = agencyRepository;
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.managerRepository = managerRepository;
        this.promotionService = promotionService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<RealEstateAgency> getAllAgencies() {
        return agencyRepository.findAll();
    }

    @PostMapping
    @Transactional
    public RealEstateAgencyPostResponse createAgency(@Valid @RequestBody RealEstateAgencyRequest agencyRequest) {
        RealEstateAgency agency = new RealEstateAgency(agencyRequest.getIban(), agencyRequest.getName());
        RealEstateAgency savedAgency = agencyRepository.save(agency);
        User user = getUserFromJwt();
        RealEstateManager manager = promotionService.promoteToManager(user, savedAgency);
        return new RealEstateAgencyPostResponse(savedAgency, manager.getRole().name());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public RealEstateAgency getAgencyById(@PathVariable Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }

    @PutMapping("/{id}")
    @Transactional
    public RealEstateAgency updateAgency(@PathVariable Long id, @Valid @RequestBody RealEstateAgencyRequest agency) {
        RealEstateManager manager = getRealEstateManagerFromJwt();

        RealEstateAgency savedAgency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));

        if (!manager.getAgency().equals(savedAgency)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this agency");
        }

        savedAgency.setName(agency.getName());
        return agencyRepository.save(savedAgency);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteAgency(@PathVariable Long id) {
        RealEstateManager manager = getRealEstateManagerFromJwt();

        RealEstateAgency savedAgency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));

        if (!manager.getAgency().equals(savedAgency)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this agency");
        }

        agencyRepository.delete(savedAgency);
    }

    @GetMapping("/{id}/agents")
    public Set<RealEstateAgent> getAgents(@PathVariable Long id) {
        RealEstateAgency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
        return agentRepository.findByAgencyId(agency.getId());
    }

    @PostMapping("/{id}/agents")
    @Transactional
    public RealEstateAgent postAgent(@PathVariable Long id, @NotNull @Valid @RequestBody RealEstateAgentDTO agentDTO) {
        String email = agentDTO.getEmail();

        RealEstateAgency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));

        RealEstateManager manager = getRealEstateManagerFromJwt();
        if (!manager.getAgency().equals(agency)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to add agents to this agency");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email " + email));

        return promotionService.promoteToAgent(user, agency);
    }

    @GetMapping("/{id}/managers")
    public Set<RealEstateManager> getManagers(@PathVariable Long id) {
        RealEstateAgency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
        return managerRepository.findByAgencyId(agency.getId());
    }


    //TODO: Move somewhere that makes sense

    private User getUserFromJwt() {
        Jwt jwt = getJwt();
        String cognitoSub = getCognitoSubFromToken(jwt);
        return userRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with cognito sub: " + cognitoSub));
    }

    private RealEstateManager getRealEstateManagerFromJwt() {
        Jwt jwt = getJwt();
        String cognitoSub = getCognitoSubFromToken(jwt);
        return managerRepository.findByCognitoSub(cognitoSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found with cognito sub: " + cognitoSub));
    }
}
