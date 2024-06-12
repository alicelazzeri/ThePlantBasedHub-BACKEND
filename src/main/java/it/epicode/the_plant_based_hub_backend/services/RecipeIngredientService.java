package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeIngredientService {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeService recipeService;

    // GET all recipe ingredients

    @Transactional(readOnly = true)
    public Page<RecipeIngredient> getAllRecipeIngredients(Pageable pageable) {
        return recipeIngredientRepository.findAll(pageable);
    }

    // GET recipe ingredient by id

    @Transactional(readOnly = true)
    public RecipeIngredient getRecipeIngredientById(long id) {
        return recipeIngredientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Recipe ingredient with id: " + id + " not found"));
    }

    // POST saving recipe ingredient

    @Transactional
    public RecipeIngredient saveRecipeIngredient(RecipeIngredientRequestDTO recipeIngredientPayload) {
        RecipeIngredient recipeIngredient = mapToEntity(recipeIngredientPayload);
        return recipeIngredientRepository.save(recipeIngredient);
    }

    // PUT updating recipe ingredient

    @Transactional
    public RecipeIngredient updateRecipeIngredient(long id, RecipeIngredientRequestDTO updatedRecipeIngredient) {
        RecipeIngredient recipeIngredientToBeUpdated = this.getRecipeIngredientById(id);
        if (recipeIngredientToBeUpdated == null) {
            throw new NotFoundException("Recipe ingredient with id: " + id + " not found");
        } else {
            updateRecipeIngredientFromDTO(recipeIngredientToBeUpdated, updatedRecipeIngredient);
            return recipeIngredientRepository.save(recipeIngredientToBeUpdated);
        }
    }

    // DELETE recipe ingredient

    @Transactional
    public void deleteRecipeIngredient(long id) {
        if (!recipeIngredientRepository.existsById(id)) {
            throw new NotFoundException("Recipe ingredient with id: " + id + " not found");
        } else {
            recipeIngredientRepository.deleteById(id);
        }
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity (converts RecipeIngredientDTO to a RecipeIngredient entity instance in order to save or
    // update data on db via RecipeIngredientRepository)

    public RecipeIngredient mapToEntity(RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientRequestDTO.ingredientId());
        Recipe recipe = recipeService.getRecipeById(recipeIngredientRequestDTO.recipeId());
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .withQuantity(recipeIngredientRequestDTO.quantity())
                .withMeasurementUnit(recipeIngredientRequestDTO.measurementUnit())
                .withRecipe(recipe)
                .withIngredient(ingredient)
                .build();
        return recipeIngredient;
    }

    // update already existing recipe ingredient from RecipeIngredientDTO

    public void updateRecipeIngredientFromDTO(RecipeIngredient existingRecipeIngredient, RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientRequestDTO.ingredientId());
        Recipe recipe = recipeService.getRecipeById(recipeIngredientRequestDTO.recipeId());
        existingRecipeIngredient.setQuantity(recipeIngredientRequestDTO.quantity());
        existingRecipeIngredient.setMeasurementUnit(recipeIngredientRequestDTO.measurementUnit());
        existingRecipeIngredient.setRecipe(recipe);
        existingRecipeIngredient.setIngredient(ingredient);
    }
}
