package it.epicode.the_plant_based_hub_backend.payloads.entities;

public record FavoriteRecipeDTO(
        long userId,
        long recipeId
) {
}
