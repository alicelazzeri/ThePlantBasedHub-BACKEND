package it.epicode.capstone_project_alicelazzeri.repositories;

import it.epicode.capstone_project_alicelazzeri.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>, PagingAndSortingRepository<Recipe, UUID> {
    @Query("SELECT r FROM Recipe AS r JOIN r.ingredients AS ri WHERE ri.ingredient.id IN :ingredientIds GROUP BY r.id HAVING COUNT(ri.id) = :ingredientCount")
    public List<Recipe> findRecipesByIngredients(@Param("ingredientIds") List<UUID> ingredientIds, @Param("ingredientCount") long ingredientCount);
}
