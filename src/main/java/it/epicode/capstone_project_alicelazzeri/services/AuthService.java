package it.epicode.capstone_project_alicelazzeri.services;

import it.epicode.capstone_project_alicelazzeri.entities.User;
import it.epicode.capstone_project_alicelazzeri.exceptions.UnauthorizedException;
import it.epicode.capstone_project_alicelazzeri.payloads.UserLoginRequestDTO;
import it.epicode.capstone_project_alicelazzeri.security.JWTTools;
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

    public String authenticateUserAndGenerateToken(UserLoginRequestDTO loginPayload) {
        User user = userService.findByEmail(loginPayload.email());
        if (bcrypt.matches(loginPayload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials! Try login again.");
        }

    }
}
