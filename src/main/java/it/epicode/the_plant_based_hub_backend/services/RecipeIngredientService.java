package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientDTO;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeService recipeService;

    // GET all recipe ingredients

    public Page<RecipeIngredient> getAllRecipeIngredients(Pageable pageable) {
        return recipeIngredientRepository.findAll(pageable);
    }

    // GET recipe ingredient by id

    public RecipeIngredient getRecipeIngredientById(long id) {
        return recipeIngredientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Recipe ingredient with id: " + id + " not found"));
    }

    // POST saving recipe ingredient

    public RecipeIngredient saveRecipeIngredient(RecipeIngredientDTO recipeIngredientPayload) {
        RecipeIngredient recipeIngredient = mapToEntity(recipeIngredientPayload);
        return recipeIngredientRepository.save(recipeIngredient);
    }

    // PUT updating recipe ingredient

    public RecipeIngredient updateRecipeIngredient(long id, RecipeIngredientDTO updatedRecipeIngredient) {
        RecipeIngredient recipeIngredientToBeUpdated = this.getRecipeIngredientById(id);
        if (recipeIngredientToBeUpdated == null) {
            throw new NotFoundException("Recipe ingredient with id: " + id + " not found");
        } else {
            updateRecipeIngredientFromDTO(recipeIngredientToBeUpdated, updatedRecipeIngredient);
            return recipeIngredientRepository.save(recipeIngredientToBeUpdated);
        }
    }

    // DELETE recipe ingredient

    public void deleteRecipeIngredient(long id) {
        if (!recipeIngredientRepository.existsById(id)) {
            throw new NotFoundException("Recipe ingredient with id: " + id + " not found");
        } else {
            recipeIngredientRepository.deleteById(id);
        }
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity (converts RecipeIngredientDTO to a RecipeIngredient entity instance in order to save or
    // update data on db via RecipeIngredientRepository)

    public RecipeIngredient mapToEntity(RecipeIngredientDTO recipeIngredientDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientDTO.ingredientId());
        Recipe recipe = recipeService.getRecipeById(recipeIngredientDTO.recipeId());
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .withQuantity(recipeIngredientDTO.quantity())
                .withMeasurementUnit(recipeIngredientDTO.measurementUnit())
                .withRecipe(recipe)
                .withIngredient(ingredient)
                .build();
        return recipeIngredient;
    }

    // update already existing recipe ingredient from RecipeIngredientDTO

    public void updateRecipeIngredientFromDTO(RecipeIngredient existingRecipeIngredient, RecipeIngredientDTO recipeIngredientDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientDTO.ingredientId());
        Recipe recipe = recipeService.getRecipeById(recipeIngredientDTO.recipeId());
        existingRecipeIngredient.setQuantity(recipeIngredientDTO.quantity());
        existingRecipeIngredient.setMeasurementUnit(recipeIngredientDTO.measurementUnit());
        existingRecipeIngredient.setRecipe(recipe);
        existingRecipeIngredient.setIngredient(ingredient);
    }
}
