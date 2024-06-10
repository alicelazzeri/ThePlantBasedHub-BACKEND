package it.epicode.the_plant_based_hub_backend.controllers;

import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserLoginRequestDTO;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserLoginResponseDTO;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserRegisterRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.AuthService;
import it.epicode.the_plant_based_hub_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // POST http://localhost:8080/api/auth/register

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody @Validated UserRegisterRequestDTO registerPayload,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        String result = userService.saveUser(registerPayload);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // POST http://localhost:8080/api/auth/register/admin

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(
            @RequestBody @Validated UserRegisterRequestDTO registerPayload,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        String result = userService.saveUserAdmin(registerPayload);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // POST http://localhost:8080/api/auth/login

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO loginPayload) {
        String token = authService.authenticateUserAndGenerateToken(loginPayload);
        UserLoginResponseDTO response = new UserLoginResponseDTO(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
