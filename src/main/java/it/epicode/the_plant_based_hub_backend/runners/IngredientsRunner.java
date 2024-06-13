package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class IngredientsRunner implements CommandLineRunner {

    @Autowired
    private IngredientService ingredientService;

    @Override
    public void run(String... args) throws Exception {

    }
}
