package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>, PagingAndSortingRepository<Ingredient, Long> {
    Ingredient findFirstByIngredientNameIgnoreCase(String ingredientName);
    List<Ingredient> findByIngredientNameContainingIgnoreCase(String ingredientName); // partial research
    List<Ingredient> findByProteinsBetween(double minProteins, double maxProteins);
    List<Ingredient> findByCarbohydratesBetween(double minCarbohydrates, double maxCarbohydrates);
    List<Ingredient> findByFatsBetween(double minFats, double maxFats);
    List<Ingredient> findByFibersBetween(double minFibers, double maxFibers);
    List<Ingredient> findBySugarsBetween(double minSugars, double maxSugars);
    List<Ingredient> findByVitaminsContainingIgnoreCase(String vitamins);
    List<Ingredient> findByMineralsContainingIgnoreCase(String minerals);
    List<Ingredient> findByIngredientCategory(IngredientCategory ingredientCategory);
}
