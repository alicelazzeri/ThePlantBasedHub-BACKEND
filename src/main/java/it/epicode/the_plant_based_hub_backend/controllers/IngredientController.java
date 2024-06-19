package it.epicode.the_plant_based_hub_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.IngredientRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin
@Tag(name = "Ingredient API", description = "Operations related to ingredients")

public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    // GET http://localhost:8080/api/ingredients + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all ingredients", description = "Retrieve all ingredients",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No ingredients found")
    })
    public ResponseEntity<Page<Ingredient>> getAllIngredients(Pageable pageable) {
        Page<Ingredient> ingredients = ingredientService.getAllIngredients(pageable);
        if (ingredients.isEmpty()) {
            throw  new NoContentException("No ingredients are present in this list");
        } else {
            ResponseEntity<Page<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/ingredients/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredient by ID", description = "Retrieve an ingredient by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredient",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    public ResponseEntity<Ingredient> getIngredientById(@Parameter(description = "ID of the ingredient to be retrieved") @PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(ingredient, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/ingredients + bearer token

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new ingredient", description = "Create a new ingredient",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingredient created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Ingredient> saveIngredient(
            @Parameter(description = "Ingredient data to be created")
            @RequestBody @Validated IngredientRequestDTO ingredientPayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Ingredient savedIngredient = ingredientService.saveIngredient(ingredientPayload);
            ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(savedIngredient, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/ingredients/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update an ingredient", description = "Update an existing ingredient",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    public ResponseEntity<Ingredient> updateIngredient(
            @Parameter(description = "ID of the ingredient to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated ingredient data")
            @RequestBody @Validated IngredientRequestDTO updatedIngredient,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Ingredient ingredientToBeUpdated = ingredientService.updateIngredient(id, updatedIngredient);
            ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(ingredientToBeUpdated, HttpStatus.OK);
            return responseEntity;
        }
    }

    // DELETE http://localhost:8080/api/ingredients/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete an ingredient", description = "Delete an ingredient by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingredient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    public ResponseEntity<Void> deleteIngredient(@Parameter(description = "ID of the ingredient to be deleted") @PathVariable long id) {
        ingredientService.deleteIngredient(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    // GET ingredient by name
    // GET http://localhost:8080/api/ingredients/name/{ingredientName} + bearer token

    @GetMapping("/name/{ingredientName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredient by name", description = "Retrieve an ingredient by name",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredient",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    public ResponseEntity<Ingredient> getIngredientByName(@Parameter(description = "Name of the ingredient to be retrieved") @PathVariable String name) {
        Ingredient ingredient = ingredientService.getIngredientByName(name);
        ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(ingredient, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by proteins range
    // GET http://localhost:8080/api/ingredients/proteins?min={min}&max={max} + bearer token

    @GetMapping("/proteins")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by proteins range", description = "Retrieve ingredients by proteins range",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByProteins(
            @Parameter(description = "Minimum value of proteins")
            @RequestParam double min,
            @Parameter(description = "Maximum value of proteins")
            @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByProteinsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by carbohydrates range
    // GET http://localhost:8080/api/ingredients/carbohydrates?min={min}&max={max} + bearer token

    @GetMapping("/carbohydrates")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by carbohydrates range", description = "Retrieve ingredients by carbohydrates range",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByCarbohydrates(
            @Parameter(description = "Minimum value of carbohydrates")
            @RequestParam double min,
            @Parameter(description = "Maximum value of carbohydrates")
            @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByCarbohydratesBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by fats range
    // GET http://localhost:8080/api/ingredients/fats?min={min}&max={max} + bearer token

    @GetMapping("/fats")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by fats range", description = "Retrieve ingredients by fats range",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByFats(
            @Parameter(description = "Minimum value of fats")
            @RequestParam double min,
            @Parameter(description = "Maximum value of fats")
            @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByFatsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by fibers range
    // GET http://localhost:8080/api/ingredients/fibers?min={min}&max={max} + bearer token

    @GetMapping("/fibers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by fibers range", description = "Retrieve ingredients by fibers range",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByFibers(
            @Parameter(description = "Minimum value of fibers")
            @RequestParam double min,
            @Parameter(description = "Maximum value of fibers")
            @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByFibersBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by sugars range
    // GET http://localhost:8080/api/ingredients/sugars?min={min}&max={max} + bearer token

    @GetMapping("/sugars")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by sugars range", description = "Retrieve ingredients by sugars range",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsBySugars(
            @Parameter(description = "Minimum value of sugars")
            @RequestParam double min,
            @Parameter(description = "Maximum value of sugars")
            @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsBySugarsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by vitamins
    // GET http://localhost:8080/api/ingredients/vitamins/{vitamins} + bearer token

    @GetMapping("/vitamins/{vitamins}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by vitamins", description = "Retrieve ingredients by vitamins",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByVitamins( @Parameter(description = "Vitamins to search for") @PathVariable String vitamins) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByVitamins(vitamins);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by minerals
    // GET http://localhost:8080/api/ingredients/minerals/{minerals} + bearer token

    @GetMapping("/minerals/{minerals}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by minerals", description = "Retrieve ingredients by minerals",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredients not found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByMinerals(@Parameter(description = "Minerals to search for") @PathVariable String minerals) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByMinerals(minerals);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by ingredient category
    // GET http://localhost:8080/api/ingredients/category/{ingredientCategory} + bearer token

    @GetMapping("/category/{ingredientCategory}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get ingredients by category", description = "Retrieve ingredients by category",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ingredients",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "204", description = "No ingredients found")
    })
    public ResponseEntity<List<Ingredient>> getIngredientsByIngredientCategory( @Parameter(description = "Category of the ingredients to be retrieved") @PathVariable IngredientCategory ingredientCategory) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientCategory(ingredientCategory);
        if (ingredients.isEmpty()) {
            throw new NoContentException("No ingredients found for category: " + ingredientCategory);
        } else {
            ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
            return responseEntity;
        }
    }

}
