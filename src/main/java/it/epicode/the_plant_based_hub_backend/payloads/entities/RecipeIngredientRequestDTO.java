package it.epicode.the_plant_based_hub_backend.payloads.entities;

public record RecipeIngredientRequestDTO(
        long ingredientId,
        int quantity,
        String measurementUnit,
        long recipeId,
        String ingredientName
) {
}
