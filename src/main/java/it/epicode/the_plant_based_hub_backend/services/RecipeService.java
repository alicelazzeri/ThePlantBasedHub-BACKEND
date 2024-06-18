package it.epicode.the_plant_based_hub_backend.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeRepository;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientService ingredientService;

    // GET all recipes
    @Transactional(readOnly = true)
    public Page<Recipe> getAllRecipes(Pageable pageable){
        return recipeRepository.findAll(pageable);
    }

    // GET recipe by ID

    @Transactional(readOnly = true)
    public Recipe getRecipeById(long id) {
        return recipeRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Recipe with id: " + id + " not found"));
    }

    // POST save recipe without ingredients

    @Transactional
    public Recipe saveRecipeWithoutIngredients(RecipeRequestDTO recipePayload) {
        Recipe recipe = mapToEntity(recipePayload);
        recipe.setIngredients(new ArrayList<>());  // Save without ingredients
        return recipeRepository.save(recipe);
    }

    // POST save recipe with ingredients

    @Transactional
    public void saveRecipeIngredients(List<RecipeIngredientRequestDTO> ingredients) {
        for (RecipeIngredientRequestDTO dto : ingredients) {
            RecipeIngredient recipeIngredient = mapToRecipeIngredientEntity(dto);
            recipeIngredientRepository.save(recipeIngredient);
        }
    }

    // PUT updating recipe

    @Transactional
    public Recipe updateRecipe(long id, RecipeRequestDTO updatedRecipe) {
        Recipe recipeToBeUpdated = this.getRecipeById(id);
        if (recipeToBeUpdated == null) {
            throw new NotFoundException("Recipe with id: " + id + " not found");
        } else {
            updateRecipeFromDTO(recipeToBeUpdated, updatedRecipe);
            return recipeRepository.save(recipeToBeUpdated);
        }
    }

    // DELETE recipe

    @Transactional
    public void deleteRecipe(long id) {
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException("Recipe with id: " + id + " not found");
        } else {
            recipeRepository.deleteById(id);
        }
    }

    // Map RecipeDTO to Recipe entity (converts RecipeDTO to a Recipe entity instance in order to save or
    // update data on db via RecipeRepository)

    private Recipe mapToEntity(RecipeRequestDTO recipeRequestDTO) {
        return Recipe.builder()
                .withRecipeName(recipeRequestDTO.recipeName())
                .withRecipeDescription(recipeRequestDTO.recipeDescription())
                .withRecipeCategory(recipeRequestDTO.recipeCategory())
                .withRecipeInstructions(recipeRequestDTO.recipeInstructions())
                .withPreparationTime(recipeRequestDTO.preparationTime())
                .withNumberOfServings(recipeRequestDTO.numberOfServings())
                .withCaloriesPerServing(recipeRequestDTO.caloriesPerServing())
                .withIngredients(new ArrayList<>())
                .build();
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity (converts RecipeIngredientDTO to a RecipeIngredient entity instance in order to save or
    // update data on db via recipeIngredientRepository)

    private RecipeIngredient mapToRecipeIngredientEntity(RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        Ingredient ingredient = ingredientService.getIngredientById(recipeIngredientRequestDTO.ingredientId());
        Recipe recipe = getRecipeById(recipeIngredientRequestDTO.recipeId());
        return RecipeIngredient.builder()
                .withQuantity(recipeIngredientRequestDTO.quantity())
                .withMeasurementUnit(recipeIngredientRequestDTO.measurementUnit())
                .withIngredient(ingredient)
                .withRecipe(recipe)
                .build();
    }

    // update already existing recipe from RecipeDTO

    private void updateRecipeFromDTO(Recipe existingRecipe, RecipeRequestDTO recipeRequestDTO) {
        existingRecipe.setRecipeName(recipeRequestDTO.recipeName());
        existingRecipe.setRecipeDescription(recipeRequestDTO.recipeDescription());
        existingRecipe.setRecipeCategory(recipeRequestDTO.recipeCategory());
        existingRecipe.setRecipeInstructions(recipeRequestDTO.recipeInstructions());
        existingRecipe.setPreparationTime(recipeRequestDTO.preparationTime());
        existingRecipe.setNumberOfServings(recipeRequestDTO.numberOfServings());
        existingRecipe.setCaloriesPerServing(recipeRequestDTO.caloriesPerServing());

        List<RecipeIngredient> ingredients = recipeRequestDTO.ingredients().stream()
                .map(this::mapToRecipeIngredientEntity)
                .collect(Collectors.toList());
        existingRecipe.setIngredients(ingredients);
    }

    // recipe PDF generation

    public ByteArrayOutputStream generateRecipePDF(Recipe recipe) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, output);
        document.open();

        // Color configuration
        BaseColor black = new BaseColor(0, 0, 0);
        BaseColor green = new BaseColor(0, 96, 47);

        // Font configuration
        BaseFont raleway = BaseFont.createFont("src/main/resources/fonts/Raleway/Raleway-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font ralewayFont = new Font(raleway, 12, Font.NORMAL, black);
        BaseFont lora = BaseFont.createFont("src/main/resources/fonts/Lora/Lora-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font loraFont = new Font(lora, 13, Font.NORMAL, green);

        // Recipe title
        Paragraph recipeTitle = new Paragraph(recipe.getRecipeName(), new Font(lora, 16, Font.BOLD, green));
        recipeTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(recipeTitle);

        // Description
        document.add(new Paragraph("Description", loraFont));
        document.add(new Paragraph(recipe.getRecipeDescription(), ralewayFont));

        // Category
        Paragraph category = new Paragraph();
        Chunk categoryLabel = new Chunk("Category: ", loraFont);
        Chunk categoryValue = new Chunk(recipe.getRecipeCategory().toString(), ralewayFont);
        category.add(categoryLabel);
        category.add(categoryValue);
        document.add(category);

        // Preparation time
        Paragraph prepTime = new Paragraph();
        Chunk prepTimeLabel = new Chunk("Preparation time: ", loraFont);
        Chunk prepTimeValue = new Chunk(recipe.getPreparationTime() + " minutes", ralewayFont);
        prepTime.add(prepTimeLabel);
        prepTime.add(prepTimeValue);
        document.add(prepTime);

        // Number of servings
        Paragraph numberOfServings = new Paragraph();
        Chunk servingsLabel = new Chunk("Number of servings: ", loraFont);
        Chunk servingsValue = new Chunk(String.valueOf(recipe.getNumberOfServings()), ralewayFont);
        numberOfServings.add(servingsLabel);
        numberOfServings.add(servingsValue);
        document.add(numberOfServings);

        // Calories per serving
        Paragraph caloriesPerServing = new Paragraph();
        Chunk caloriesLabel = new Chunk("Calories per serving: ", loraFont);
        Chunk caloriesValue = new Chunk(String.valueOf(recipe.getCaloriesPerServing()) + " kcal", ralewayFont);
        caloriesPerServing.add(caloriesLabel);
        caloriesPerServing.add(caloriesValue);
        document.add(caloriesPerServing);

        // Ingredients
        document.add(new Paragraph("Ingredients:", loraFont));
        String ingredients = recipe.getIngredients().stream()
                .map(ingredient -> ingredient.getIngredient().getIngredientName() + " - " +
                        ingredient.getQuantity() + " " + ingredient.getMeasurementUnit())
                .collect(Collectors.joining("\n"));
        document.add(new Paragraph(ingredients, ralewayFont));

        // Instructions
        document.add(new Paragraph("Instructions:", loraFont));
        document.add(new Paragraph(recipe.getRecipeInstructions(), ralewayFont));

        document.close();
        return output;
    }

    // GET recipe by recipe name

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByRecipeName(String recipeName) {
        return recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName);
    }

    // GET recipe by recipe category

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByRecipeCategory(RecipeCategory recipeCategory) {
        return recipeRepository.findByRecipeCategory(recipeCategory);
    }

    // GET recipe by ingredient name

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByIngredientName (String ingredientName) {
        return recipeRepository.findByIngredientsIngredientIngredientName(ingredientName);
    }

    // GET recipe by ingredient category

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByIngredientCategory(IngredientCategory ingredientCategory) {
        return recipeRepository.findByIngredientsIngredientIngredientCategory(ingredientCategory);
    }

}
