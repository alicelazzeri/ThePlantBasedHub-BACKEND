package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {
}
