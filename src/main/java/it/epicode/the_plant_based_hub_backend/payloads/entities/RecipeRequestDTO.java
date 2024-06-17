package it.epicode.the_plant_based_hub_backend.payloads.entities;

import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeRequestDTO(
        @NotNull(message = "Recipe name is mandatory")
        @NotEmpty(message = "Recipe name cannot be empty")
        @Size(min = 3, max = 100, message = "Recipe name should be between 3 and 100 characters long")
        String recipeName,

        @NotNull(message = "Recipe description is mandatory")
        @NotEmpty(message = "Recipe description cannot be empty")
        String recipeDescription,

        @NotNull(message = "Recipe category is mandatory")
        RecipeCategory recipeCategory,

        @NotNull(message = "Recipe instructions is mandatory")
        @NotEmpty(message = "Recipe instructions cannot be empty")
        String recipeInstructions,

        @NotNull(message = "Preparation time is mandatory")
        @Positive(message = "Preparation time must be greater than zero")
        int preparationTime,

        @NotNull(message = "Number of servings are mandatory")
        @Positive(message = "Number of servings must be greater than zero")
        int numberOfServings,

        @NotNull(message = "Calories per serving are mandatory")
        @Positive(message = "Calories per serving must be greater than zero")
        int caloriesPerServing,

        List<RecipeIngredientRequestDTO> ingredients
) {
}
