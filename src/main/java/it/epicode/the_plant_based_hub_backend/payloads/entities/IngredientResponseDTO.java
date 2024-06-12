package it.epicode.the_plant_based_hub_backend.payloads.entities;

import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;

public record IngredientResponseDTO(
        long id,
        String ingredientName,
        IngredientCategory ingredientCategory,
        int caloriesPerServing,
        String recommendedAmount
) {
}
