package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.FavoriteRecipe;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.FavoriteRecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.FavoriteRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteRecipeService {
    @Autowired
    private FavoriteRecipeRepository favoriteRecipeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    // GET all favorite recipes

    @Transactional(readOnly = true)
    public Page<FavoriteRecipe> getAllFavoriteRecipes(Pageable pageable) {
        return favoriteRecipeRepository.findAll(pageable);
    }

    // GET favorite recipe by id

    @Transactional(readOnly = true)
    public FavoriteRecipe getFavoriteRecipeById(long id) {
        return favoriteRecipeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Favorite recipe with id: " + id + " not found"));
    }

    // POST saving favorite recipe

    @Transactional
    public FavoriteRecipe saveFavoriteRecipe(FavoriteRecipeRequestDTO favoriteRecipePayload) {
        FavoriteRecipe favoriteRecipe = mapToEntity(favoriteRecipePayload);
        return favoriteRecipeRepository.save(favoriteRecipe);
    }

    // PUT updating favorite recipe

    @Transactional
    public FavoriteRecipe updateFavoriteRecipe(long id, FavoriteRecipeRequestDTO updatedFavoriteRecipe) {
        FavoriteRecipe favoriteRecipeToBeUpdated = this.getFavoriteRecipeById(id);
        updateFavoriteRecipeFromDTO(favoriteRecipeToBeUpdated, updatedFavoriteRecipe);
        return favoriteRecipeRepository.save(favoriteRecipeToBeUpdated);
    }

    // DELETE favorite recipe

    @Transactional
    public void deleteFavoriteRecipe(long id) {
        if (!favoriteRecipeRepository.existsById(id)) {
            throw new NotFoundException("Favorite recipe with id: " + id + " not found");
        }
        favoriteRecipeRepository.deleteById(id);
    }

    // Map FavoriteRecipeDTO to FavoriteRecipe entity

    public FavoriteRecipe mapToEntity(FavoriteRecipeRequestDTO favoriteRecipeRequestDTO) {
        User user = userService.getUserById(favoriteRecipeRequestDTO.userId())
                .orElseThrow(() -> new NotFoundException("User with id: " + favoriteRecipeRequestDTO.userId() + " not found"));
        Recipe recipe = recipeService.getRecipeById(favoriteRecipeRequestDTO.recipeId());

        return FavoriteRecipe.builder()
                .withUser(user)
                .withRecipe(recipe)
                .build();
    }

    // Update already existing favorite recipe

    public void updateFavoriteRecipeFromDTO(FavoriteRecipe existingFavoriteRecipe, FavoriteRecipeRequestDTO favoriteRecipeRequestDTO) {
        User user = userService.getUserById(favoriteRecipeRequestDTO.userId())
                .orElseThrow(() -> new NotFoundException("User with id: " + favoriteRecipeRequestDTO.userId() + " not found"));
        Recipe recipe = recipeService.getRecipeById(favoriteRecipeRequestDTO.recipeId());

        existingFavoriteRecipe.setUser(user);
        existingFavoriteRecipe.setRecipe(recipe);
    }
}
