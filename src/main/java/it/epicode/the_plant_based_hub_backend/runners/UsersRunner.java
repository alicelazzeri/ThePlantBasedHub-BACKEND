package it.epicode.the_plant_based_hub_backend.runners;

import it.epicode.the_plant_based_hub_backend.entities.enums.Role;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserRegisterRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UsersRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        try {
           userService.findByEmail("alice.lazzeri@hotmail.it");
        } catch (NotFoundException e) {
            UserRegisterRequestDTO adminUser = new UserRegisterRequestDTO(
                    "Alice",
                    "Lazzeri",
                    adminEmail,
                    bcrypt.encode(adminPassword),
                    Role.ADMIN
            );
            userService.saveUserAdmin(adminUser);
        }
    }
}
