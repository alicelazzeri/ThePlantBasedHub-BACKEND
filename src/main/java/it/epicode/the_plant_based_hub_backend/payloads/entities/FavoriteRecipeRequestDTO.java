package it.epicode.the_plant_based_hub_backend.payloads.entities;

public record FavoriteRecipeRequestDTO(
        long userId,
        long recipeId
) {
}
