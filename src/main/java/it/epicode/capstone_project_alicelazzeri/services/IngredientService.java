package it.epicode.capstone_project_alicelazzeri.services;

import it.epicode.capstone_project_alicelazzeri.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
}
