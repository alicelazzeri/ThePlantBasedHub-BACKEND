package it.epicode.the_plant_based_hub_backend.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeRepository;
import it.epicode.the_plant_based_hub_backend.repositories.RecipeIngredientRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    private String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.findByEmail(email);
            return user.getFirstName();
        }
        return "User";
    }

    public void sendRecipePdf(String email, long recipeId) throws MessagingException, IOException, DocumentException {
        Recipe recipe = getRecipeById(recipeId);
        ByteArrayOutputStream pdfOutputStream = generateRecipePDF(recipe);
        String recipientName = getLoggedInUserName();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setFrom("pbhcustomerservice@gmail.com", "The Plant Based Hub");
            helper.setSubject("Your Requested Recipe PDF from The Plant Based Hub 🌱");

            String emailContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "@import url('https://fonts.googleapis.com/css2?family=Forum&display=swap');" +
                    "@import url('https://fonts.googleapis.com/css2?family=Tenor+Sans&display=swap');" +
                    "body { font-family: 'Tenor Sans', Arial, sans-serif; font-weight: 400 }" +
                    ".email-container { padding: 20px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"email-container\">" +
                    "<div>" +
                    "<p style=\"font-size: 18px;\">Hello <span style=\"color: green;\"><strong>" + recipientName + "</strong></span>,</p>" +
                    "<p style=\"font-size: 14px;\">We are pleased to provide you with the PDF of your requested recipe from 🌱 <strong><span style='font-family: \"Forum\", Arial, sans-serif;'>The Plant Based Hub</span></strong>! 🌱</p>" +
                    "<p style=\"font-size: 14px;\">Your journey to delicious and nutritious plant-based meals continues with this recipe. Attached, you'll find the detailed instructions and ingredients to create a wonderful dish. Enjoy your cooking experience and feel free to explore more recipes on our platform.</p>" +
                    "<p style=\"font-size: 14px;\">Here at The Plant Based Hub, we are committed to providing you with the best plant-based recipes to support your healthy lifestyle.</p>" +
                    "<p style=\"font-size: 14px;\">❓ If you have any questions or need assistance, please don't hesitate to reach out to our support team via email at <a href='mailto:pbhcustomerservice@gmail.com'>pbhcustomerservice@gmail.com</a>.</p>" +
                    "<p style=\"font-size: 14px;\">Happy Cooking! 💖</p>" +
                    "<p style=\"font-size: 14px;\">🌱 The Plant Based Hub Team 🌱</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);
            helper.addAttachment("recipe.pdf", new ByteArrayResource(pdfOutputStream.toByteArray()));

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // GET all recipes

    @Transactional(readOnly = true)
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    // GET recipe by ID

    @Transactional(readOnly = true)
    public Recipe getRecipeById(long id) {
        return recipeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Recipe with id: " + id + " not found"));
    }

    // POST save recipe without ingredients

    @Transactional
    public Recipe saveRecipeWithoutIngredients(RecipeRequestDTO recipePayload) {
        Recipe recipe = mapToEntity(recipePayload);
        recipe.setIngredients(new ArrayList<>());  // Save without ingredients
        return recipeRepository.save(recipe);
    }

    // POST save ingredients in existing recipe

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

    // Map RecipeDTO to Recipe entity

    private Recipe mapToEntity(RecipeRequestDTO recipeRequestDTO) {
        return Recipe.builder()
                .withRecipeName(recipeRequestDTO.recipeName())
                .withRecipeDescription(recipeRequestDTO.recipeDescription())
                .withRecipeCategory(recipeRequestDTO.recipeCategory())
                .withRecipeInstructions(recipeRequestDTO.recipeInstructions())
                .withPreparationTime(recipeRequestDTO.preparationTime())
                .withNumberOfServings(recipeRequestDTO.numberOfServings())
                .withCaloriesPerServing(recipeRequestDTO.caloriesPerServing())
                .withImageUrl(recipeRequestDTO.imageUrl())
                .withIngredients(new ArrayList<>())
                .build();
    }

    // Map RecipeIngredientDTO to RecipeIngredient entity

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
        existingRecipe.setImageUrl(recipeRequestDTO.imageUrl());

        if (recipeRequestDTO.ingredients() != null) {
            List<RecipeIngredient> newIngredients = recipeRequestDTO.ingredients().stream()
                    .map(this::mapToRecipeIngredientEntity)
                    .collect(Collectors.toList());

            // Clear existing ingredients and add new ones
            existingRecipe.getIngredients().clear();
            existingRecipe.getIngredients().addAll(newIngredients);
        }
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

        // Add logo with text
        Image logo = Image.getInstance("src/main/resources/images/logo.png"); // Path to your logo
        logo.scaleToFit(80, 80);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Paragraph logoText = new Paragraph("THE PLANT BASED HUB", new Font(lora, 16, Font.BOLD, green));
        logoText.setAlignment(Element.ALIGN_CENTER);
        logoText.setSpacingAfter(20);
        document.add(logoText);

        // Recipe title
        Paragraph recipeTitle = new Paragraph(recipe.getRecipeName(), new Font(lora, 16, Font.BOLD, green));
        recipeTitle.setAlignment(Element.ALIGN_CENTER);
        recipeTitle.setSpacingAfter(20);
        document.add(recipeTitle);

        // Description
        Paragraph descriptionTitle = new Paragraph("Description", loraFont);
        descriptionTitle.setAlignment(Element.ALIGN_CENTER);
        descriptionTitle.setSpacingAfter(10);
        document.add(descriptionTitle);

        Paragraph description = new Paragraph(recipe.getRecipeDescription(), ralewayFont);
        description.setAlignment(Element.ALIGN_CENTER);
        description.setSpacingAfter(20);
        document.add(description);

        // Category
        Paragraph category = new Paragraph();
        category.setAlignment(Element.ALIGN_CENTER);
        Chunk categoryLabel = new Chunk("Category: ", loraFont);
        Chunk categoryValue = new Chunk(recipe.getRecipeCategory().toString(), ralewayFont);
        category.add(categoryLabel);
        category.add(categoryValue);
        category.setSpacingAfter(10);
        document.add(category);

        // Preparation time
        Paragraph prepTime = new Paragraph();
        prepTime.setAlignment(Element.ALIGN_CENTER);
        Chunk prepTimeLabel = new Chunk("Preparation time: ", loraFont);
        Chunk prepTimeValue = new Chunk(recipe.getPreparationTime() + " minutes", ralewayFont);
        prepTime.add(prepTimeLabel);
        prepTime.add(prepTimeValue);
        prepTime.setSpacingAfter(10);
        document.add(prepTime);

        // Number of servings
        Paragraph numberOfServings = new Paragraph();
        numberOfServings.setAlignment(Element.ALIGN_CENTER);
        Chunk servingsLabel = new Chunk("Number of servings: ", loraFont);
        Chunk servingsValue = new Chunk(String.valueOf(recipe.getNumberOfServings()), ralewayFont);
        numberOfServings.add(servingsLabel);
        numberOfServings.add(servingsValue);
        numberOfServings.setSpacingAfter(10);
        document.add(numberOfServings);

        // Calories per serving
        Paragraph caloriesPerServing = new Paragraph();
        caloriesPerServing.setAlignment(Element.ALIGN_CENTER);
        Chunk caloriesLabel = new Chunk("Calories per serving: ", loraFont);
        Chunk caloriesValue = new Chunk(String.valueOf(recipe.getCaloriesPerServing()) + " kcal", ralewayFont);
        caloriesPerServing.add(caloriesLabel);
        caloriesPerServing.add(caloriesValue);
        caloriesPerServing.setSpacingAfter(20);
        document.add(caloriesPerServing);

        // Ingredients
        Paragraph ingredientsTitle = new Paragraph("Ingredients:", loraFont);
        ingredientsTitle.setAlignment(Element.ALIGN_CENTER);
        ingredientsTitle.setSpacingAfter(10);
        document.add(ingredientsTitle);

        String ingredients = recipe.getIngredients().stream()
                .map(ingredient -> ingredient.getIngredient().getIngredientName() + " - " +
                        ingredient.getQuantity() + " " + ingredient.getMeasurementUnit())
                .collect(Collectors.joining("\n"));

        Paragraph ingredientsParagraph = new Paragraph(ingredients, ralewayFont);
        ingredientsParagraph.setAlignment(Element.ALIGN_CENTER);
        ingredientsParagraph.setSpacingAfter(20);
        document.add(ingredientsParagraph);

        // Instructions
        Paragraph instructionsTitle = new Paragraph("Instructions:", loraFont);
        instructionsTitle.setAlignment(Element.ALIGN_CENTER);
        instructionsTitle.setSpacingAfter(10);
        document.add(instructionsTitle);

        Paragraph instructions = new Paragraph(recipe.getRecipeInstructions(), ralewayFont);
        instructions.setAlignment(Element.ALIGN_CENTER);
        instructions.setSpacingAfter(20);
        document.add(instructions);

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
    public List<Recipe> getRecipeByRecipeCategory(String recipeCategory) {
        RecipeCategory category;
        try {
            category = RecipeCategory.valueOf(recipeCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Recipe category " + recipeCategory + " not found");
        }
        return recipeRepository.findByRecipeCategory(category);
    }

    // GET recipe by ingredient name

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByIngredientName(String ingredientName) {
        return recipeRepository.findByIngredientsIngredientIngredientNameContainingIgnoreCase(ingredientName);
    }

    // GET recipe by ingredient category

    @Transactional(readOnly = true)
    public List<Recipe> getRecipeByIngredientCategory(String ingredientCategory) {
        IngredientCategory category;
        try {
            category = IngredientCategory.valueOf(ingredientCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Ingredient category " + ingredientCategory + " not found");
        }
        return recipeRepository.findByIngredientsIngredientIngredientCategory(category);
    }

    // GET recipe by total proteins

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalProteins(double minProteins, double maxProteins) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    double totalProteins = recipe.getIngredients().stream()
                            .mapToDouble(ri -> ri.getIngredient().getProteins())
                            .sum();
                    return totalProteins >= minProteins && totalProteins <= maxProteins;
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total carbohydrates

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalCarbohydrates(double minCarbohydrates, double maxCarbohydrates) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    double totalCarbohydrates = recipe.getIngredients().stream()
                            .mapToDouble(ri -> ri.getIngredient().getCarbohydrates())
                            .sum();
                    return totalCarbohydrates >= minCarbohydrates && totalCarbohydrates <= maxCarbohydrates;
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total fats

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalFats(double minFats, double maxFats) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    double totalFats = recipe.getIngredients().stream()
                            .mapToDouble(ri -> ri.getIngredient().getFats())
                            .sum();
                    return totalFats >= minFats && totalFats <= maxFats;
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total fibers

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalFibers(double minFibers, double maxFibers) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    double totalFibers = recipe.getIngredients().stream()
                            .mapToDouble(ri -> ri.getIngredient().getFibers())
                            .sum();
                    return totalFibers >= minFibers && totalFibers <= maxFibers;
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total sugars

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalSugars(double minSugars, double maxSugars) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    double totalSugars = recipe.getIngredients().stream()
                            .mapToDouble(ri -> ri.getIngredient().getSugars())
                            .sum();
                    return totalSugars >= minSugars && totalSugars <= maxSugars;
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total vitamins

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalVitamins(String vitamins) {
        List<String> vitaminList = List.of(vitamins.toLowerCase().split(",\\s*"));
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    String totalVitamins = recipe.getIngredients().stream()
                            .flatMap(ri -> List.of(ri.getIngredient().getVitamins().toLowerCase().split(",\\s*")).stream())
                            .distinct()
                            .collect(Collectors.joining(", "));
                    return vitaminList.stream().allMatch(totalVitamins::contains);
                })
                .collect(Collectors.toList());
    }

    // GET recipe by total minerals

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByTotalMinerals(String minerals) {
        List<String> mineralList = List.of(minerals.toLowerCase().split(",\\s*"));
        List<Recipe> allRecipes = recipeRepository.findAll();
        return allRecipes.stream()
                .filter(recipe -> {
                    String totalMinerals = recipe.getIngredients().stream()
                            .flatMap(ri -> List.of(ri.getIngredient().getMinerals().toLowerCase().split(",\\s*")).stream())
                            .distinct()
                            .collect(Collectors.joining(", "));
                    return mineralList.stream().allMatch(totalMinerals::contains);
                })
                .collect(Collectors.toList());
    }
}
