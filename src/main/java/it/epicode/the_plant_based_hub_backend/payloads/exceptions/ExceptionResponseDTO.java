package it.epicode.the_plant_based_hub_backend.payloads.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDTO (
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt
) {
}
