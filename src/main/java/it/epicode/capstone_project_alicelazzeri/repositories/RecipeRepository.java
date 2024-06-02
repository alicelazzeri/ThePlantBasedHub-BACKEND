package it.epicode.capstone_project_alicelazzeri.repositories;

import it.epicode.capstone_project_alicelazzeri.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, PagingAndSortingRepository<Recipe, UUID> {
}
