package it.epicode.the_plant_based_hub_backend.payloads.entities;

import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import jakarta.validation.constraints.*;

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
        String recommendedAmount,

        @PositiveOrZero(message = "Proteins must be zero or greater")
        Double proteins,

        @PositiveOrZero(message = "Carbohydrates must be zero or greater")
        Double carbohydrates,

        @PositiveOrZero(message = "Fats must be zero or greater")
        Double fats,

        @PositiveOrZero(message = "Fibers must be zero or greater")
        Double fibers,

        @PositiveOrZero(message = "Sugars must be zero or greater")
        Double sugars,

        @NotNull(message = "Vitamins is mandatory")
        @NotEmpty(message = "Vitamins cannot be empty")
        String vitamins,

        @NotNull(message = "Minerals is mandatory")
        @NotEmpty(message = "Minerals cannot be empty")
        String minerals
) {
}
