package it.epicode.capstone_project_alicelazzeri.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @ManyToOne
    @JoinColumn(name ="recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="ingredient_id", nullable = false)
    private Ingredient ingredient;
}
