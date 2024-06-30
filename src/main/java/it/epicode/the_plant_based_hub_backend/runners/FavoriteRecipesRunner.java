package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.payloads.entities.FavoriteRecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.FavoriteRecipeService;
import it.epicode.the_plant_based_hub_backend.services.RecipeService;
import it.epicode.the_plant_based_hub_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(4)
public class FavoriteRecipesRunner implements CommandLineRunner {

    @Autowired
    private FavoriteRecipeService favoriteRecipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Override
    public void run(String... args) throws Exception {
        populateFavoriteRecipes();
    }

    private void populateFavoriteRecipes() {
        List<User> users = userService.getAllUsers(PageRequest.of(0, 100)).getContent();
        List<Recipe> recipes = recipeService.getAllRecipes(PageRequest.of(0, 100)).getContent();

        for (User user : users) {
            for (int i = 0; i < Math.min(3, recipes.size()); i++) { // Aggiungi fino a 3 ricette preferite per ogni utente
                Recipe recipe = recipes.get(i);

                FavoriteRecipeRequestDTO favoriteRecipeRequestDTO = new FavoriteRecipeRequestDTO(
                        user.getId(),
                        recipe.getId()
                );

                try {
                    favoriteRecipeService.saveFavoriteRecipe(favoriteRecipeRequestDTO);
                } catch (Exception e) {
                    System.err.println("Error saving favorite recipe for user: " + user.getEmail() + ". " + e.getMessage());
                }
            }
        }
        System.out.println("Favorite recipes have been successfully added to the DB.");
    }
}
