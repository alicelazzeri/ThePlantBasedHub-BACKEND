package it.epicode.capstone_project_alicelazzeri.services;

import it.epicode.capstone_project_alicelazzeri.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
}
