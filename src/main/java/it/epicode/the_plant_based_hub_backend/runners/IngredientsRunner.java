package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class IngredientsRunner implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {
        populateIngredients();
    }

    private void populateIngredients() {
        Ingredient[] ingredients = {

                // Proteins
                new Ingredient("Tofu", IngredientCategory.PROTEINS, 76, "100g"),
                new Ingredient("Tempeh", IngredientCategory.PROTEINS, 192, "100g"),
                new Ingredient("Seitan", IngredientCategory.PROTEINS, 370, "100g"),
                new Ingredient("Lentils", IngredientCategory.PROTEINS, 116, "100g"),
                new Ingredient("Edamame", IngredientCategory.PROTEINS, 121, "100g"),
                new Ingredient("Peanuts", IngredientCategory.PROTEINS, 567, "100g"),
                new Ingredient("Black beans", IngredientCategory.PROTEINS, 132, "100g"),
                new Ingredient("Green peas", IngredientCategory.PROTEINS, 81, "100g"),
                new Ingredient("Hummus", IngredientCategory.PROTEINS, 89, "100g"),
                new Ingredient("Falafel", IngredientCategory.PROTEINS, 333, "100g"),
                new Ingredient("Silken tofu", IngredientCategory.PROTEINS, 55, "100g"),


                // Legumes
                new Ingredient("Kidney beans", IngredientCategory.LEGUMES, 127, "100g"),
                new Ingredient("Navy beans", IngredientCategory.LEGUMES, 140, "100g"),
                new Ingredient("Pinto beans", IngredientCategory.LEGUMES, 143, "100g"),
                new Ingredient("Lima beans", IngredientCategory.LEGUMES, 113, "100g"),
                new Ingredient("Cannellini beans", IngredientCategory.LEGUMES, 71, "100g"),
                new Ingredient("Soybeans", IngredientCategory.LEGUMES, 173, "100g"),
                new Ingredient("Black-eyed peas", IngredientCategory.LEGUMES, 89, "100g"),
                new Ingredient("Split peas", IngredientCategory.LEGUMES, 118, "100g"),
                new Ingredient("Mung beans", IngredientCategory.LEGUMES, 105, "100g"),
                new Ingredient("Fava beans", IngredientCategory.LEGUMES, 88, "100g"),
                new Ingredient("Chickpeas", IngredientCategory.LEGUMES, 164, "100g"),

                // Carbohydrates
                new Ingredient("Sweet potatoes", IngredientCategory.CARBOHYDRATES, 86, "100g"),
                new Ingredient("Quinoa", IngredientCategory.CARBOHYDRATES, 120, "100g"),
                new Ingredient("Brown rice", IngredientCategory.CARBOHYDRATES, 123, "100g"),
                new Ingredient("Oats", IngredientCategory.CARBOHYDRATES, 389, "100g"),
                new Ingredient("Barley", IngredientCategory.CARBOHYDRATES, 354, "100g"),
                new Ingredient("Buckwheat", IngredientCategory.CARBOHYDRATES, 343, "100g"),
                new Ingredient("Millet", IngredientCategory.CARBOHYDRATES, 378, "100g"),
                new Ingredient("Corn", IngredientCategory.CARBOHYDRATES, 365, "100g"),
                new Ingredient("Wild rice", IngredientCategory.CARBOHYDRATES, 101, "100g"),
                new Ingredient("Couscous (whole wheat)", IngredientCategory.CARBOHYDRATES, 376, "100g"),
                new Ingredient("Granola", IngredientCategory.CARBOHYDRATES, 471, "100g"),
                new Ingredient("Rice", IngredientCategory.CARBOHYDRATES, 130, "100g"),
                new Ingredient("Taco shells", IngredientCategory.CARBOHYDRATES, 320, "100g"),
                new Ingredient("Potatoes", IngredientCategory.CARBOHYDRATES, 77, "100g"),

                // Fiber
                new Ingredient("Artichokes", IngredientCategory.FIBER, 47, "100g"),
                new Ingredient("Beets", IngredientCategory.FIBER, 43, "100g"),
                new Ingredient("Berries", IngredientCategory.FIBER, 57, "100g"),
                new Ingredient("Brussels sprouts", IngredientCategory.FIBER, 43, "100g"),
                new Ingredient("Chia seeds", IngredientCategory.FIBER, 486, "100g"),
                new Ingredient("Frozen berries", IngredientCategory.FIBER, 57, "100g"),

                // Vitamins
                new Ingredient("Kale", IngredientCategory.VITAMINS, 49, "100g"),
                new Ingredient("Spinach", IngredientCategory.VITAMINS, 23, "100g"),
                new Ingredient("Bell peppers", IngredientCategory.VITAMINS, 31, "100g"),
                new Ingredient("Carrots", IngredientCategory.VITAMINS, 41, "100g"),
                new Ingredient("Broccoli", IngredientCategory.VITAMINS, 55, "100g"),
                new Ingredient("Tomatoes", IngredientCategory.VITAMINS, 18, "100g"),
                new Ingredient("Oranges", IngredientCategory.VITAMINS, 47, "100g"),
                new Ingredient("Strawberries", IngredientCategory.VITAMINS, 32, "100g"),
                new Ingredient("Blueberries", IngredientCategory.VITAMINS, 57, "100g"),
                new Ingredient("Kiwi", IngredientCategory.VITAMINS, 61, "100g"),
                new Ingredient("Cherry tomatoes", IngredientCategory.VITAMINS, 18, "100g"),
                new Ingredient("Lemon", IngredientCategory.VITAMINS, 29, "100g"),
                new Ingredient("Lemon zest", IngredientCategory.VITAMINS, 29, "100g"),
                new Ingredient("Lemon juice", IngredientCategory.VITAMINS, 29, "100g"),
                new Ingredient("Lime", IngredientCategory.VITAMINS, 29, "100g"),
                new Ingredient("Lime zest", IngredientCategory.VITAMINS, 29, "100g"),
                new Ingredient("Lime juice", IngredientCategory.VITAMINS, 29, "100g"),

                // Healthy Fats
                new Ingredient("Olives", IngredientCategory.HEALTHY_FATS, 115, "100g"),
                new Ingredient("Almonds", IngredientCategory.HEALTHY_FATS, 575, "100g"),
                new Ingredient("Walnuts", IngredientCategory.HEALTHY_FATS, 654, "100g"),
                new Ingredient("Flax seeds", IngredientCategory.HEALTHY_FATS, 534, "100g"),
                new Ingredient("Pumpkin seeds", IngredientCategory.HEALTHY_FATS, 559, "100g"),
                new Ingredient("Sunflower seeds", IngredientCategory.HEALTHY_FATS, 584, "100g"),
                new Ingredient("Pistachios", IngredientCategory.HEALTHY_FATS, 562, "100g"),
                new Ingredient("Avocado", IngredientCategory.HEALTHY_FATS, 160, "100g"),
                new Ingredient("Hemp seeds", IngredientCategory.HEALTHY_FATS, 553, "100g"),
                new Ingredient("Sesame seeds", IngredientCategory.HEALTHY_FATS, 343, "100g"),
                new Ingredient("Peanut butter", IngredientCategory.HEALTHY_FATS, 343, "100g"),
                new Ingredient("Almond butter", IngredientCategory.HEALTHY_FATS, 614, "100g"),

                // Vegetables
                new Ingredient("Cucumbers", IngredientCategory.VEGETABLES, 16, "100g"),
                new Ingredient("Zucchini", IngredientCategory.VEGETABLES, 17, "100g"),
                new Ingredient("Eggplant", IngredientCategory.VEGETABLES, 25, "100g"),
                new Ingredient("Mushrooms", IngredientCategory.VEGETABLES, 22, "100g"),
                new Ingredient("Onion", IngredientCategory.VEGETABLES, 40, "100g"),
                new Ingredient("Garlic", IngredientCategory.VEGETABLES, 149, "100g"),
                new Ingredient("Green beans", IngredientCategory.VEGETABLES, 31, "100g"),
                new Ingredient("Asparagus", IngredientCategory.VEGETABLES, 20, "100g"),
                new Ingredient("Celery", IngredientCategory.VEGETABLES, 16, "100g"),
                new Ingredient("Radishes", IngredientCategory.VEGETABLES, 16, "100g"),
                new Ingredient("Diced tomatoes", IngredientCategory.VEGETABLES, 18, "100g"),
                new Ingredient("Tomato paste", IngredientCategory.VEGETABLES, 82, "100g"),
                new Ingredient("Pumpkin puree", IngredientCategory.VEGETABLES, 34, "100g"),
                new Ingredient("Cauliflower", IngredientCategory.VEGETABLES, 64, "100g"),
                new Ingredient("Vegetable broth", IngredientCategory.VEGETABLES, 10, "100g"),
                new Ingredient("Nori", IngredientCategory.VEGETABLES, 35, "100g"),
                new Ingredient("Lettuce", IngredientCategory.VEGETABLES, 15, "100g"),
                new Ingredient("Green onions", IngredientCategory.VEGETABLES, 32, "100g"),
                new Ingredient("Portobello mushrooms", IngredientCategory.VEGETABLES, 22, "100g"),
                new Ingredient("Coleslaw", IngredientCategory.VEGETABLES, 150, "100g"),
                new Ingredient("Cabbage", IngredientCategory.VEGETABLES, 25, "100g"),
                new Ingredient("Red onion", IngredientCategory.VEGETABLES, 40, "100g"),

                // Fruits
                new Ingredient("Apples", IngredientCategory.FRUIT, 52, "100g"),
                new Ingredient("Bananas", IngredientCategory.FRUIT, 89, "100g"),
                new Ingredient("Raspberries", IngredientCategory.FRUIT, 52, "100g"),
                new Ingredient("Blackberries", IngredientCategory.FRUIT, 43, "100g"),
                new Ingredient("Cranberries", IngredientCategory.FRUIT, 43, "100g"),
                new Ingredient("Cherries", IngredientCategory.FRUIT, 50, "100g"),
                new Ingredient("Grapes", IngredientCategory.FRUIT, 69, "100g"),
                new Ingredient("Mangoes", IngredientCategory.FRUIT, 60, "100g"),
                new Ingredient("Papayas", IngredientCategory.FRUIT, 43, "100g"),
                new Ingredient("Watermelon", IngredientCategory.FRUIT, 30, "100g"),
                new Ingredient("Cantaloupe", IngredientCategory.FRUIT, 34, "100g"),
                new Ingredient("Fresh fruits", IngredientCategory.FRUIT, 50, "100g"),
                new Ingredient("Pears", IngredientCategory.FRUIT, 57, "100g"),
                new Ingredient("Dates", IngredientCategory.FRUIT, 117, "100g"),
                new Ingredient("Jackfruit", IngredientCategory.FRUIT, 95, "100g"),

                // Grains
                new Ingredient("Farro", IngredientCategory.GRAINS, 340, "100g"),
                new Ingredient("Bulgur", IngredientCategory.GRAINS, 342, "100g"),
                new Ingredient("Amaranth", IngredientCategory.GRAINS, 371, "100g"),
                new Ingredient("Spelt", IngredientCategory.GRAINS, 338, "100g"),
                new Ingredient("Rye", IngredientCategory.GRAINS, 335, "100g"),
                new Ingredient("Sorghum", IngredientCategory.GRAINS, 329, "100g"),
                new Ingredient("Teff", IngredientCategory.GRAINS, 367, "100g"),
                new Ingredient("Freekeh", IngredientCategory.GRAINS, 352, "100g"),
                new Ingredient("Kamut", IngredientCategory.GRAINS, 337, "100g"),
                new Ingredient("Whole grain bread", IngredientCategory.GRAINS, 265, "100g"),
                new Ingredient("Sushi rice", IngredientCategory.GRAINS, 130, "100g"),
                new Ingredient("Flour", IngredientCategory.GRAINS, 364, "100g"),
                new Ingredient("Whole wheat flour", IngredientCategory.GRAINS, 340, "100g"),

                // Nuts and Seeds
                new Ingredient("Cashews", IngredientCategory.NUTS_SEEDS, 553, "100g"),
                new Ingredient("Pecans", IngredientCategory.NUTS_SEEDS, 691, "100g"),
                new Ingredient("Brazil nuts", IngredientCategory.NUTS_SEEDS, 659, "100g"),
                new Ingredient("Macadamia nuts", IngredientCategory.NUTS_SEEDS, 718, "100g"),
                new Ingredient("Hazelnuts", IngredientCategory.NUTS_SEEDS, 628, "100g"),
                new Ingredient("Pine nuts", IngredientCategory.NUTS_SEEDS, 673, "100g"),
                new Ingredient("Poppy seeds", IngredientCategory.NUTS_SEEDS, 525, "100g"),

                // Grain Products
                new Ingredient("Whole wheat pasta", IngredientCategory.GRAIN_PRODUCTS, 124, "100g"),
                new Ingredient("Brown rice cakes", IngredientCategory.GRAIN_PRODUCTS, 387, "100g"),
                new Ingredient("Corn tortillas", IngredientCategory.GRAIN_PRODUCTS, 218, "100g"),
                new Ingredient("Oatmeal", IngredientCategory.GRAIN_PRODUCTS, 68, "100g"),
                new Ingredient("Whole wheat crackers", IngredientCategory.GRAIN_PRODUCTS, 450, "100g"),
                new Ingredient("Rice noodles", IngredientCategory.GRAIN_PRODUCTS, 364, "100g"),
                new Ingredient("Quinoa flakes", IngredientCategory.GRAIN_PRODUCTS, 368, "100g"),
                new Ingredient("Buckwheat pancakes", IngredientCategory.GRAIN_PRODUCTS, 89, "100g"),
                new Ingredient("Millet puffs", IngredientCategory.GRAIN_PRODUCTS, 380, "100g"),
                new Ingredient("Multigrain crackers", IngredientCategory.GRAIN_PRODUCTS, 421, "100g"),
                new Ingredient("Tortillas", IngredientCategory.GRAIN_PRODUCTS, 218, "100g"),
                new Ingredient("Popcorn kernels", IngredientCategory.GRAIN_PRODUCTS, 238, "100g"),
                new Ingredient("Cornstarch", IngredientCategory.GRAIN_PRODUCTS, 381, "100g"),
                new Ingredient("Vegan digestive biscuits", IngredientCategory.GRAIN_PRODUCTS, 480, "100g"),
                new Ingredient("Spaghetti", IngredientCategory.GRAIN_PRODUCTS, 158, "100g"),
                new Ingredient("Buns", IngredientCategory.GRAIN_PRODUCTS, 250, "100g"),
                new Ingredient("Pizza dough", IngredientCategory.GRAIN_PRODUCTS, 250, "100g"),
                new Ingredient("Breadcrumbs", IngredientCategory.GRAIN_PRODUCTS, 350, "100g"),
                new Ingredient("Rice cakes", IngredientCategory.GRAIN_PRODUCTS, 387, "100g"),


                // Meat and Dairy Substitutes
                new Ingredient("Soy milk", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 54, "100g"),
                new Ingredient("Almond milk", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 17, "100g"),
                new Ingredient("Oat milk", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 17, "100g"),
                new Ingredient("Coconut milk", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 230, "100g"),
                new Ingredient("Cashew cheese", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 290, "100g"),
                new Ingredient("Vegan yogurt", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 100, "100g"),
                new Ingredient("Plant-based burgers", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 240, "100g"),
                new Ingredient("Vegan cheese", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 300, "100g"),
                new Ingredient("Plant-based sausage", IngredientCategory.MEAT_DAIRY_SUBSTITUTES, 280, "100g"),

                // Sweeteners
                new Ingredient("Maple syrup", IngredientCategory.SWEETENERS, 260, "100g"),
                new Ingredient("Agave nectar", IngredientCategory.SWEETENERS, 310, "100g"),
                new Ingredient("Date syrup", IngredientCategory.SWEETENERS, 282, "100g"),
                new Ingredient("Coconut sugar", IngredientCategory.SWEETENERS, 375, "100g"),
                new Ingredient("Stevia", IngredientCategory.SWEETENERS, 0, "100g"),
                new Ingredient("Molasses", IngredientCategory.SWEETENERS, 290, "100g"),
                new Ingredient("Brown rice syrup", IngredientCategory.SWEETENERS, 316, "100g"),
                new Ingredient("Yacon syrup", IngredientCategory.SWEETENERS, 133, "100g"),
                new Ingredient("Monk fruit sweetener", IngredientCategory.SWEETENERS, 0, "100g"),
                new Ingredient("Erythritol", IngredientCategory.SWEETENERS, 0, "100g"),
                new Ingredient("Sugar", IngredientCategory.SWEETENERS, 387, "100g"),
                new Ingredient("Powdered sugar", IngredientCategory.SWEETENERS, 387, "100g"),
                new Ingredient("Brown sugar", IngredientCategory.SWEETENERS, 380, "100g"),
                new Ingredient("Baking powder", IngredientCategory.SWEETENERS, 53, "100g"),
                new Ingredient("Chocolate chips", IngredientCategory.SWEETENERS, 500, "100g"),
                new Ingredient("Cocoa powder", IngredientCategory.SWEETENERS, 228, "100g"),
                new Ingredient("Dark chocolate", IngredientCategory.SWEETENERS, 546, "100g"),


                // Oils and Condiments
                new Ingredient("Olive oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Coconut oil", IngredientCategory.OILS_CONDIMENTS, 862, "100g"),
                new Ingredient("Avocado oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Sesame oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Flaxseed oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Hemp oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Pumpkin seed oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Sunflower oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Walnut oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Grapeseed oil", IngredientCategory.OILS_CONDIMENTS, 884, "100g"),
                new Ingredient("Vegan mayo", IngredientCategory.OILS_CONDIMENTS, 300, "100g"),
                new Ingredient("Soy sauce", IngredientCategory.OILS_CONDIMENTS, 53, "100g"),
                new Ingredient("Apple cider vinegar", IngredientCategory.OILS_CONDIMENTS, 22, "100g"),
                new Ingredient("Balsamic vinegar", IngredientCategory.OILS_CONDIMENTS, 88, "100g"),
                new Ingredient("Mustard", IngredientCategory.OILS_CONDIMENTS, 66, "100g"),
                new Ingredient("BBQ sauce", IngredientCategory.OILS_CONDIMENTS, 100, "100g"),
                new Ingredient("Tomato sauce", IngredientCategory.OILS_CONDIMENTS, 29, "100g"),
                new Ingredient("Tahini", IngredientCategory.OILS_CONDIMENTS, 595, "100g"),
                new Ingredient("Nutritional yeast", IngredientCategory.OILS_CONDIMENTS, 290, "100g"),
                new Ingredient("Vanilla extract", IngredientCategory.OILS_CONDIMENTS, 12, "100g"),
                new Ingredient("Enchilada sauce", IngredientCategory.OILS_CONDIMENTS, 400, "100g"),
                new Ingredient("Taco seasoning", IngredientCategory.OILS_CONDIMENTS, 260, "100g"),
                new Ingredient("Vegetable shortening", IngredientCategory.OILS_CONDIMENTS, 890, "100g"),
                new Ingredient("Water", IngredientCategory.OILS_CONDIMENTS, 0, "ml"),
                new Ingredient("Pad Thai sauce", IngredientCategory.OILS_CONDIMENTS, 100, "100g"),

                // Spices
                new Ingredient("Salt", IngredientCategory.SPICES, 0, "100g"),
                new Ingredient("Black pepper", IngredientCategory.SPICES, 251, "100g"),
                new Ingredient("Chili powder", IngredientCategory.SPICES, 282, "100g"),
                new Ingredient("Chili flakes", IngredientCategory.SPICES, 282, "100g"),
                new Ingredient("Paprika", IngredientCategory.SPICES, 282, "100g"),
                new Ingredient("Curry powder", IngredientCategory.SPICES, 325, "100g"),
                new Ingredient("Pumpkin spice mix", IngredientCategory.SPICES, 380, "100g"),
                new Ingredient("Cinnamon", IngredientCategory.SPICES, 247, "100g"),
                new Ingredient("Turmeric", IngredientCategory.SPICES, 354, "100g"),
                new Ingredient("Ginger", IngredientCategory.SPICES, 80, "100g"),
                new Ingredient("Nutmeg", IngredientCategory.SPICES, 525, "100g"),
                new Ingredient("Cloves", IngredientCategory.SPICES, 274, "100g"),
                new Ingredient("Cumin", IngredientCategory.SPICES, 375, "100g"),
                new Ingredient("Coriander", IngredientCategory.SPICES, 298, "100g"),
                new Ingredient("Cardamom", IngredientCategory.SPICES, 311, "100g"),
                new Ingredient("Saffron", IngredientCategory.SPICES, 310, "100g"),
                new Ingredient("Fenugreek", IngredientCategory.SPICES, 323, "100g"),
                new Ingredient("Allspice", IngredientCategory.SPICES, 263, "100g"),
                new Ingredient("Anise", IngredientCategory.SPICES, 337, "100g"),
                new Ingredient("Bay leaves", IngredientCategory.SPICES, 313, "100g"),
                new Ingredient("Mustard seeds", IngredientCategory.SPICES, 508, "100g"),
                new Ingredient("Rosemary", IngredientCategory.SPICES, 131, "100g"),
                new Ingredient("Thyme", IngredientCategory.SPICES, 101, "100g"),
                new Ingredient("Oregano", IngredientCategory.SPICES, 265, "100g"),
                new Ingredient("Basil", IngredientCategory.SPICES, 251, "100g"),
                new Ingredient("Tarragon", IngredientCategory.SPICES, 295, "100g"),
                new Ingredient("Sage", IngredientCategory.SPICES, 315, "100g"),
                new Ingredient("Marjoram", IngredientCategory.SPICES, 271, "100g"),
                new Ingredient("Parsley", IngredientCategory.SPICES, 36, "100g"),
                new Ingredient("Mint", IngredientCategory.SPICES, 70, "100g"),
                new Ingredient("Dill", IngredientCategory.SPICES, 43, "100g"),
                new Ingredient("Cilantro", IngredientCategory.SPICES, 23, "100g"),
                new Ingredient("Green curry paste", IngredientCategory.SPICES, 213, "100g"),
                new Ingredient("Garlic powder", IngredientCategory.SPICES, 67, "100g"),
                new Ingredient("Lemongrass", IngredientCategory.SPICES, 99, "100g"),

                // Superfoods
                new Ingredient("Spirulina", IngredientCategory.SUPERFOODS, 290, "100g"),
                new Ingredient("Chlorella", IngredientCategory.SUPERFOODS, 392, "100g"),
                new Ingredient("Maca powder", IngredientCategory.SUPERFOODS, 325, "100g"),
                new Ingredient("Cacao nibs", IngredientCategory.SUPERFOODS, 228, "100g"),
                new Ingredient("Acai berries", IngredientCategory.SUPERFOODS, 70, "100g"),
                new Ingredient("Goji berries", IngredientCategory.SUPERFOODS, 349, "100g"),
                new Ingredient("Moringa powder", IngredientCategory.SUPERFOODS, 34, "100g"),
                new Ingredient("Ashwagandha", IngredientCategory.SUPERFOODS, 277, "100g")
        };

        ingredientRepository.saveAll(Arrays.asList(ingredients));
        System.out.println("Ingredients populated on DB successfully.");
    }
}
