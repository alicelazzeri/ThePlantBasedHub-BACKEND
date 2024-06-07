package it.epicode.capstone_project_alicelazzeri.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @Email(message = "Email cannot be empty")
        @NotBlank(message = "Email cannot be blank or with blank spaces only")
        String email,
        @NotBlank(message = "Password cannot be blank or with blank spaces only")
        String password
) {
}
