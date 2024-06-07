package it.epicode.capstone_project_alicelazzeri.payloads;

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
