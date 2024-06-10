package it.epicode.the_plant_based_hub_backend.payloads.entities;

public record RecipeIngredientDTO(
        int quantity,
        String measurementUnit,
        long recipeId,
        long ingredientId
) {
}
