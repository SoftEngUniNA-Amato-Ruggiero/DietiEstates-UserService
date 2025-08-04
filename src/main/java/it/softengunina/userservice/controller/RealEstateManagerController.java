package it.softengunina.userservice.controller;

import it.softengunina.userservice.model.RealEstateManager;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import it.softengunina.userservice.repository.RealEstateManagerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
