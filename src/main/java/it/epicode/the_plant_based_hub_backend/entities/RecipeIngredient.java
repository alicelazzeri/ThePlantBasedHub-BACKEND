package it.epicode.the_plant_based_hub_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="recipe_ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class RecipeIngredient extends BaseEntity {

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String measurementUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="recipe_id", nullable = false)
   @JsonBackReference
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ingredient_id", nullable = false)
    private Ingredient ingredient;
}
