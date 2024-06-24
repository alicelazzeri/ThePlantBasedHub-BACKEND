package it.epicode.the_plant_based_hub_backend.controllers;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.RecipeService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin
@Tag(name = "Recipe API", description = "Operations related to recipes")

public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    // GET http://localhost:8080/api/recipes + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all recipes", description = "Retrieve all recipes",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No recipes found")
    })
    public ResponseEntity<Page<Recipe>> getAllRecipes(Pageable pageable) {
        Page<Recipe> recipes = recipeService.getAllRecipes(pageable);
        if (recipes.isEmpty()) {
            throw new NoContentException("No recipes are present in this list");
        } else {
            ResponseEntity<Page<Recipe>> responseEntity = new ResponseEntity<>(recipes, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/recipes/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipe by ID", description = "Retrieve a recipe by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Recipe> getRecipeById(@Parameter(description = "ID of the recipe to be retrieved") @PathVariable long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(recipe, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/recipes + bearer token
    // saving recipe without ingredients

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new recipe", description = "Create a new recipe without ingredients",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Recipe> saveRecipe(
            @Parameter(description = "Recipe data to be created")
            @RequestBody @Validated RecipeRequestDTO recipePayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Recipe savedRecipe = recipeService.saveRecipeWithoutIngredients(recipePayload);
            ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // POST http://localhost:8080/api/recipes/{id}/ingredients + bearer token
    // saving ingredients in an existing recipe

    @PostMapping("/{id}/ingredients")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add ingredients to a recipe", description = "Add ingredients to an existing recipe",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingredients added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Void> saveRecipeIngredients(
            @Parameter(description = "ID of the recipe to which ingredients will be added")
            @PathVariable long id,
            @Parameter(description = "List of ingredients to be added")
            @RequestBody @Validated List<RecipeIngredientRequestDTO> ingredients,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            recipeService.saveRecipeIngredients(ingredients);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    // PUT http://localhost:8080/api/recipes/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a recipe", description = "Update an existing recipe",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Recipe> updateRecipe(
            @Parameter(description = "ID of the recipe to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated recipe data")
            @RequestBody @Validated RecipeRequestDTO updatedRecipe,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Recipe recipeToBeUpdated = recipeService.updateRecipe(id, updatedRecipe);
            ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(recipeToBeUpdated, HttpStatus.OK);
            return responseEntity;
        }
    }

    // DELETE http://localhost:8080/api/recipes/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a recipe", description = "Delete a recipe by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Void> deleteRecipe(@Parameter(description = "ID of the recipe to be deleted") @PathVariable long id) {
        recipeService.deleteRecipe(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    // GET recipe PDF generation http://localhost:8080/api/recipes/{id}/pdf

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Download recipe as PDF", description = "Generate and download a recipe in PDF format",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated PDF",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Error generating PDF")
    })
    public ResponseEntity<byte[]> downloadRecipePDF(
            @Parameter(description = "ID of the recipe to be downloaded as PDF")
            @PathVariable long id) throws DocumentException, IOException {
        Recipe recipe = recipeService.getRecipeById(id);
        ByteArrayOutputStream output;
        try {
            output = recipeService.generateRecipePDF(recipe);
        } catch (DocumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=recipe_" + id + ".pdf");
        headers.add("Content-Type", "application/pdf");
        return ResponseEntity.ok().headers(headers).body(output.toByteArray());
    }

    // POST sending PDF via email http://localhost:8080/api/recipes/{id}/send-pdf?email={email}

    @PostMapping("/{id}/send-pdf")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Send recipe PDF via email", description = "Generate a recipe PDF and send it to the user via email",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF sent successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "500", description = "Error sending PDF")
    })
    public ResponseEntity<Void> sendRecipePDFViaEmail(
            @Parameter(description = "ID of the recipe to be sent as PDF")
            @PathVariable long id,
            @Parameter(description = "Email address of the recipient")
            @RequestParam String email) {
        try {
            recipeService.sendRecipePdf(email, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (MessagingException | IOException | DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET recipes by recipe name
    // GET http://localhost:8080/api/recipes/name/{recipeName} + bearer token

    @GetMapping("/name/{recipeName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipes by name", description = "Retrieve recipes by name",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "204", description = "No recipes found")
    })
    public ResponseEntity<List<Recipe>> getRecipesByRecipeName(@Parameter(description = "Name of the recipe to be retrieved") @PathVariable String recipeName) {
        List<Recipe> recipes = recipeService.getRecipeByRecipeName(recipeName);
        if (recipes.isEmpty()) {
            throw new NoContentException("No recipes found with name containing: " + recipeName);
        } else {
            ResponseEntity<List<Recipe>> responseEntity = new ResponseEntity<>(recipes, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET recipes by recipe category
    // GET http://localhost:8080/api/recipes/category/{recipeCategory} + bearer token

    @GetMapping("/category/{recipeCategory}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipes by category", description = "Retrieve recipes by category",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "204", description = "No recipes found")
    })
    public ResponseEntity<List<Recipe>> getRecipesByRecipeCategory(
            @Parameter(description = "Category of the recipes to be retrieved") @PathVariable String recipeCategory) {
        List<Recipe> recipes = recipeService.getRecipeByRecipeCategory(recipeCategory);
        if (recipes.isEmpty()) {
            throw new NoContentException("No recipes found in category: " + recipeCategory);
        } else {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }
    }

    // GET recipes by ingredient name
    // GET http://localhost:8080/api/recipes/ingredient/{ingredientName} + bearer token

    @GetMapping("/ingredient/{ingredientName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipes by ingredient", description = "Retrieve recipes by ingredient",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "204", description = "No recipes found")
    })
    public ResponseEntity<List<Recipe>> getRecipesByIngredientName(@Parameter(description = "Name of the ingredient to search for") @PathVariable String ingredientName) {
        List<Recipe> recipes = recipeService.getRecipeByIngredientName(ingredientName);
        if (recipes.isEmpty()) {
            throw new NoContentException("No recipes found with ingredient: " + ingredientName);
        } else {
            ResponseEntity<List<Recipe>> responseEntity = new ResponseEntity<>(recipes, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET recipes by ingredient category
    // GET http://localhost:8080/api/recipes/ingredient-category/{ingredientCategory} + bearer token

    @GetMapping("/ingredient-category/{ingredientCategory}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipes by ingredient category", description = "Retrieve recipes by ingredient category",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "204", description = "No recipes found")
    })
    public ResponseEntity<List<Recipe>> getRecipesByIngredientCategory(
            @Parameter(description = "Category of the ingredient to search for")
            @PathVariable String ingredientCategory) {
        List<Recipe> recipes = recipeService.getRecipeByIngredientCategory(ingredientCategory);
        if (recipes.isEmpty()) {
            throw new NoContentException("No recipes found with ingredient category: " + ingredientCategory);
        } else {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }
    }

}
