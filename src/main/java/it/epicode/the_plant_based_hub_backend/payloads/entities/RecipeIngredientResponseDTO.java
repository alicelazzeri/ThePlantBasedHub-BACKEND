package it.epicode.the_plant_based_hub_backend.payloads.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record RecipeIngredientResponseDTO(
        long id,
        int quantity,
        String measurementUnit,
        @JsonIgnore
        long recipeId,
        @JsonIgnore
        long ingredientId
) {
}
