package it.epicode.the_plant_based_hub_backend.payloads.entities;

import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;

import java.util.List;

public record RecipeResponseDTO(
        long id,
        String recipeName,
        String recipeDescription,
        RecipeCategory recipeCategory,
        String recipeInstructions,
        int preparationTime,
        int numberOfServings,
        int caloriesPerServing,
        List<RecipeIngredientRequestDTO> ingredients
) {
}
