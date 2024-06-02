package it.epicode.capstone_project_alicelazzeri.entities;

import jakarta.persistence.*;

import java.util.UUID;

public class FavoriteRecipe {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
