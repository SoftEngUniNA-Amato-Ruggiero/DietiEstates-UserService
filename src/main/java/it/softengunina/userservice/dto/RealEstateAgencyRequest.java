package it.softengunina.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class RealEstateAgencyRequest {
    @NotBlank
    String iban;

    @NotBlank
    String name;
}
