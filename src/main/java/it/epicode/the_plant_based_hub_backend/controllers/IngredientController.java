package it.epicode.the_plant_based_hub_backend.controllers;

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

    // GET ingredient by name
    // GET http://localhost:8080/api/ingredients/name/{ingredientName} + bearer token

    @GetMapping("/name/{ingredientName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Ingredient> getIngredientByName(@PathVariable String name) {
        Ingredient ingredient = ingredientService.getIngredientByName(name);
        ResponseEntity<Ingredient> responseEntity = new ResponseEntity<>(ingredient, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by proteins range
    // GET http://localhost:8080/api/ingredients/proteins?min={min}&max={max} + bearer token

    @GetMapping("/proteins")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByProteins(@RequestParam double min, @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByProteinsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by carbohydrates range
    // GET http://localhost:8080/api/ingredients/carbohydrates?min={min}&max={max} + bearer token

    @GetMapping("/carbohydrates")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByCarbohydrates(@RequestParam double min, @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByCarbohydratesBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by fats range
    // GET http://localhost:8080/api/ingredients/fats?min={min}&max={max} + bearer token

    @GetMapping("/fats")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByFats(@RequestParam double min, @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByFatsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by fibers range
    // GET http://localhost:8080/api/ingredients/fibers?min={min}&max={max} + bearer token

    @GetMapping("/fibers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByFibers(@RequestParam double min, @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByFibersBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by sugars range
    // GET http://localhost:8080/api/ingredients/sugars?min={min}&max={max} + bearer token

    @GetMapping("/sugars")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsBySugars(@RequestParam double min, @RequestParam double max) {
        List<Ingredient> ingredients = ingredientService.getIngredientsBySugarsBetween(min, max);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by vitamins
    // GET http://localhost:8080/api/ingredients/vitamins/{vitamins} + bearer token

    @GetMapping("/vitamins/{vitamins}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByVitamins(@PathVariable String vitamins) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByVitamins(vitamins);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by minerals
    // GET http://localhost:8080/api/ingredients/minerals/{minerals} + bearer token

    @GetMapping("/minerals/{minerals}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByMinerals(@PathVariable String minerals) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByMinerals(minerals);
        ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
        return responseEntity;
    }

    // GET ingredients by ingredient category
    // GET http://localhost:8080/api/ingredients/category/{ingredientCategory} + bearer token

    @GetMapping("/category/{ingredientCategory}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Ingredient>> getIngredientsByIngredientCategory(@PathVariable IngredientCategory ingredientCategory) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientCategory(ingredientCategory);
        if (ingredients.isEmpty()) {
            throw new NoContentException("No ingredients found for category: " + ingredientCategory);
        } else {
            ResponseEntity<List<Ingredient>> responseEntity = new ResponseEntity<>(ingredients, HttpStatus.OK);
            return responseEntity;
        }
    }

}
