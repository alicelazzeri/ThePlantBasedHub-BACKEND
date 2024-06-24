package it.epicode.the_plant_based_hub_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.epicode.the_plant_based_hub_backend.entities.enums.RecipeCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String recipeName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recipeDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeCategory recipeCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recipeInstructions;

    @Column(nullable = false)
    private int preparationTime;

    @Column(nullable = false)
    private int numberOfServings;

    @Column(nullable = false)
    private int caloriesPerServing;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"recipe", "hibernateLazyInitializer", "handler"})
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public List<String> getIngredientNames() {
        return ingredients.stream()
                .map(RecipeIngredient::getIngredientName)
                .collect(Collectors.toList());
    }

}