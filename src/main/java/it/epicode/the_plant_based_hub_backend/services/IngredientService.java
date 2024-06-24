package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.IngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Ingredient saveIngredient(IngredientRequestDTO ingredientPayload) {
        Ingredient ingredient = mapToEntity(ingredientPayload);
        return ingredientRepository.save(ingredient);
    }

    // PUT updating ingredient

    @Transactional
    public Ingredient updateIngredient(long id, IngredientRequestDTO updatedIngredient) {
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

    public Ingredient mapToEntity(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = Ingredient.builder()
                .withIngredientName(ingredientRequestDTO.ingredientName())
                .withIngredientCategory(ingredientRequestDTO.ingredientCategory())
                .withCaloriesPerServing(ingredientRequestDTO.caloriesPerServing())
                .withRecommendedAmount(ingredientRequestDTO.recommendedAmount())
                .withProteins(ingredientRequestDTO.proteins())
                .withCarbohydrates(ingredientRequestDTO.carbohydrates())
                .withFats(ingredientRequestDTO.fats())
                .withFibers(ingredientRequestDTO.fibers())
                .withSugars(ingredientRequestDTO.sugars())
                .withVitamins(ingredientRequestDTO.vitamins())
                .withMinerals(ingredientRequestDTO.minerals())
                .build();
        return ingredient;
    }

    // update already existing ingredient from IngredientDTO

    private void updateIngredientFromDTO(Ingredient existingIngredient, IngredientRequestDTO ingredientRequestDTO) {
        existingIngredient.setIngredientName(ingredientRequestDTO.ingredientName());
        existingIngredient.setIngredientCategory(ingredientRequestDTO.ingredientCategory());
        existingIngredient.setCaloriesPerServing(ingredientRequestDTO.caloriesPerServing());
        existingIngredient.setRecommendedAmount(ingredientRequestDTO.recommendedAmount());
        existingIngredient.setProteins(ingredientRequestDTO.proteins());
        existingIngredient.setCarbohydrates(ingredientRequestDTO.carbohydrates());
        existingIngredient.setFats(ingredientRequestDTO.fats());
        existingIngredient.setFibers(ingredientRequestDTO.fibers());
        existingIngredient.setSugars(ingredientRequestDTO.sugars());
        existingIngredient.setVitamins(ingredientRequestDTO.vitamins());
        existingIngredient.setMinerals(ingredientRequestDTO.minerals());
    }

    // GET ingredient by name

    @Transactional(readOnly = true)
    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientRepository.findFirstByIngredientNameIgnoreCase(ingredientName);
    }

    // GET ingredients by name

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByNameContaining(String ingredientName) {
        return ingredientRepository.findByIngredientNameContainingIgnoreCase(ingredientName);
    }

    // GET ingredients by proteins range

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByProteinsBetween(double minProteins, double maxProteins) {
        return ingredientRepository.findByProteinsBetween(minProteins, maxProteins);
    }

    // GET ingredients by carbohydrates range

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByCarbohydratesBetween(double minCarbohydrates, double maxCarbohydrates) {
        return ingredientRepository.findByCarbohydratesBetween(minCarbohydrates, maxCarbohydrates);
    }

    // GET ingredients by fats range

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByFatsBetween(double minFats, double maxFats) {
        return ingredientRepository.findByFatsBetween(minFats, maxFats);
    }

    // GET ingredients by fibers range

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByFibersBetween(double minFibers, double maxFibers) {
        return ingredientRepository.findByFibersBetween(minFibers, maxFibers);
    }

    // GET ingredients by sugars range

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsBySugarsBetween(double minSugars, double maxSugars) {
        return ingredientRepository.findBySugarsBetween(minSugars, maxSugars);
    }

    // GET ingredients by vitamins

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByVitamins(String vitamins) {
        return ingredientRepository.findByVitaminsContainingIgnoreCase(vitamins);
    }

    // GET ingredients by minerals

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByMinerals(String minerals) {
        return ingredientRepository.findByMineralsContainingIgnoreCase(minerals);
    }

    // GET ingredients by ingredient category

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByIngredientCategory(IngredientCategory ingredientCategory) {
        return ingredientRepository.findByIngredientCategory(ingredientCategory);
    }
}
