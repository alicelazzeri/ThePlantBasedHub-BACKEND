package it.epicode.the_plant_based_hub_backend.payloads.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record FavoriteRecipeResponseDTO(
        long id,
        @JsonIgnore
        long userId,
        @JsonIgnore
        long recipeId
) {
}
