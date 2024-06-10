package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.IngredientDTO;
import it.epicode.the_plant_based_hub_backend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    // GET all ingredients

    @Transactional(readOnly = true)
    public Page<Ingredient> getAllIngredients(Pageable pageable){
        return ingredientRepository.findAll(pageable);
    }

    // GET ingredient by ID

    @Transactional(readOnly = true)
    public Ingredient getIngredientById(long id) {
        return ingredientRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Ingredient with id: " + id + " not found"));
    }

    // POST saving ingredient

    @Transactional
    public Ingredient saveIngredient(IngredientDTO ingredientPayload) {
        Ingredient ingredient = mapToEntity(ingredientPayload);
        return ingredientRepository.save(ingredient);
    }

    // PUT updating ingredient

    @Transactional
    public Ingredient updateIngredient(long id, IngredientDTO updatedIngredient) {
        Ingredient ingredientToBeUpdated = this.getIngredientById(id);
        if (ingredientToBeUpdated == null) {
            throw new NotFoundException("Ingredient with id: " + id + " not found");
        } else {
            updateIngredientFromDTO(ingredientToBeUpdated, updatedIngredient);
            return ingredientRepository.save(ingredientToBeUpdated);
        }
    }

    // DELETE ingredient

    @Transactional
    public void deleteIngredient(long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException("Ingredient with id: " + id + " not found");
        } else {
            ingredientRepository.deleteById(id);
        }
    }

    // Map IngredientDTO to Ingredient entity (converts IngredientDTO to an Ingredient entity instance in order to save or
    // update data on db via IngredientRepository)

    public Ingredient mapToEntity(IngredientDTO ingredientDTO) {
        Ingredient ingredient = Ingredient.builder()
                .withIngredientName(ingredientDTO.ingredientName())
                .withIngredientCategory(ingredientDTO.ingredientCategory())
                .withCaloriesPerServing(ingredientDTO.caloriesPerServing())
                .withRecommendedAmount(ingredientDTO.recommendedAmount())
                .build();
        return ingredient;
    }

    // update already existing ingredient from IngredientDTO

    private void updateIngredientFromDTO(Ingredient existingIngredient, IngredientDTO ingredientDTO) {
        existingIngredient.setIngredientName(ingredientDTO.ingredientName());
        existingIngredient.setIngredientCategory(ingredientDTO.ingredientCategory());
        existingIngredient.setCaloriesPerServing(ingredientDTO.caloriesPerServing());
        existingIngredient.setRecommendedAmount(ingredientDTO.recommendedAmount());
    }
}
