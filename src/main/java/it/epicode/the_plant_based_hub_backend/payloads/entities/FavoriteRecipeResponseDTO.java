package it.epicode.the_plant_based_hub_backend.payloads.entities;

public record FavoriteRecipeResponseDTO(
        long id,
        long userId,
        long recipeId
) {
}
