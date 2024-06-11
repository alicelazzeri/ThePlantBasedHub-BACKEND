package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientDTO;
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
    public Recipe saveRecipe(RecipeDTO recipePayload) {
        Recipe recipe = mapToEntity(recipePayload);
        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        return recipeRepository.save(recipe);
    }

    // PUT updating recipe

    @Transactional
    public Recipe updateRecipe(long id, RecipeDTO updatedRecipe) {
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

    private Recipe mapToEntity(RecipeDTO recipeDTO) {
        List<RecipeIngredient> ingredients = recipeDTO.ingredients().stream()
                .map(this::mapToRecipeIngredientEntity)
                .collect(Collectors.toList());

        Recipe recipe = Recipe.builder()
                .withRecipeName(recipeDTO.recipeName())
                .withRecipeDescription(recipeDTO.recipeDescription())
                .withRecipeCategory(recipeDTO.recipeCategory())
                .withRecipeInstructions(recipeDTO.recipeInstructions())
                .withPreparationTime(recipeDTO.preparationTime())
                .withNumberOfServings(recipeDTO.numberOfServings())
                .withCaloriesPerServing(recipeDTO.caloriesPerServing())
                .withIngredients(ingredients)
                .build();
        return recipe;
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity (converts RecipeIngredientDTO to a RecipeIngredient entity instance in order to save or
    // update data on db via recipeIngredientRepository)

    private RecipeIngredient mapToRecipeIngredientEntity(RecipeIngredientDTO recipeIngredientDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientDTO.ingredientId());
        return RecipeIngredient.builder()
                .withQuantity(recipeIngredientDTO.quantity())
                .withMeasurementUnit(recipeIngredientDTO.measurementUnit())
                .withIngredient(ingredient)
                .build();
    }

    // update already existing recipe from RecipeDTO

    private void updateRecipeFromDTO(Recipe existingRecipe, RecipeDTO recipeDTO) {
        existingRecipe.setRecipeName(recipeDTO.recipeName());
        existingRecipe.setRecipeDescription(recipeDTO.recipeDescription());
        existingRecipe.setRecipeCategory(recipeDTO.recipeCategory());
        existingRecipe.setRecipeInstructions(recipeDTO.recipeInstructions());
        existingRecipe.setPreparationTime(recipeDTO.preparationTime());
        existingRecipe.setNumberOfServings(recipeDTO.numberOfServings());
        existingRecipe.setCaloriesPerServing(recipeDTO.caloriesPerServing());

        List<RecipeIngredient> ingredients = recipeDTO.ingredients().stream()
                .map(this::mapToRecipeIngredientEntity)
                .collect(Collectors.toList());
        existingRecipe.setIngredients(ingredients);
    }
}
