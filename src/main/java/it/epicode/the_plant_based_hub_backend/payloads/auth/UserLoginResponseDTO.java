package it.epicode.the_plant_based_hub_backend.payloads.auth;

public record UserLoginResponseDTO(
        long id,
        String accessToken,
        String firstName,
        String lastName,
        String email
) {
}
