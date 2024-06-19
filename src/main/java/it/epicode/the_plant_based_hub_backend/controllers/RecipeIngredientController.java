package it.epicode.the_plant_based_hub_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe-ingredients")
@CrossOrigin
@Tag(name = "Recipe Ingredient API", description = "Operations related to recipe ingredients")

public class RecipeIngredientController {

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    // GET http://localhost:8080/api/recipe-ingredients + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all recipe ingredients", description = "Retrieve all recipe ingredients",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of recipe ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No recipe ingredients found")
    })
    public ResponseEntity<Page<RecipeIngredient>> getAllRecipeIngredients(Pageable pageable) {
        Page<RecipeIngredient> recipeIngredients = recipeIngredientService.getAllRecipeIngredients(pageable);
        if (recipeIngredients.isEmpty()) {
            throw new NoContentException("No recipe ingredients are present in this list");
        } else {
            ResponseEntity<Page<RecipeIngredient>> responseEntity = new ResponseEntity<>(recipeIngredients, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/recipe-ingredients/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get recipe ingredient by ID", description = "Retrieve a recipe ingredient by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipe ingredient",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeIngredient.class))),
            @ApiResponse(responseCode = "404", description = "Recipe ingredient not found")
    })
    public ResponseEntity<RecipeIngredient> getRecipeIngredientById(@Parameter(description = "ID of the recipe ingredient to be retrieved") @PathVariable long id) {
        RecipeIngredient recipeIngredient = recipeIngredientService.getRecipeIngredientById(id);
        ResponseEntity<RecipeIngredient> responseEntity = new ResponseEntity<>(recipeIngredient, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/recipe-ingredients + bearer token

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new recipe ingredient", description = "Create a new recipe ingredient",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe ingredient created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeIngredient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RecipeIngredient> saveRecipeIngredient(
            @Parameter(description = "Recipe ingredient data to be created")
            @RequestBody @Validated RecipeIngredientRequestDTO recipeIngredientPayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            RecipeIngredient savedRecipeIngredient = recipeIngredientService.saveRecipeIngredient(recipeIngredientPayload);
            ResponseEntity<RecipeIngredient> responseEntity = new ResponseEntity<>(savedRecipeIngredient, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/recipe-ingredients/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a recipe ingredient", description = "Update an existing recipe ingredient",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe ingredient updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeIngredient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Recipe ingredient not found")
    })
    public ResponseEntity<RecipeIngredient> updateRecipeIngredient(
            @Parameter(description = "ID of the recipe ingredient to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated recipe ingredient data")
            @RequestBody @Validated RecipeIngredientRequestDTO updatedRecipeIngredient,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            RecipeIngredient recipeIngredientToBeUpdated = recipeIngredientService.updateRecipeIngredient(id, updatedRecipeIngredient);
            ResponseEntity<RecipeIngredient> responseEntity = new ResponseEntity<>(recipeIngredientToBeUpdated, HttpStatus.OK);
            return responseEntity;
        }
    }

    // DELETE http://localhost:8080/api/recipe-ingredients/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a recipe ingredient", description = "Delete a recipe ingredient by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe ingredient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe ingredient not found")
    })
    public ResponseEntity<Void> deleteRecipeIngredient( @Parameter(description = "ID of the recipe ingredient to be deleted") @PathVariable long id) {
        recipeIngredientService.deleteRecipeIngredient(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
