package it.epicode.the_plant_based_hub_backend.runners;

import com.github.javafaker.Faker;
import it.epicode.the_plant_based_hub_backend.entities.enums.Role;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserRegisterRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class UsersRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    private Faker faker = new Faker();


    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }

    private void createUsers() {
        for (int i = 0; i < 10; i++) {
            UserRegisterRequestDTO userDto = new UserRegisterRequestDTO(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    bcrypt.encode(faker.internet().password()),
                    Role.USER
            );
            userService.saveUser(userDto);
        }
        System.out.println("Users have been successfully added to the DB.");
    }
}
