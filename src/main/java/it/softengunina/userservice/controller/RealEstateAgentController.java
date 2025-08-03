package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.RealEstateAgentDTO;
import it.softengunina.userservice.model.*;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateAgentRepository;
import it.softengunina.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class RealEstateAgentController {

    private static final String NOT_FOUND_MESSAGE = "Agent not found with id ";
    private final UserRepository userRepository;
    private final RealEstateAgentRepository agentRepository;
    private final RealEstateAgencyRepository agencyRepository;

    public RealEstateAgentController(UserRepository userRepository, RealEstateAgentRepository agentRepository, RealEstateAgencyRepository agencyRepository) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping
    public List<RealEstateAgent> getAllAgents() {
        return agentRepository.findAll();
    }

    @PostMapping
    public RealEstateAgent createAgentFromExistingUser(@Valid  @RequestBody RealEstateAgentDTO agent) {
        RealEstateAgency agency = agencyRepository.findById(agent.getAgencyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, RealEstateAgencyController.NOT_FOUND_MESSAGE + agent.getAgencyId()));

        User user = userRepository.findByEmail(agent.getCredentials().getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + agent.getCredentials().getEmail()));

        //TODO: promote user to agent, then save it
        return agentRepository.save(new RealEstateAgent(user.getCredentials(), user.getInfo(), agency));
    }

    @PutMapping("/{id}")
    public User updateAgent(@PathVariable Long id, @Valid @RequestBody RealEstateAgentDTO agent) {
        if (agent.getAgencyId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agency ID must be provided for agent update.");
        }
        return agentRepository.findById(id)
                .map(existingAgent -> {
                    existingAgent.setInfo(agent.getInfo());
                    existingAgent.setCredentials(agent.getCredentials());
                    return agentRepository.save(existingAgent);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }
}
