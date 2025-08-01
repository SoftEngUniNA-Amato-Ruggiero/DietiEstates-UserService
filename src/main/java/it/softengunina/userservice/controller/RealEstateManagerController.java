package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.RealEstateAgentDTO;
import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.model.RealEstateAgent;
import it.softengunina.userservice.model.RealEstateManager;
import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class RealEstateManagerController {
    private static final String NOT_FOUND_MESSAGE = "Manager not found with id ";
    private final RealEstateManagerRepository repository;
    private final RealEstateAgencyRepository agencyRepository;

    public RealEstateManagerController(RealEstateManagerRepository repository, RealEstateAgencyRepository agencyRepository) {
        this.repository = repository;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping
    public List<RealEstateManager> getAllManagers() {
        return repository.findAll();
    }

    @PostMapping
    public RealEstateAgent createManager(@RequestBody RealEstateAgentDTO agent) {
        RealEstateAgency agency = agencyRepository.findById(agent.getAgencyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, RealEstateAgencyController.NOT_FOUND_MESSAGE + agent.getAgencyId()));
        RealEstateManager newManager = new RealEstateManager(agent.getCredentials(), agent.getInfo(), agency);
        return repository.save(newManager);
    }

    @PutMapping("/{id}")
    public User updateManager(@PathVariable Long id, @RequestBody RealEstateAgentDTO agent) {
        if (agent.getAgencyId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agency ID must be provided for agent update.");
        }
        return repository.findById(id)
                .map(existingManager -> {
                    existingManager.setInfo(agent.getInfo());
                    existingManager.setCredentials(agent.getCredentials());
                    return repository.save(existingManager);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }
}
