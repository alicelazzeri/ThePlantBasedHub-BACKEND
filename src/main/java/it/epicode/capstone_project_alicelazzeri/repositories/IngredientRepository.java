package it.epicode.capstone_project_alicelazzeri.repositories;

import it.epicode.capstone_project_alicelazzeri.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, PagingAndSortingRepository<Ingredient, UUID> {
}
