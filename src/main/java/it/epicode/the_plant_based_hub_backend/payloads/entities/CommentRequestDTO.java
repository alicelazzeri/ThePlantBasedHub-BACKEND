package it.epicode.the_plant_based_hub_backend.payloads.entities;

import jakarta.validation.constraints.*;

public record CommentRequestDTO(
        @NotNull(message = "Rating is mandatory")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        int recipeRating,

        @NotNull(message = "Text is mandatory")
        @NotEmpty(message = "Text cannot be empty")
        String commentText,

        @NotNull(message = "User ID is mandatory")
        long userId,

        @NotNull(message = "Recipe ID is mandatory")
        long recipeId
) {

}
