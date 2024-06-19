package it.epicode.the_plant_based_hub_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.FavoriteRecipe;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.FavoriteRecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.FavoriteRecipeService;
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
@RequestMapping("/api/favorite-recipes")
@CrossOrigin
@Tag(name = "Favorite Recipe API", description = "Operations related to favorite recipes")

public class FavoriteRecipeController {
    @Autowired
    private FavoriteRecipeService favoriteRecipeService;

    // GET http://localhost:8080/api/favorite-recipes + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all favorite recipes", description = "Retrieve all favorite recipes",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of favorite recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No favorite recipes found")
    })
    public ResponseEntity<Page<FavoriteRecipe>> getAllFavoriteRecipes(Pageable pageable) {
        Page<FavoriteRecipe> favoriteRecipes = favoriteRecipeService.getAllFavoriteRecipes(pageable);
        if (favoriteRecipes.isEmpty()) {
            throw new NoContentException("No favorite recipes are present in this list");
        } else {
            ResponseEntity<Page<FavoriteRecipe>> responseEntity = new ResponseEntity<>(favoriteRecipes, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/favorite-recipes/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get favorite recipe by ID", description = "Retrieve a favorite recipe by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite recipe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteRecipe.class))),
            @ApiResponse(responseCode = "404", description = "Favorite recipe not found")
    })
    public ResponseEntity<FavoriteRecipe> getFavoriteRecipeById(@Parameter(description = "ID of the favorite recipe to be retrieved") @PathVariable long id) {
        FavoriteRecipe favoriteRecipe = favoriteRecipeService.getFavoriteRecipeById(id);
        ResponseEntity<FavoriteRecipe> responseEntity = new ResponseEntity<>(favoriteRecipe, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/favorite-recipes + bearer token

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Create a new favorite recipe", description = "Create a new favorite recipe",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorite recipe created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteRecipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<FavoriteRecipe> saveFavoriteRecipe(
            @Parameter(description = "Favorite recipe data to be created")
            @RequestBody @Validated FavoriteRecipeRequestDTO favoriteRecipePayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            FavoriteRecipe savedFavoriteRecipe = favoriteRecipeService.saveFavoriteRecipe(favoriteRecipePayload);
            ResponseEntity<FavoriteRecipe> responseEntity = new ResponseEntity<>(savedFavoriteRecipe, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/favorite-recipes/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Update a favorite recipe", description = "Update an existing favorite recipe",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite recipe updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteRecipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Favorite recipe not found")
    })
    public ResponseEntity<FavoriteRecipe> updateFavoriteRecipe(
            @Parameter(description = "ID of the favorite recipe to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated favorite recipe data")
            @RequestBody FavoriteRecipeRequestDTO updatedFavoriteRecipe,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            FavoriteRecipe favoriteRecipeToBeUpdated = favoriteRecipeService.updateFavoriteRecipe(id, updatedFavoriteRecipe);
            ResponseEntity<FavoriteRecipe> responseEntity = new ResponseEntity<>(favoriteRecipeToBeUpdated, HttpStatus.OK);
            return responseEntity;
        }
    }

    // DELETE http://localhost:8080/api/favorite-recipes/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Delete a favorite recipe", description = "Delete a favorite recipe by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Favorite recipe not found")
    })
    public ResponseEntity<Void> deleteFavoriteRecipe(@Parameter(description = "ID of the favorite recipe to be deleted") @PathVariable long id) {
        favoriteRecipeService.deleteFavoriteRecipe(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    // GET http://localhost:8080/api/favorite-recipes/user/{userId} + bearer token

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get favorite recipes by user ID", description = "Retrieve favorite recipes by user ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteRecipe.class))),
            @ApiResponse(responseCode = "204", description = "No favorite recipes found")
    })
    public ResponseEntity<List<FavoriteRecipe>> getFavoriteRecipeByUserId(@Parameter(description = "ID of the user to retrieve favorite recipes for") @PathVariable long userId) {
        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeService.getFavoriteRecipeByUserId(userId);
        if (favoriteRecipes.isEmpty()) {
            throw new NoContentException("No favorite recipes found for the user.");
        } else {
           ResponseEntity<List<FavoriteRecipe>> responseEntity = new ResponseEntity<>(favoriteRecipes, HttpStatus.OK);
           return responseEntity;
        }
    }

    // GET http://localhost:8080/api/favorite-recipes/user/{userId}/recipe/{recipeId} + bearer token

    @GetMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get favorite recipe by user ID and recipe ID", description = "Retrieve a favorite recipe by user ID and recipe ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite recipe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteRecipe.class))),
            @ApiResponse(responseCode = "404", description = "Favorite recipe not found")
    })
    public ResponseEntity<FavoriteRecipe> getFavoriteRecipeByUserIdAndRecipeId(
            @Parameter(description = "ID of the user")
            @PathVariable long userId,
            @Parameter(description = "ID of the recipe")
            @PathVariable long recipeId) {
        FavoriteRecipe favoriteRecipe = favoriteRecipeService.getFavoriteRecipeByUserIdAndRecipeId(userId, recipeId);
        ResponseEntity<FavoriteRecipe> responseEntity = new ResponseEntity<>(favoriteRecipe, HttpStatus.OK);
        return responseEntity;
    }
}
