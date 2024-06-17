package it.epicode.the_plant_based_hub_backend.payloads.entities;

import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IngredientRequestDTO(
        @NotNull(message = "Ingredient name is mandatory")
        @NotEmpty(message = "Ingredient name cannot be empty")
        String ingredientName,

        @NotNull(message = "Ingredient category is mandatory")
        IngredientCategory ingredientCategory,

        @NotNull(message = "Calories per serving are mandatory")
        @Positive(message = "Calories per serving must be greater than zero")
        int caloriesPerServing,

        @NotNull(message = "Recommended amount is mandatory")
        @NotEmpty(message = "Recommended amount cannot be empty")
        String recommendedAmount
) {
}
