package it.epicode.the_plant_based_hub_backend.controllers;

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

@RestController
@RequestMapping("/api/favorite-recipes")
@CrossOrigin
public class FavoriteRecipeController {
    @Autowired
    private FavoriteRecipeService favoriteRecipeService;

    // GET http://localhost:8080/api/favorite-recipes + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    public ResponseEntity<FavoriteRecipe> getFavoriteRecipeById(@PathVariable long id) {
        FavoriteRecipe favoriteRecipe = favoriteRecipeService.getFavoriteRecipeById(id);
        ResponseEntity<FavoriteRecipe> responseEntity = new ResponseEntity<>(favoriteRecipe, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/favorite-recipes + bearer token

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<FavoriteRecipe> saveFavoriteRecipe(
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
    public ResponseEntity<FavoriteRecipe> updateFavoriteRecipe(
            @PathVariable long id,
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
    public ResponseEntity<Void> deleteFavoriteRecipe(@PathVariable long id) {
        favoriteRecipeService.deleteFavoriteRecipe(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }



}
