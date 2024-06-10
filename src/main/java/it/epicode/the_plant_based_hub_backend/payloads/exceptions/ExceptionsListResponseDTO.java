package it.epicode.the_plant_based_hub_backend.payloads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionsListResponseDTO(
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt,
        List<ObjectError> exceptionsList
) {
}
