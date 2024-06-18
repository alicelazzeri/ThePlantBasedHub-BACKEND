package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>, PagingAndSortingRepository<Ingredient, Long> {
    Optional<Ingredient> findByIngredientName (String ingredientName);
    List<Ingredient> findByProteinsBetween(double minProteins, double maxProteins);
    List<Ingredient> findByCarbohydratesBetween(double minCarbohydrates, double maxCarbohydrates);
    List<Ingredient> findByFatsBetween(double minFats, double maxFats);
    List<Ingredient> findByFibersBetween(double minFibers, double maxFibers);
    List<Ingredient> findBySugarsBetween(double minSugars, double maxSugars);
    List<Ingredient> findByVitamins(String vitamins);
    List<Ingredient> findByMinerals(String minerals);
    List<Ingredient> findByIngredientCategory(IngredientCategory ingredientCategory);
}
