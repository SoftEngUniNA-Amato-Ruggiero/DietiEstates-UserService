package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.PersonInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class RealEstateAgentDTO {
    @NotNull
    @Valid
    private String email;

    @NotNull
    @Valid
    private PersonInfo info;

    @NotNull
    private Long agencyId;
}
