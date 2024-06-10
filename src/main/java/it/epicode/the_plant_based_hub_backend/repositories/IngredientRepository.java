package it.epicode.the_plant_based_hub_backend.repositories;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>, PagingAndSortingRepository<Ingredient, Long> {
}
