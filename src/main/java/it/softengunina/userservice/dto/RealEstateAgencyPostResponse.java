package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.RealEstateAgency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RealEstateAgencyPostResponse {
    @NotNull
    @Valid
    RealEstateAgency agency;

    @NotNull
    String role;
}