package it.epicode.capstone_project_alicelazzeri.entities;

import jakarta.persistence.*;

import java.util.UUID;

public class FavoriteRecipe extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
