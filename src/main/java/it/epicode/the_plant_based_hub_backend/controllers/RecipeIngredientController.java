package it.epicode.the_plant_based_hub_backend.controllers;

import it.epicode.the_plant_based_hub_backend.entities.RecipeIngredient;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeIngredientDTO;
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
public class RecipeIngredientController {

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    // GET http://localhost:8080/api/recipe-ingredients + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<RecipeIngredient>> getAllEvents(Pageable pageable) {
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
    public ResponseEntity<RecipeIngredient> getRecipeIngredientById(@PathVariable long id) {
        RecipeIngredient recipeIngredient = recipeIngredientService.getRecipeIngredientById(id);
        ResponseEntity<RecipeIngredient> responseEntity = new ResponseEntity<>(recipeIngredient, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/recipe-ingredients + bearer token

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<RecipeIngredient> saveRecipeIngredient(
            @RequestBody @Validated RecipeIngredientDTO recipeIngredientPayload,
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<RecipeIngredient> updateRecipeIngredient(
            @PathVariable long id,
            @RequestBody @Validated RecipeIngredientDTO updatedRecipeIngredient,
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteRecipeIngredient(@PathVariable long id) {
        recipeIngredientService.deleteRecipeIngredient(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
