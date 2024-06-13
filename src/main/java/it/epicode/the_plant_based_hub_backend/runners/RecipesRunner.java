package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RecipesRunner implements CommandLineRunner {

    @Autowired
    private RecipeService recipeService;

    @Override
    public void run(String... args) throws Exception {

    }
}
