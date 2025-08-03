package it.softengunina.userservice.controller;

import it.softengunina.userservice.dto.RealEstateAgentDTO;
import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.model.RealEstateAgent;
import it.softengunina.userservice.model.RealEstateManager;
import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import jakarta.validation.Valid;
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
}
