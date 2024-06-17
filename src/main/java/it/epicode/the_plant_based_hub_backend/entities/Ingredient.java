package it.epicode.the_plant_based_hub_backend.entities;

import it.epicode.the_plant_based_hub_backend.entities.enums.IngredientCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Ingredient extends BaseEntity {

    @Column(nullable = false)
    private String ingredientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientCategory ingredientCategory;

    @Column(nullable = false)
    private int caloriesPerServing;

    @Column(nullable = false)
    private String recommendedAmount;

    @Column(nullable = true)
    private Double proteins;

    @Column(nullable = true)
    private Double carbohydrates;

    @Column(nullable = true)
    private Double fats;

    @Column(nullable = true)
    private Double fibers;

    @Column(nullable = true)
    private Double sugars;

    @Column(nullable = true)
    private String vitamins;

    @Column(nullable = true)
    private String minerals;
}
