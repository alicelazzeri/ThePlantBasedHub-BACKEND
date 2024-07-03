package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.exceptions.UnauthorizedException;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserLoginRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserLoginResponseDTO;
import it.epicode.the_plant_based_hub_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public UserLoginResponseDTO authenticateUserAndGenerateToken(UserLoginRequestDTO loginPayload) {
        User user = userService.findByEmail(loginPayload.email());
        if (bcrypt.matches(loginPayload.password(), user.getPassword())) {
            System.out.println("CORRECT password for created user:" + user.getEmail());
            String token = jwtTools.createToken(user);
            return new UserLoginResponseDTO(token, user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            System.out.println("INCORRECT password for created user:" + user.getEmail());
            throw new UnauthorizedException("Invalid credentials! Try login again.");
        }
    }
}
