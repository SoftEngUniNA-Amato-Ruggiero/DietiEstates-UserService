package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.RealEstateAgency;
import it.softengunina.userservice.model.RealEstateManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class RealEstateAgencyResponse {
    @NotNull
    @Valid
    private RealEstateManager manager;

    @NotNull
    @Valid
    private RealEstateAgency agency;
}
