package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    // GET all recipes

    @Transactional(readOnly = true)
    public Page<Recipe> getAllRecipes(Pageable pageable){
        return recipeRepository.findAll(pageable);
    }

    // GET recipe by ID

    @Transactional(readOnly = true)
    public Recipe getRecipeById(long id) {
        return recipeRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Recipe with id: " + id + " not found"));
    }

    // POST saving recipe

    @Transactional
    public Recipe saveRecipe(RecipeRequestDTO recipePayload) {
        Recipe recipe = mapToEntity(recipePayload);
        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        return recipeRepository.save(recipe);
    }

    // PUT updating recipe

    @Transactional
    public Recipe updateRecipe(long id, RecipeRequestDTO updatedRecipe) {
        Recipe recipeToBeUpdated = this.getRecipeById(id);
        if (recipeToBeUpdated == null) {
            throw new NotFoundException("Recipe with id: " + id + " not found");
        } else {
            updateRecipeFromDTO(recipeToBeUpdated, updatedRecipe);
            return recipeRepository.save(recipeToBeUpdated);
        }
    }

    // DELETE recipe

    @Transactional
    public void deleteRecipe(long id) {
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException("Recipe with id: " + id + " not found");
        } else {
            recipeRepository.deleteById(id);
        }
    }

    // Map RecipeDTO to Recipe entity (converts RecipeDTO to a Recipe entity instance in order to save or
    // update data on db via RecipeRepository)

    private Recipe mapToEntity(RecipeRequestDTO recipeRequestDTO) {
        List<RecipeIngredient> ingredients = recipeRequestDTO.ingredients().stream()
                .map(this::mapToRecipeIngredientEntity)
                .collect(Collectors.toList());

        Recipe recipe = Recipe.builder()
                .withRecipeName(recipeRequestDTO.recipeName())
                .withRecipeDescription(recipeRequestDTO.recipeDescription())
                .withRecipeCategory(recipeRequestDTO.recipeCategory())
                .withRecipeInstructions(recipeRequestDTO.recipeInstructions())
                .withPreparationTime(recipeRequestDTO.preparationTime())
                .withNumberOfServings(recipeRequestDTO.numberOfServings())
                .withCaloriesPerServing(recipeRequestDTO.caloriesPerServing())
                .withIngredients(ingredients)
                .build();
        return recipe;
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity (converts RecipeIngredientDTO to a RecipeIngredient entity instance in order to save or
    // update data on db via recipeIngredientRepository)

    private RecipeIngredient mapToRecipeIngredientEntity(RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientRequestDTO.ingredientId());
        return RecipeIngredient.builder()
                .withQuantity(recipeIngredientRequestDTO.quantity())
                .withMeasurementUnit(recipeIngredientRequestDTO.measurementUnit())
                .withIngredient(ingredient)
                .build();
    }

    // update already existing recipe from RecipeDTO

    private void updateRecipeFromDTO(Recipe existingRecipe, RecipeRequestDTO recipeRequestDTO) {
        existingRecipe.setRecipeName(recipeRequestDTO.recipeName());
        existingRecipe.setRecipeDescription(recipeRequestDTO.recipeDescription());
        existingRecipe.setRecipeCategory(recipeRequestDTO.recipeCategory());
        existingRecipe.setRecipeInstructions(recipeRequestDTO.recipeInstructions());
        existingRecipe.setPreparationTime(recipeRequestDTO.preparationTime());
        existingRecipe.setNumberOfServings(recipeRequestDTO.numberOfServings());
        existingRecipe.setCaloriesPerServing(recipeRequestDTO.caloriesPerServing());

        List<RecipeIngredient> ingredients = recipeRequestDTO.ingredients().stream()
                .map(this::mapToRecipeIngredientEntity)
                .collect(Collectors.toList());
        existingRecipe.setIngredients(ingredients);
    }
}
