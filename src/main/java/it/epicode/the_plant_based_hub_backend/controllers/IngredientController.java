package it.epicode.the_plant_based_hub_backend.controllers;

import it.epicode.the_plant_based_hub_backend.entities.Ingredient;
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

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    // GET http://localhost:8080/api/ingredients + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(ingredient, HttpStatus.OK);
        return responseEntity;
    }

    // POST http://localhost:8080/api/ingredients + bearer token

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Ingredient> saveIngredient(
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
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable long id,
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
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        ingredientService.deleteIngredient(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
