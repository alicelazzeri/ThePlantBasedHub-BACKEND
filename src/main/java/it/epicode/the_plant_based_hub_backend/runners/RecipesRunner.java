package it.epicode.the_plant_based_hub_backend.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RecipesRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
