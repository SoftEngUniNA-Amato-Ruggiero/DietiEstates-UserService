package it.softengunina.userservice.controller;

import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.repository.RealEstateAgencyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agencies")
public class RealEstateAgencyController {
    static final String NOT_FOUND_MESSAGE = "Agency not found with id ";
    private final RealEstateAgencyRepository repository;

    public RealEstateAgencyController(RealEstateAgencyRepository repository) { this.repository = repository; }

    @GetMapping
    public List<RealEstateAgency> getAllAgencies() {
        return repository.findAll();
    }

    @PostMapping
    public RealEstateAgency createAgency(@Valid @RequestBody RealEstateAgency agency) {
        return repository.save(agency);
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
