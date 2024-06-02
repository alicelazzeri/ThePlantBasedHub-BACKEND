package it.epicode.capstone_project_alicelazzeri.entities;

import it.epicode.capstone_project_alicelazzeri.entities.enums.IngredientCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Ingredient {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String ingredientName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientCategory ingredientCategory;
    @Column(nullable = false)
    private int caloriesPerServing;
    @Column(nullable = false)
    private String recommendedAmount;
}
