package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {
    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);
    List<Recipe> findByRecipeCategory(RecipeCategory recipeCategory);
    List<Recipe> findByIngredientsIngredientIngredientName(String ingredientName);
    List<Recipe> findByIngredientsIngredientIngredientCategory(IngredientCategory ingredientCategory);
}
