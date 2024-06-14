package it.epicode.the_plant_based_hub_backend.controllers;

import com.itextpdf.text.DocumentException;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.RecipeRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.RecipeService;
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

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    // GET http://localhost:8080/api/recipes + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(recipe, HttpStatus.OK);
                return responseEntity;
    }

    // POST http://localhost:8080/api/recipes + bearer token

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Recipe> saveRecipe(
            @RequestBody @Validated RecipeRequestDTO recipePayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Recipe savedRecipe = recipeService.saveRecipe(recipePayload);
            ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/recipes/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable long id,
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
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }

    // GET recipe PDF generation http://localhost:8080/api/recipes/{id}/pdf

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<byte[]> downloadRecipePDF(@PathVariable long id) throws DocumentException, IOException {
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
}
