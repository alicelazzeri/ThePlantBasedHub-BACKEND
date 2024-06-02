package it.epicode.capstone_project_alicelazzeri.repositories;

import it.epicode.capstone_project_alicelazzeri.entities.FavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, UUID>, PagingAndSortingRepository<FavoriteRecipe, UUID> {
}
