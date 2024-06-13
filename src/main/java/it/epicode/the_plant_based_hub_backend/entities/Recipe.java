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

@Entity
@Table(name="recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Recipe extends BaseEntity {

    @Column(nullable = false)
    private String recipeName;
    @Column(nullable = false)
    private String recipeDescription;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipeCategory recipeCategory;
    @Column(nullable = false)
    private String recipeInstructions;
    @Column(nullable = false)
    private int preparationTime;
    @Column(nullable = false)
    private int numberOfServings;
    @Column(nullable = false)
    private int caloriesPerServing;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("recipe")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

}
