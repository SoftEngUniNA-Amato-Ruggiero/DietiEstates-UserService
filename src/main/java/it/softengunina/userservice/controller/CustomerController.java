package it.softengunina.userservice.controller;

import it.softengunina.userservice.model.Customer;
import it.softengunina.userservice.model.User;
import it.softengunina.userservice.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final String NOT_FOUND_MESSAGE = "Customer not found with id ";
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return repository.save(customer);
    }

    @PutMapping("/{id}")
    public User updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        return repository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setInfo(customer.getInfo());
                    existingCustomer.setCredentials(customer.getCredentials());
                    return repository.save(existingCustomer);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE + id));
    }
}
