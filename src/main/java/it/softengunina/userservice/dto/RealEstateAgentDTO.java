package it.softengunina.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class RealEstateAgentDTO {
    @NotBlank
    @Email
    private String email;
}
