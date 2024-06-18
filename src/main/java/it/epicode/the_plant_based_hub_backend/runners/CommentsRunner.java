package it.epicode.the_plant_based_hub_backend.runners;

import com.github.javafaker.Faker;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.payloads.entities.CommentRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.CommentService;
import it.epicode.the_plant_based_hub_backend.services.RecipeService;
import it.epicode.the_plant_based_hub_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Order(4)
public class CommentsRunner implements CommandLineRunner {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    private Faker faker = new Faker();
    private Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        createComments();
    }

    private void createComments() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<User> users = userService.getAllUsers(pageable).getContent();
        List<Recipe> recipes = recipeService.getAllRecipes(pageable).getContent();;

        if (recipes.isEmpty() || users.isEmpty()) {
            System.out.println("No recipes or users found in the database.");
            return;
        }

        for (User user : users) {
            for (int i = 0; i < 5; i++) {
                Recipe recipe = recipes.get(random.nextInt(recipes.size()));
                CommentRequestDTO commentDto = new CommentRequestDTO(
                        random.nextInt(5) + 1,
                        generateComment(recipe),
                        user.getId(),
                        recipe.getId()
                );
                commentService.saveComment(commentDto);
            }
        }
        System.out.println("Comments have been added.");
    }

    private String generateComment(Recipe recipe) {
        String[] comments = {
                "This " + recipe.getRecipeName() + " was absolutely delicious! Highly recommend.",
                "I tried the " + recipe.getRecipeName() + " and it was so tasty. Will make it again!",
                "The " + recipe.getRecipeName() + " recipe was easy to follow and turned out great.",
                "Loved the " + recipe.getRecipeName() + "! Perfect for a quick and healthy meal.",
                "The " + recipe.getRecipeName() + " was a hit with my family. We all enjoyed it."
        };
        return comments[random.nextInt(comments.length)];
    }
}
