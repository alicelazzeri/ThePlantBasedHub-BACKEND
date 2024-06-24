package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.IngredientService;
import it.epicode.the_plant_based_hub_backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(2)
public class RecipesRunner implements CommandLineRunner {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @Override
    public void run(String... args) throws Exception {
        try {
            populateRecipes();
        } catch (Exception e) {
            System.err.println("Error populating recipes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateRecipes() {
        List<RecipeRequestDTO> recipes = new ArrayList<>();

        // BREAKFAST
        recipes.add(new RecipeRequestDTO(
                "Vegan Tofu Scramble",
                "A delicious and easy-to-make tofu scramble perfect for breakfast. This vegan tofu scramble is packed with proteins and veggies, making it a healthy start to your day.",
                RecipeCategory.BREAKFAST,
                "1. Heat oil in a pan.\n2. Crumble tofu and add to the pan.\n3. Add spices and vegetables. Cook for 10 minutes until vegetables are tender and tofu is slightly crispy.",
                15,
                2,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tofu").getId(), 200, "g", 0, "Tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 50, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Spinach").getId(), 50, "g", 0, "Spinach"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 1, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Turmeric").getId(), 1, "tsp", 0, "Turmeric"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Pancakes",
                "Fluffy vegan pancakes perfect for a weekend breakfast. These pancakes are light and airy, with a hint of sweetness from the bananas.",
                RecipeCategory.BREAKFAST,
                "1. Mix all ingredients to form a smooth batter.\n2. Heat a non-stick griddle and pour 1/4 cup of batter for each pancake.\n3. Cook until bubbles form on the surface, then flip and cook until golden brown on the other side.",
                20,
                4,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oats").getId(), 200, "g", 0, "Oats"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 100, "g", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 100, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 1, "tbsp", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Smoothie Bowl",
                "A refreshing and nutritious smoothie bowl topped with fruits and nuts. Perfect for a quick and healthy breakfast.",
                RecipeCategory.BREAKFAST,
                "1. Blend frozen berries, banana, and almond milk until smooth.\n2. Pour into a bowl and top with granola, sliced fruits, and nuts.\n3. Enjoy immediately.",
                10,
                1,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Frozen berries").getId(), 100, "g", 0, "Frozen berries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 100, "g", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 200, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Granola").getId(), 50, "g", 0, "Granola"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Chia Pudding",
                "A simple and delicious chia pudding, perfect for breakfast or a snack. Customize with your favorite toppings.",
                RecipeCategory.BREAKFAST,
                "1. Combine chia seeds and almond milk in a bowl.\n2. Stir well to prevent clumping.\n3. Refrigerate overnight.\n4. Serve with fresh fruits and walnuts.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chia seeds").getId(), 50, "g", 0, "Chia seeds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 200, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Fresh fruits").getId(), 100, "g", 0, "Fresh fruits"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Walnuts").getId(), 30, "g", 0, "Walnuts"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Avocado Toast",
                "A simple and tasty avocado toast, perfect for a quick breakfast or snack.",
                RecipeCategory.BREAKFAST,
                "1. Toast the bread slices.\n2. Mash the avocado and spread on the toast.\n3. Sprinkle with salt, pepper, and lemon juice.\n4. Top with cherry tomatoes and enjoy.",
                5,
                1,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole grain bread").getId(), 2, "slices", 0, "Whole grain bread"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "unit", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cherry tomatoes").getId(), 50, "g", 0, "Cherry tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 1, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Breakfast Burrito",
                "A hearty vegan breakfast burrito filled with tofu scramble, black beans, and veggies.",
                RecipeCategory.BREAKFAST,
                "1. Heat oil in a pan and add crumbled tofu.\n2. Add black beans, bell peppers, and spices. Cook for 10 minutes.\n3. Warm tortillas and fill with tofu mixture. Roll up and enjoy.",
                20,
                2,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tofu").getId(), 200, "g", 0, "Tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 100, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 50, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tortillas").getId(), 2, "units", 0, "Tortillas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 1, "tsp", 0, "Chili powder")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Overnight Oats",
                "A simple and delicious overnight oats recipe that you can prepare in advance for a quick breakfast.",
                RecipeCategory.BREAKFAST,
                "1. Combine oats, almond milk, chia seeds, and maple syrup in a jar.\n2. Stir well and refrigerate overnight.\n3. Serve with fresh berries and nuts.",
                5,
                1,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oats").getId(), 50, "g", 0, "Oats"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 200, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chia seeds").getId(), 10, "g", 0, "Chia seeds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 1, "tbsp", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Banana Bread",
                "A moist and delicious vegan banana bread that's perfect for breakfast or as a snack.",
                RecipeCategory.BREAKFAST,
                "1. Preheat oven to 180°C.\n2. Mash bananas and mix with flour, sugars, and baking powder.\n3. Pour batter into a loaf pan and bake for 45 minutes.",
                60,
                8,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 3, "units", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 100, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tbsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Nutmeg").getId(), 1, "tsp", 0, "Nutmeg")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Granola",
                "A crunchy and delicious vegan granola that you can enjoy with almond milk or yogurt.",
                RecipeCategory.BREAKFAST,
                "1. Preheat oven to 150°C.\n2. Mix oats, hazelnuts, and maple syrup in a bowl.\n3. Spread mixture on a baking sheet and bake for 30 minutes, stirring occasionally.",
                40,
                10,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oats").getId(), 200, "g", 0, "Oats"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Hazelnuts").getId(), 100, "g", 0, "Hazelnuts"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 2, "tbsp", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Breakfast Muffins",
                "Delicious and healthy vegan breakfast muffins made with whole wheat flour and fresh berries.",
                RecipeCategory.BREAKFAST,
                "1. Preheat oven to 180°C.\n2. Mix flour, sugars, and baking powder in a bowl.\n3. Add almond milk and berries. Mix well.\n4. Pour batter into muffin tins and bake for 25 minutes.",
                35,
                6,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole wheat flour").getId(), 200, "g", 0, "Whole wheat flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 50, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tbsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 100, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Blackberries").getId(), 100, "g", 0, "Blackberries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Raspberries").getId(), 100, "g", 0, "Raspberries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Strawberries").getId(), 100, "g", 0, "Strawberries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        // LUNCH
        recipes.add(new RecipeRequestDTO(
                "Quinoa Salad with Avocado",
                "A refreshing quinoa salad with avocado, black beans, and corn. Perfect for a light and healthy lunch.",
                RecipeCategory.LUNCH,
                "1. Cook quinoa according to package instructions and let it cool.\n2. In a large bowl, combine cooked quinoa, diced avocado, black beans, corn, cherry tomatoes, and cilantro.\n3. Dress with olive oil, lime juice, salt, and pepper.\n4. Toss gently and serve chilled.",
                30,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 150, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Corn").getId(), 150, "g", 0, "Corn"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cherry tomatoes").getId(), 100, "g", 0, "Cherry tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 10, "g", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime juice").getId(), 1, "pcs", 0, "Lime juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Buddha Bowl",
                "A nourishing vegan Buddha bowl with roasted vegetables, quinoa, and a tahini dressing. Perfect for a satisfying lunch.",
                RecipeCategory.LUNCH,
                "1. Preheat the oven to 200°C (400°F).\n2. Toss sweet potatoes, bell peppers, and broccoli with olive oil, salt, and pepper. Roast for 20-25 minutes.\n3. Cook quinoa according to package instructions.\n4. In a bowl, combine cooked quinoa, roasted vegetables, avocado slices, and chickpeas.\n5. Drizzle with tahini dressing and serve.",
                40,
                4,
                450,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sweet potatoes").getId(), 200, "g", 0, "Sweet potatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 100, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Broccoli").getId(), 100, "g", 0, "Broccoli"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 150, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tahini").getId(), 3, "tbsp", 0, "Tahini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Lentil Soup",
                "A hearty and comforting lentil soup, perfect for a nutritious lunch. Made with lentils, vegetables, and spices.",
                RecipeCategory.LUNCH,
                "1. In a large pot, sauté onions, garlic, carrots, and celery in olive oil until softened.\n2. Add lentils, diced tomatoes, vegetable broth, and spices.\n3. Bring to a boil, then reduce heat and simmer for 30 minutes.\n4. Serve hot with a sprinkle of fresh parsley.",
                45,
                6,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 100, "g", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lentils").getId(), 200, "g", 0, "Lentils"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 1, "L", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Sushi Rolls",
                "Delicious vegan sushi rolls filled with avocado, cucumber, and carrots. Perfect for a light and healthy lunch.",
                RecipeCategory.LUNCH,
                "1. Cook sushi rice according to package instructions and let it cool.\n2. Place a nori sheet on a bamboo mat and spread a thin layer of sushi rice over it.\n3. Arrange avocado, cucumber, and carrot strips on the rice.\n4. Roll tightly and slice into pieces.\n5. Serve with soy sauce and pickled ginger.",
                30,
                4,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sushi rice").getId(), 200, "g", 0, "Sushi rice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Nori").getId(), 4, "sheets", 0, "Nori"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cucumbers").getId(), 100, "g", 0, "Cucumbers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Soy sauce").getId(), 2, "tbsp", 0, "Soy sauce")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Chickpea Salad Sandwich",
                "A satisfying chickpea salad sandwich, perfect for a quick and healthy lunch. Made with mashed chickpeas, celery, and vegan mayo.",
                RecipeCategory.LUNCH,
                "1. In a bowl, mash chickpeas with a fork.\n2. Add diced celery, vegan mayo, lemon juice, salt, and pepper.\n3. Spread the chickpea salad on whole grain bread slices and top with lettuce and tomato slices.\n4. Serve as a sandwich.",
                15,
                2,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 200, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 50, "g", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegan mayo").getId(), 2, "tbsp", 0, "Vegan mayo"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 1, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole grain bread").getId(), 4, "slices", 0, "Whole grain bread"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lettuce").getId(), 2, "leaves", 0, "Lettuce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 1, "pcs", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Falafel Wrap",
                "A delicious vegan falafel wrap filled with crispy falafel, hummus, and fresh vegetables. Perfect for a tasty lunch.",
                RecipeCategory.LUNCH,
                "1. Prepare falafel according to the recipe or package instructions.\n2. Warm the tortillas in a pan.\n3. Spread hummus on each tortilla and add falafel, diced tomatoes, cucumbers, and lettuce.\n4. Roll up the tortillas and serve.",
                30,
                4,
                450,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Falafel").getId(), 200, "g", 0, "Falafel"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tortillas").getId(), 4, "pcs", 0, "Tortillas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Hummus").getId(), 100, "g", 0, "Hummus"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 100, "g", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cucumbers").getId(), 100, "g", 0, "Cucumbers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lettuce").getId(), 4, "leaves", 0, "Lettuce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Stuffed Bell Peppers",
                "Colorful bell peppers stuffed with a flavorful mixture of quinoa, black beans, and corn. Perfect for a nutritious lunch.",
                RecipeCategory.LUNCH,
                "1. Preheat the oven to 180°C (350°F).\n2. Cook quinoa according to package instructions.\n3. In a bowl, combine cooked quinoa, black beans, corn, and diced tomatoes.\n4. Cut the tops off the bell peppers and remove the seeds.\n5. Stuff the peppers with the quinoa mixture and place them in a baking dish.\n6. Bake for 30 minutes and serve.",
                50,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 150, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Corn").getId(), 150, "g", 0, "Corn"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 200, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 4, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Lentil Tacos",
                "Flavorful vegan lentil tacos with a spicy lentil filling, fresh vegetables, and avocado. Perfect for a satisfying lunch.",
                RecipeCategory.LUNCH,
                "1. Cook lentils according to package instructions.\n2. In a pan, sauté onions and garlic in olive oil until softened.\n3. Add cooked lentils, taco seasoning, and a splash of water. Cook for 5 minutes.\n4. Warm the taco shells and fill them with the lentil mixture, diced tomatoes, shredded lettuce, and avocado slices.\n5. Serve with lime wedges.",
                30,
                4,
                450,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lentils").getId(), 200, "g", 0, "Lentils"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Taco seasoning").getId(), 2, "tbsp", 0, "Taco seasoning"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Taco shells").getId(), 8, "pcs", 0, "Taco shells"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 100, "g", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lettuce").getId(), 100, "g", 0, "Lettuce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime").getId(), 1, "pcs", 0, "Lime")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Quinoa and Kale Salad",
                "A nutritious quinoa and kale salad with cranberries, almonds, and a lemon tahini dressing. Perfect for a healthy lunch.",
                RecipeCategory.LUNCH,
                "1. Cook quinoa according to package instructions and let it cool.\n2. In a large bowl, combine cooked quinoa, chopped kale, dried cranberries, and sliced almonds.\n3. Dress with lemon tahini dressing and toss to combine.\n4. Serve chilled.",
                20,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Kale").getId(), 200, "g", 0, "Kale"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cranberries").getId(), 50, "g", 0, "Cranberries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almonds").getId(), 50, "g", 0, "Almonds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon").getId(), 1, "pcs", 0, "Lemon"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tahini").getId(), 3, "tbsp", 0, "Tahini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Sweet Potato and Black Bean Enchiladas",
                "Delicious vegan enchiladas filled with sweet potatoes and black beans, topped with enchilada sauce and avocado slices. Perfect for a hearty lunch.",
                RecipeCategory.LUNCH,
                "1. Preheat the oven to 180°C (350°F).\n2. Cook diced sweet potatoes until tender.\n3. In a bowl, combine cooked sweet potatoes, black beans, and spices.\n4. Fill tortillas with the sweet potato mixture and roll them up.\n5. Place the enchiladas in a baking dish, cover with enchilada sauce, and bake for 20 minutes.\n6. Serve with avocado slices and fresh cilantro.",
                40,
                4,
                450,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sweet potatoes").getId(), 200, "g", 0, "Sweet potatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 150, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tortillas").getId(), 8, "pcs", 0, "Tortillas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Enchilada sauce").getId(), 200, "ml", 0, "Enchilada sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 10, "g", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        // DINNER
        recipes.add(new RecipeRequestDTO(
                "Vegan Spaghetti Bolognese",
                "A hearty vegan spaghetti bolognese made with lentils and vegetables.",
                RecipeCategory.DINNER,
                "1. Cook spaghetti according to package instructions.\n2. In a pan, sauté onions and garlic in olive oil.\n3. Add carrots, celery, and lentils. Cook for 10 minutes.\n4. Add diced tomatoes, tomato paste, and vegetable broth. Simmer for 20 minutes.\n5. Serve the sauce over the cooked spaghetti.",
                40,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Spaghetti").getId(), 200, "g", 0, "Spaghetti"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 100, "g", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lentils").getId(), 200, "g", 0, "Lentils"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomato paste").getId(), 2, "tbsp", 0, "Tomato paste"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 1, "L", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oregano").getId(), 1, "tsp", 0, "Oregano"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Basil").getId(), 1, "tsp", 0, "Basil")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Tacos",
                "Tasty vegan tacos with black beans, avocado, and a variety of fresh toppings.",
                RecipeCategory.DINNER,
                "1. Warm the tortillas in a pan.\n2. In a bowl, mix black beans, corn, diced tomatoes, and spices.\n3. Fill each tortilla with the bean mixture, avocado slices, and your favorite toppings.\n4. Serve with lime wedges.",
                20,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tortillas").getId(), 8, "pcs", 0, "Tortillas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 200, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Corn").getId(), 150, "g", 0, "Corn"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 200, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 2, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime").getId(), 1, "pcs", 0, "Lime"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 1, "tsp", 0, "Chili powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Stir-fry",
                "A quick and easy vegan stir-fry with tofu, broccoli, and bell peppers.",
                RecipeCategory.DINNER,
                "1. In a wok, heat oil and add tofu cubes. Stir-fry until golden brown.\n2. Add broccoli, bell peppers, and carrots. Cook for 5 minutes.\n3. Add soy sauce, garlic, and ginger. Stir-fry for another 5 minutes.\n4. Serve with rice or noodles.",
                20,
                4,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tofu").getId(), 200, "g", 0, "Tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Broccoli").getId(), 150, "g", 0, "Broccoli"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 100, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Soy sauce").getId(), 3, "tbsp", 0, "Soy sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Ginger").getId(), 1, "tsp", 0, "Ginger"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sesame seeds").getId(), 1, "tbsp", 0, "Sesame seeds")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Shepherd's Pie",
                "A comforting vegan shepherd's pie with lentils, vegetables, and a mashed potato topping.",
                RecipeCategory.DINNER,
                "1. Preheat the oven to 200°C (400°F).\n2. In a pan, cook lentils, onions, carrots, and peas until tender.\n3. Add vegetable broth and tomato paste. Simmer for 10 minutes.\n4. Transfer to a baking dish and top with mashed potatoes.\n5. Bake for 20 minutes until golden brown.",
                50,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lentils").getId(), 200, "g", 0, "Lentils"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green peas").getId(), 150, "g", 0, "Green peas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomato paste").getId(), 2, "tbsp", 0, "Tomato paste"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Potatoes").getId(), 500, "g", 0, "Potatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Thyme").getId(), 1, "tsp", 0, "Thyme"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Pad Thai",
                "A delicious vegan pad Thai with rice noodles, tofu, and a flavorful sauce.",
                RecipeCategory.DINNER,
                "1. Cook rice noodles according to package instructions.\n2. In a pan, sauté tofu cubes until golden brown.\n3. Add bell peppers, carrots, and green onions. Cook for 5 minutes.\n4. Add the cooked noodles and pad Thai sauce. Stir well.\n5. Serve with chopped peanuts and lime wedges.",
                30,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice noodles").getId(), 200, "g", 0, "Rice noodles"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tofu").getId(), 200, "g", 0, "Tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 100, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green onions").getId(), 50, "g", 0, "Green onions"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Pad Thai sauce").getId(), 4, "tbsp", 0, "Pad Thai sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Peanuts").getId(), 50, "g", 0, "Peanuts"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime").getId(), 1, "pcs", 0, "Lime"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Stuffed Portobello Mushrooms",
                "Hearty stuffed portobello mushrooms filled with quinoa, spinach, and tomatoes.",
                RecipeCategory.DINNER,
                "1. Preheat the oven to 200°C (400°F).\n2. Cook quinoa according to package instructions.\n3. In a pan, sauté spinach and tomatoes until wilted.\n4. Mix the quinoa with the spinach and tomatoes.\n5. Stuff the portobello mushrooms with the quinoa mixture and bake for 20 minutes.",
                40,
                4,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Portobello mushrooms").getId(), 4, "pcs", 0, "Portobello mushrooms"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Spinach").getId(), 150, "g", 0, "Spinach"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 100, "g", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Cauliflower Curry",
                "A flavorful vegan curry with cauliflower, chickpeas, and coconut milk.",
                RecipeCategory.DINNER,
                "1. In a pot, heat oil and sauté onions and garlic.\n2. Add cauliflower florets, chickpeas, and curry powder. Cook for 10 minutes.\n3. Add coconut milk and simmer for 15 minutes.\n4. Serve with rice.",
                30,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cauliflower").getId(), 1, "head", 0, "Cauliflower"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 200, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 400, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Curry powder").getId(), 2, "tbsp", 0, "Curry powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Ginger").getId(), 1, "tsp", 0, "Ginger"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan BBQ Jackfruit Sandwiches",
                "Delicious vegan BBQ jackfruit sandwiches with coleslaw.",
                RecipeCategory.DINNER,
                "1. In a pan, sauté onions and garlic.\n2. Add jackfruit and BBQ sauce. Cook for 10 minutes.\n3. Serve the BBQ jackfruit on buns with coleslaw.",
                20,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Jackfruit").getId(), 400, "g", 0, "Jackfruit"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("BBQ sauce").getId(), 200, "ml", 0, "BBQ sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Buns").getId(), 4, "pcs", 0, "Buns"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coleslaw").getId(), 200, "g", 0, "Coleslaw"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Mushroom Risotto",
                "A creamy vegan mushroom risotto with arborio rice and vegetable broth.",
                RecipeCategory.DINNER,
                "1. In a pot, heat olive oil and sauté onions and garlic.\n2. Add arborio rice and cook for 2 minutes.\n3. Gradually add vegetable broth, stirring constantly until the rice is cooked.\n4. In a separate pan, cook mushrooms until tender.\n5. Mix the mushrooms into the risotto and serve.",
                40,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice").getId(), 200, "g", 0, "Rice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mushrooms").getId(), 200, "g", 0, "Mushrooms"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 1, "L", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Thyme").getId(), 1, "tsp", 0, "Thyme"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Pizza",
                "A delicious vegan pizza with a variety of fresh vegetables and vegan cheese.",
                RecipeCategory.DINNER,
                "1. Preheat the oven to 220°C (425°F).\n2. Roll out the pizza dough and spread with tomato sauce.\n3. Top with your favorite vegetables and vegan cheese.\n4. Bake for 15-20 minutes until the crust is golden brown.",
                30,
                4,
                450,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Pizza dough").getId(), 1, "pcs", 0, "Pizza dough"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomato sauce").getId(), 200, "g", 0, "Tomato sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 100, "g", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mushrooms").getId(), 100, "g", 0, "Mushrooms"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olives").getId(), 50, "g", 0, "Olives"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegan cheese").getId(), 100, "g", 0, "Vegan cheese"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Basil").getId(), 1, "tbsp", 0, "Basil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oregano").getId(), 1, "tbsp", 0, "Oregano")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Roasted Brussels Sprouts",
                "Crispy and delicious roasted Brussels sprouts with a hint of garlic.",
                RecipeCategory.SIDE_DISHES,
                "1. Preheat oven to 200°C (400°F).\n2. Toss Brussels sprouts with olive oil, garlic, salt, and pepper.\n3. Spread on a baking sheet and roast for 20-25 minutes until crispy.",
                30,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Brussels sprouts").getId(), 400, "g", 0, "Brussels sprouts"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Garlic Mashed Potatoes",
                "Creamy mashed potatoes with a rich garlic flavor.",
                RecipeCategory.SIDE_DISHES,
                "1. Boil potatoes until tender.\n2. In a pan, sauté garlic in olive oil until fragrant.\n3. Mash the potatoes with garlic, almond milk, salt, and pepper until smooth.",
                25,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Potatoes").getId(), 500, "g", 0, "Potatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 4, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 100, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rosemary").getId(), 1, "tsp", 0, "Rosemary")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Grilled Asparagus",
                "Tender grilled asparagus with a squeeze of lemon.",
                RecipeCategory.SIDE_DISHES,
                "1. Toss asparagus with olive oil, salt, and pepper.\n2. Grill on medium-high heat for 5-7 minutes.\n3. Squeeze lemon juice over the top and serve.",
                15,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Asparagus").getId(), 400, "g", 0, "Asparagus"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 1, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon").getId(), 1, "pcs", 0, "Lemon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Balsamic Glazed Carrots",
                "Sweet and tangy balsamic glazed carrots, perfect for a side dish.",
                RecipeCategory.SIDE_DISHES,
                "1. Preheat oven to 200°C (400°F).\n2. Toss carrots with olive oil, balsamic vinegar, and maple syrup.\n3. Roast for 25-30 minutes, stirring occasionally.",
                35,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 500, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Balsamic vinegar").getId(), 2, "tbsp", 0, "Balsamic vinegar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 1, "tbsp", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Thyme").getId(), 1, "tsp", 0, "Thyme")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Stuffed Mushrooms",
                "Savory stuffed mushrooms filled with a flavorful mixture of herbs and breadcrumbs.",
                RecipeCategory.SIDE_DISHES,
                "1. Preheat oven to 180°C (350°F).\n2. Remove stems from mushrooms and chop them finely.\n3. Sauté chopped stems with garlic and onion in olive oil.\n4. Mix with breadcrumbs, herbs, salt, and pepper.\n5. Stuff the mushroom caps and bake for 20 minutes.",
                40,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mushrooms").getId(), 400, "g", 0, "Mushrooms"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Breadcrumbs").getId(), 100, "g", 0, "Breadcrumbs"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Parsley").getId(), 2, "tbsp", 0, "Parsley"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Fenugreek").getId(), 2, "tbsp", 0, "Fenugreek"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mint").getId(), 2, "tbsp", 0, "Mint"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Parsley").getId(), 1, "tbsp", 0, "Parsley")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Coleslaw",
                "A refreshing vegan coleslaw with a creamy dressing.",
                RecipeCategory.SIDE_DISHES,
                "1. Shred cabbage and carrots.\n2. In a bowl, whisk together vegan mayo, apple cider vinegar, maple syrup, salt, and pepper.\n3. Toss the vegetables with the dressing and refrigerate for 30 minutes before serving.",
                40,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cabbage").getId(), 300, "g", 0, "Cabbage"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 100, "g", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegan mayo").getId(), 100, "g", 0, "Vegan mayo"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Apple cider vinegar").getId(), 2, "tbsp", 0, "Apple cider vinegar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 1, "tbsp", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dill").getId(), 1, "tsp", 0, "Dill")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Garlic Green Beans",
                "Tender green beans sautéed with garlic and olive oil.",
                RecipeCategory.SIDE_DISHES,
                "1. Blanch green beans in boiling water for 2 minutes.\n2. In a pan, sauté garlic in olive oil until fragrant.\n3. Add green beans, salt, and pepper, and cook for another 5 minutes.",
                15,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green beans").getId(), 400, "g", 0, "Green beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 3, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili flakes").getId(), 1, "tsp", 0, "Chili flakes")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Roasted Sweet Potato Wedges",
                "Crispy roasted sweet potato wedges with a touch of cinnamon.",
                RecipeCategory.SIDE_DISHES,
                "1. Preheat oven to 200°C (400°F).\n2. Toss sweet potato wedges with olive oil, cinnamon, salt, and pepper.\n3. Spread on a baking sheet and roast for 25-30 minutes until crispy.",
                35,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sweet potatoes").getId(), 500, "g", 0, "Sweet potatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Quinoa Salad",
                "A light and nutritious quinoa salad with fresh vegetables and a lemon vinaigrette.",
                RecipeCategory.SIDE_DISHES,
                "1. Cook quinoa according to package instructions.\n2. In a large bowl, combine cooked quinoa, diced cucumbers, cherry tomatoes, red onion, and fresh herbs.\n3. In a small bowl, whisk together lemon juice, olive oil, salt, and pepper.\n4. Pour the dressing over the salad and toss to combine.",
                20,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cucumbers").getId(), 100, "g", 0, "Cucumbers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cherry tomatoes").getId(), 100, "g", 0, "Cherry tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Red onion").getId(), 50, "g", 0, "Red onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Parsley").getId(), 2, "tbsp", 0, "Parsley"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mint").getId(), 2, "tbsp", 0, "Mint"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 1, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Spicy Roasted Cauliflower",
                "Roasted cauliflower florets with a spicy kick.",
                RecipeCategory.SIDE_DISHES,
                "1. Preheat oven to 200°C (400°F).\n2. Toss cauliflower florets with olive oil, chili powder, cumin, salt, and pepper.\n3. Spread on a baking sheet and roast for 25-30 minutes until tender and slightly crispy.",
                35,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cauliflower").getId(), 500, "g", 0, "Cauliflower"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 1, "tsp", 0, "Chili powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

       // ONE_POT_MEALS

        recipes.add(new RecipeRequestDTO(
                "Vegan Lentil Stew",
                "A hearty and nutritious lentil stew with vegetables.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a large pot over medium heat.\n2. Add diced onion, carrots, and celery, and sauté until softened.\n3. Add lentils, vegetable broth, diced tomatoes, and spices.\n4. Simmer for 30-40 minutes until lentils are tender.",
                45,
                4,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lentils").getId(), 200, "g", 0, "Lentils"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 2, "pcs", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 2, "pcs", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bay leaves").getId(), 1, "pcs", 0, "Bay leaves"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Thyme").getId(), 1, "tsp", 0, "Thyme")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Chickpea Curry",
                "A flavorful chickpea curry cooked in one pot.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a pot and sauté diced onion and garlic.\n2. Add chickpeas, coconut milk, diced tomatoes, and spices.\n3. Simmer for 20-25 minutes until thickened.\n4. Serve over rice or with naan.",
                30,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 200, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 3, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 400, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Curry powder").getId(), 2, "tbsp", 0, "Curry powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Mushroom Stroganoff",
                "A creamy vegan mushroom stroganoff cooked in one pot.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a pot and sauté sliced mushrooms and diced onion.\n2. Add vegetable broth, coconut milk, and spices.\n3. Simmer for 15-20 minutes until thickened.\n4. Serve over pasta or rice.",
                30,
                4,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole wheat pasta").getId(), 200, "g", 0, "Whole wheat pasta"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Mushrooms").getId(), 300, "g", 0, "Mushrooms"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 200, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Soy sauce").getId(), 1, "tbsp", 0, "Soy sauce"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Ratatouille",
                "A classic French vegetable stew made in one pot.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a large pot and sauté diced onion and garlic.\n2. Add diced eggplant, zucchini, bell peppers, and tomatoes.\n3. Season with herbs and spices.\n4. Simmer for 25-30 minutes until vegetables are tender.",
                40,
                4,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 3, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Eggplant").getId(), 1, "pcs", 0, "Eggplant"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Zucchini").getId(), 2, "pcs", 0, "Zucchini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 2, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bay leaves").getId(), 2, "pcs", 0, "Bay leaves"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rosemary").getId(), 2, "tsp", 0, "Rosemary"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Thyme").getId(), 2, "tsp", 0, "Thyme"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sage").getId(), 2, "tsp", 0, "Sage"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Marjoram").getId(), 2, "tsp", 0, "Marjoram")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Quinoa Chili",
                "A hearty and spicy vegan chili with quinoa.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a pot and sauté diced onion, garlic, and bell peppers.\n2. Add quinoa, beans, diced tomatoes, vegetable broth, and spices.\n3. Simmer for 25-30 minutes until quinoa is cooked and chili is thickened.",
                40,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 3, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 2, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Quinoa").getId(), 200, "g", 0, "Quinoa"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 400, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 2, "tsp", 0, "Chili powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Thai Green Curry",
                "A fragrant and spicy vegan Thai green curry.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat coconut milk in a pot and add green curry paste.\n2. Add diced tofu, bell peppers, green beans, and zucchini.\n3. Simmer for 15-20 minutes until vegetables are tender.\n4. Serve with rice.",
                30,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 400, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green curry paste").getId(), 2, "tbsp", 0, "Green curry paste"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tofu").getId(), 200, "g", 0, "Tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 2, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green beans").getId(), 100, "g", 0, "Green beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Zucchini").getId(), 1, "pcs", 0, "Zucchini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemongrass").getId(), 1, "tbsp", 0, "Lemongrass")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Minestrone Soup",
                "A classic Italian vegetable soup made in one pot.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a large pot and sauté diced onion, garlic, carrots, and celery.\n2. Add diced tomatoes, vegetable broth, beans, pasta, and spices.\n3. Simmer for 20-25 minutes until vegetables and pasta are cooked.",
                35,
                4,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 3, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 2, "pcs", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 2, "pcs", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cannellini beans").getId(), 400, "g", 0, "Cannellini beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole wheat pasta").getId(), 100, "g", 0, "Whole wheat pasta"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Basil").getId(), 1, "tbsp", 0, "Basil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oregano").getId(), 1, "tsp", 0, "Oregano")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Jambalaya",
                "A spicy and flavorful vegan jambalaya.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat olive oil in a large pot and sauté diced onion, bell peppers, and celery.\n2. Add rice, beans, diced tomatoes, vegetable broth, and spices.\n3. Simmer for 25-30 minutes until rice is cooked and jambalaya is thickened.",
                40,
                4,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 2, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Celery").getId(), 2, "pcs", 0, "Celery"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice").getId(), 200, "g", 0, "Rice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black beans").getId(), 400, "g", 0, "Black beans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili flakes").getId(), 1, "tsp", 0, "Chili flakes")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan One-Pot Pasta",
                "A quick and easy vegan pasta dish cooked in one pot.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Combine pasta, diced tomatoes, vegetable broth, and spices in a pot.\n2. Bring to a boil and cook for 10-12 minutes until pasta is al dente.\n3. Stir in spinach and serve immediately.",
                20,
                4,
                300,


                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Whole wheat pasta").getId(), 200, "g", 0, "Whole wheat pasta"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Diced tomatoes").getId(), 400, "g", 0, "Diced tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable broth").getId(), 500, "ml", 0, "Vegetable broth"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Spinach").getId(), 100, "g", 0, "Spinach"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 1, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Basil").getId(), 1, "tbsp", 0, "Basil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Oregano").getId(), 1, "tsp", 0, "Oregano")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Coconut Rice with Vegetables",
                "A fragrant and delicious coconut rice dish with vegetables.",
                RecipeCategory.ONE_POT_MEALS,
                "1. Heat coconut milk in a pot and add rice, diced bell peppers, peas, and spices.\n2. Simmer for 20-25 minutes until rice is cooked and creamy.\n3. Serve immediately.",
                30,
                4,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 400, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice").getId(), 200, "g", 0, "Rice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bell peppers").getId(), 2, "pcs", 0, "Bell peppers"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Green peas").getId(), 100, "g", 0, "Green peas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Turmeric").getId(), 1, "tsp", 0, "Turmeric"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Ginger").getId(), 1, "tsp", 0, "Ginger")
                )
        ));

        // SNACKS

        recipes.add(new RecipeRequestDTO(
                "Vegan Energy Balls",
                "Delicious and nutritious energy balls made with almonds and dates.",
                RecipeCategory.SNACKS,
                "1. Blend dates, almonds, and cocoa powder in a food processor until smooth.\n2. Roll the mixture into small balls and refrigerate for 1 hour before serving.",
                10,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dates").getId(), 200, "g", 0, "Dates"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almonds").getId(), 100, "g", 0, "Almonds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cocoa powder").getId(), 2, "tbsp", 0, "Cocoa powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Hummus",
                "A creamy and delicious homemade hummus.",
                RecipeCategory.SNACKS,
                "1. Blend chickpeas, tahini, lemon juice, garlic, and olive oil in a food processor until smooth.\n2. Serve with fresh vegetables or pita bread.",
                10,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 400, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tahini").getId(), 2, "tbsp", 0, "Tahini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 2, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Kale Chips",
                "Crispy and healthy kale chips.",
                RecipeCategory.SNACKS,
                "1. Tear kale leaves into bite-sized pieces and toss with olive oil and salt.\n2. Bake at 180°C for 10-15 minutes until crispy.",
                20,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Kale").getId(), 200, "g", 0, "Kale"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 1, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic powder").getId(), 1, "tsp", 0, "Garlic powder")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Guacamole",
                "A fresh and flavorful guacamole dip.",
                RecipeCategory.SNACKS,
                "1. Mash avocados with a fork.\n2. Add diced onion, tomatoes, cilantro, lime juice, salt, and pepper. Mix well.\n3. Serve with tortilla chips or fresh vegetables.",
                10,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 2, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 2, "pcs", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime juice").getId(), 1, "tbsp", 0, "Lime juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 1, "tsp", 0, "Chili powder")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Stuffed Dates",
                "Sweet and satisfying stuffed dates with almond butter and almonds.",
                RecipeCategory.SNACKS,
                "1. Slice dates open and remove pits.\n2. Fill each date with almond butter and top with a whole almond.\n3. Serve immediately.",
                5,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dates").getId(), 200, "g", 0, "Dates"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond butter").getId(), 100, "g", 0, "Almond butter"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almonds").getId(), 50, "g", 0, "Almonds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Fruit Smoothie",
                "A refreshing and healthy fruit smoothie.",
                RecipeCategory.SNACKS,
                "1. Blend frozen berries, banana, and almond milk until smooth.\n2. Pour into glasses and serve immediately.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Frozen berries").getId(), 100, "g", 0, "Frozen berries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 100, "g", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 200, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chia seeds").getId(), 1, "tbsp", 0, "Chia seeds")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Rice Cakes with Avocado",
                "Simple and delicious rice cakes topped with avocado and spices.",
                RecipeCategory.SNACKS,
                "1. Slice avocado and place on rice cakes.\n2. Sprinkle with salt, pepper, and red pepper flakes.\n3. Serve immediately.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice cakes").getId(), 4, "pcs", 0, "Rice cakes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili flakes").getId(), 1, "tsp", 0, "Chili flakes")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Carrot Sticks with Hummus",
                "Crunchy carrot sticks served with homemade hummus.",
                RecipeCategory.SNACKS,
                "1. Slice carrots into sticks.\n2. Serve with homemade hummus.",
                5,
                2,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 4, "pcs", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Hummus").getId(), 200, "g", 0, "Hummus"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Apple Slices with Peanut Butter",
                "Fresh apple slices served with creamy peanut butter.",
                RecipeCategory.SNACKS,
                "1. Slice apples and serve with peanut butter.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Apples").getId(), 2, "pcs", 0, "Apples"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Peanut butter").getId(), 100, "g", 0, "Peanut butter"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Popcorn",
                "A classic snack of air-popped popcorn with nutritional yeast.",
                RecipeCategory.SNACKS,
                "1. Air-pop the popcorn kernels.\n2. Sprinkle with nutritional yeast and salt.\n3. Serve immediately.",
                5,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Popcorn kernels").getId(), 100, "g", 0, "Popcorn kernels"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Nutritional yeast").getId(), 2, "tbsp", 0, "Nutritional yeast"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt")
                )
        ));

        // DESSERTS
        recipes.add(new RecipeRequestDTO(
                "Vegan Energy Balls",
                "Delicious and nutritious energy balls made with almonds and dates.",
                RecipeCategory.SNACKS,
                "1. Blend dates, almonds, and cocoa powder in a food processor until smooth.\n2. Roll the mixture into small balls and refrigerate for 1 hour before serving.",
                10,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dates").getId(), 200, "g", 0, "Dates"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almonds").getId(), 100, "g", 0, "Almonds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cocoa powder").getId(), 2, "tbsp", 0, "Cocoa powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Hummus",
                "A creamy and delicious homemade hummus.",
                RecipeCategory.SNACKS,
                "1. Blend chickpeas, tahini, lemon juice, garlic, and olive oil in a food processor until smooth.\n2. Serve with fresh vegetables or pita bread.",
                10,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chickpeas").getId(), 400, "g", 0, "Chickpeas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tahini").getId(), 2, "tbsp", 0, "Tahini"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 2, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic").getId(), 2, "cloves", 0, "Garlic"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 2, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Kale Chips",
                "Crispy and healthy kale chips.",
                RecipeCategory.SNACKS,
                "1. Tear kale leaves into bite-sized pieces and toss with olive oil and salt.\n2. Bake at 180°C for 10-15 minutes until crispy.",
                20,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Kale").getId(), 200, "g", 0, "Kale"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Olive oil").getId(), 1, "tbsp", 0, "Olive oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Garlic powder").getId(), 1, "tsp", 0, "Garlic powder")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Guacamole",
                "A fresh and flavorful guacamole dip.",
                RecipeCategory.SNACKS,
                "1. Mash avocados with a fork.\n2. Add diced onion, tomatoes, cilantro, lime juice, salt, and pepper. Mix well.\n3. Serve with tortilla chips or fresh vegetables.",
                10,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 2, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Onion").getId(), 1, "pcs", 0, "Onion"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Tomatoes").getId(), 2, "pcs", 0, "Tomatoes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cilantro").getId(), 2, "tbsp", 0, "Cilantro"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lime juice").getId(), 1, "tbsp", 0, "Lime juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili powder").getId(), 1, "tsp", 0, "Chili powder")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Stuffed Dates",
                "Sweet and satisfying stuffed dates with almond butter and almonds.",
                RecipeCategory.SNACKS,
                "1. Slice dates open and remove pits.\n2. Fill each date with almond butter and top with a whole almond.\n3. Serve immediately.",
                5,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dates").getId(), 200, "g", 0, "Dates"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond butter").getId(), 100, "g", 0, "Almond butter"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almonds").getId(), 50, "g", 0, "Almonds"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Fruit Smoothie",
                "A refreshing and healthy fruit smoothie.",
                RecipeCategory.SNACKS,
                "1. Blend frozen berries, banana, and almond milk until smooth.\n2. Pour into glasses and serve immediately.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Frozen berries").getId(), 100, "g", 0, "Frozen berries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 100, "g", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 200, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chia seeds").getId(), 1, "tbsp", 0, "Chia seeds")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Rice Cakes with Avocado",
                "Simple and delicious rice cakes topped with avocado and spices.",
                RecipeCategory.SNACKS,
                "1. Slice avocado and place on rice cakes.\n2. Sprinkle with salt, pepper, and red pepper flakes.\n3. Serve immediately.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Rice cakes").getId(), 4, "pcs", 0, "Rice cakes"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Avocado").getId(), 1, "pcs", 0, "Avocado"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Black pepper").getId(), 1, "tsp", 0, "Black pepper"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chili flakes").getId(), 1, "tsp", 0, "Chili flakes")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Carrot Sticks with Hummus",
                "Crunchy carrot sticks served with homemade hummus.",
                RecipeCategory.SNACKS,
                "1. Slice carrots into sticks.\n2. Serve with homemade hummus.",
                5,
                2,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Carrots").getId(), 4, "pcs", 0, "Carrots"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Hummus").getId(), 200, "g", 0, "Hummus"),


                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Paprika").getId(), 1, "tsp", 0, "Paprika"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cumin").getId(), 1, "tsp", 0, "Cumin")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Apple Slices with Peanut Butter",
                "Fresh apple slices served with creamy peanut butter.",
                RecipeCategory.SNACKS,
                "1. Slice apples and serve with peanut butter.",
                5,
                2,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Apples").getId(), 2, "pcs", 0, "Apples"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Peanut butter").getId(), 100, "g", 0, "Peanut butter"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Popcorn",
                "A classic snack of air-popped popcorn with nutritional yeast.",
                RecipeCategory.SNACKS,
                "1. Air-pop the popcorn kernels.\n2. Sprinkle with nutritional yeast and salt.\n3. Serve immediately.",
                5,
                4,
                100,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Popcorn kernels").getId(), 100, "g", 0, "Popcorn kernels"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Nutritional yeast").getId(), 2, "tbsp", 0, "Nutritional yeast"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Chocolate Cake",
                "A rich and moist vegan chocolate cake perfect for any occasion.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, cocoa powder, baking powder, and salt.\n3. In another bowl, mix almond milk, vegetable oil, apple cider vinegar, and vanilla extract.\n4. Combine the wet and dry ingredients and mix until smooth.\n5. Pour the batter into a greased cake pan and bake for 30-35 minutes.\n6. Let cool before serving.",
                45,
                8,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cocoa powder").getId(), 50, "g", 0, "Cocoa powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 240, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sunflower oil").getId(), 80, "ml", 0, "Sunflower oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Apple cider vinegar").getId(), 1, "tbsp", 0, "Apple cider vinegar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Banana Bread",
                "A moist and delicious vegan banana bread perfect for breakfast or a snack.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 175°C (350°F).\n2. In a bowl, mash the ripe bananas until smooth.\n3. Add almond milk, vegetable oil, vanilla extract, and sugars. Mix well.\n4. In another bowl, mix flour, baking powder, and salt.\n5. Combine the wet and dry ingredients and mix until smooth.\n6. Pour the batter into a greased loaf pan and bake for 50-60 minutes.\n7. Let cool before serving.",
                70,
                8,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Bananas").getId(), 300, "g", 0, "Bananas"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 100, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sunflower oil").getId(), 80, "ml", 0, "Sunflower oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 150, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cinnamon").getId(), 1, "tsp", 0, "Cinnamon")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Chocolate Chip Cookies",
                "Delicious and chewy vegan chocolate chip cookies.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, baking powder, and salt.\n3. In another bowl, mix almond milk, vegetable oil, vanilla extract, and sugars.\n4. Combine the wet and dry ingredients and mix until smooth.\n5. Fold in the chocolate chips.\n6. Drop spoonfuls of dough onto a baking sheet and bake for 10-12 minutes.\n7. Let cool before serving.",
                30,
                6,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 60, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sunflower oil").getId(), 80, "ml", 0, "Sunflower oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 150, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chocolate chips").getId(), 100, "g", 0, "Chocolate chips")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Apple Pie",
                "A classic vegan apple pie with a flaky crust and sweet apple filling.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, salt, and sugars. Add vegetable shortening and mix until crumbly.\n3. Add water and mix until dough forms. Divide into two balls and roll out.\n4. In another bowl, mix sliced apples, sugars, cinnamon, and lemon juice.\n5. Place one crust in a pie pan, add the apple mixture, and cover with the second crust. Cut slits in the top.\n6. Bake for 50-60 minutes.\n7. Let cool before serving.",
                90,
                8,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 300, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
        new RecipeIngredientRequestDTO(
                ingredientService.getIngredientByName("Sugar").getId(), 50, "g", 0, "Sugar"),
                new RecipeIngredientRequestDTO(
                        ingredientService.getIngredientByName("Vegetable shortening").getId(), 150, "g", 0, "Vegetable shortening"),
                new RecipeIngredientRequestDTO(
                        ingredientService.getIngredientByName("Water").getId(), 60, "ml", 0, "Water"),
                new RecipeIngredientRequestDTO(
                        ingredientService.getIngredientByName("Apples").getId(), 6, "pcs", 0, "Apples"),
                new RecipeIngredientRequestDTO(
                        ingredientService.getIngredientByName("Cinnamon").getId(), 2, "tsp", 0, "Cinnamon"),
                new RecipeIngredientRequestDTO(
                        ingredientService.getIngredientByName("Lemon juice").getId(), 1, "tbsp", 0, "Lemon juice")
        )
));

        recipes.add(new RecipeRequestDTO(
                "Vegan Brownies",
                "Rich and fudgy vegan brownies made with cocoa and chocolate chips.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, cocoa powder, baking powder, and salt.\n3. In another bowl, mix almond milk, vegetable oil, vanilla extract, and sugars.\n4. Combine the wet and dry ingredients and mix until smooth.\n5. Fold in the chocolate chips.\n6. Pour the batter into a greased baking dish and bake for 25-30 minutes.\n7. Let cool before serving.",
                40,
                8,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cocoa powder").getId(), 50, "g", 0, "Cocoa powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Baking powder").getId(), 1, "tsp", 0, "Baking powder"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Almond milk").getId(), 240, "ml", 0, "Almond milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sunflower oil").getId(), 80, "ml", 0, "Sunflower oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 150, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Chocolate chips").getId(), 100, "g", 0, "Chocolate chips")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Lemon Bars",
                "Tangy and sweet vegan lemon bars with a buttery crust.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, powdered sugars, and salt. Add vegetable shortening and mix until crumbly.\n3. Press the mixture into a baking dish and bake for 15 minutes.\n4. In another bowl, mix lemon juice, lemon zest, sugars, and cornstarch.\n5. Pour the lemon mixture over the crust and bake for another 20-25 minutes.\n6. Let cool before cutting into bars.",
                45,
                8,
                250,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 200, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Powdered sugar").getId(), 50, "g", 0, "Powdered sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable shortening").getId(), 100, "g", 0, "Vegetable shortening"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 120, "ml", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon zest").getId(), 2, "tsp", 0, "Lemon zest"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 200, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Cornstarch").getId(), 2, "tbsp", 0, "Cornstarch")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Pumpkin Pie",
                "A creamy and spiced vegan pumpkin pie perfect for fall.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, salt, and sugars. Add vegetable shortening and mix until crumbly.\n3. Add water and mix until dough forms. Roll out and place in a pie pan.\n4. In another bowl, mix pumpkin puree, coconut milk, sugars, and spices.\n5. Pour the pumpkin mixture into the crust and bake for 50-60 minutes.\n6. Let cool before serving.",
                90,
                8,
                300,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 300, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 50, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable shortening").getId(), 150, "g", 0, "Vegetable shortening"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Water").getId(), 60, "ml", 0, "Water"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Pumpkin puree").getId(), 425, "g", 0, "Pumpkin puree"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 240, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Pumpkin spice mix").getId(), 2, "tsp", 0, "Pumpkin spice mix")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Cheesecake",
                "A creamy and tangy vegan cheesecake with a vegan digestive biscuits crust.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 160°C (320°F).\n2. In a bowl, mix digestive biscuits crumbs, sugars, and melted coconut oil. Press into a springform pan.\n3. In another bowl, blend vegan cream cheese, coconut milk, lemon juice, sugars, and vanilla extract until smooth.\n4. Pour the filling over the crust and bake for 50-60 minutes.\n5. Let cool and refrigerate for at least 4 hours before serving.",
                70,
                8,
                350,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegan digestive biscuits").getId(), 200, "g", 0, "Vegan digestive biscuits"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 50, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut oil").getId(), 80, "ml", 0, "Coconut oil"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegan cheese").getId(), 600, "g", 0, "Vegan cheese"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 240, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Lemon juice").getId(), 2, "tbsp", 0, "Lemon juice"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Pecan Pie",
                "A rich and sweet vegan pecan pie with a flaky crust.",
                RecipeCategory.DESSERTS,
                "1. Preheat oven to 180°C (350°F).\n2. In a bowl, mix flour, salt, and sugars. Add vegetable shortening and mix until crumbly.\n3. Add water and mix until dough forms. Roll out and place in a pie pan.\n4. In another bowl, mix pecans, maple syrup, coconut milk, brown sugars, and vanilla extract.\n5. Pour the pecan mixture into the crust and bake for 50-60 minutes.\n6. Let cool before serving.",
                90,
                8,
                400,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Flour").getId(), 300, "g", 0, "Flour"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Salt").getId(), 1, "tsp", 0, "Salt"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 50, "g", 0, "Sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vegetable shortening").getId(), 150, "g", 0, "Vegetable shortening"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Water").getId(), 60, "ml", 0, "Water"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Pecans").getId(), 200, "g", 0, "Pecans"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Maple syrup").getId(), 240, "ml", 0, "Maple syrup"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Coconut milk").getId(), 240, "ml", 0, "Coconut milk"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Brown sugar").getId(), 100, "g", 0, "Brown sugar"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Chocolate Mousse",
                "A light and creamy vegan chocolate mousse.",
                RecipeCategory.DESSERTS,
                "1. Melt the dark chocolate in a double boiler.\n2. In a bowl, blend the silken tofu, melted chocolate, and vanilla extract until smooth.\n3. Refrigerate for at least 2 hours before serving.",
                10,
                4,
                200,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Dark chocolate").getId(), 200, "g", 0, "Dark chocolate"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Silken tofu").getId(), 400, "g", 0, "Silken tofu"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Vanilla extract").getId(), 1, "tsp", 0, "Vanilla extract")
                )
        ));

        recipes.add(new RecipeRequestDTO(
                "Vegan Raspberry Sorbet",
                "A refreshing and sweet vegan raspberry sorbet.",
                RecipeCategory.DESSERTS,
                "1. Blend raspberries, water, and sugars until smooth.\n2. Pour the mixture into an ice cream maker and churn according to the manufacturer's instructions.\n3. Freeze for at least 2 hours before serving.",
                15,
                4,
                150,
                Arrays.asList(
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Raspberries").getId(), 500, "g", 0, "Raspberries"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Water").getId(), 240, "ml", 0, "Water"),
                        new RecipeIngredientRequestDTO(
                                ingredientService.getIngredientByName("Sugar").getId(), 150, "g", 0, "Sugar")
                )
        ));

        for (RecipeRequestDTO recipe : recipes) {
            try {
                Recipe savedRecipe = recipeService.saveRecipeWithoutIngredients(recipe);

                List<RecipeIngredientRequestDTO> updatedIngredients = recipe.ingredients().stream()
                        .map(ingredient -> new RecipeIngredientRequestDTO(
                                ingredient.ingredientId(),
                                ingredient.quantity(),
                                ingredient.measurementUnit(),
                                savedRecipe.getId(),
                                ingredient.ingredientName()

                        ))
                        .collect(Collectors.toList());

                recipeService.saveRecipeIngredients(updatedIngredients);
            } catch (Exception e) {
                System.err.println("Error saving recipe: " + recipe.recipeName() + ". " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Recipes populated on DB successfully.");
    }

    private Ingredient findIngredientByPartialName(String partialName) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByNameContaining(partialName);
        if (ingredients.isEmpty()) {
            throw new NotFoundException("No ingredient found containing: " + partialName);
        }
        return ingredients.get(0);
    }
}
