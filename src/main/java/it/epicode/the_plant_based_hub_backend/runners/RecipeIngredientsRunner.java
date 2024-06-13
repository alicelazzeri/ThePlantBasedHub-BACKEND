package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.services.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RecipeIngredientsRunner implements CommandLineRunner {

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @Override
    public void run(String... args) throws Exception {

    }
}
