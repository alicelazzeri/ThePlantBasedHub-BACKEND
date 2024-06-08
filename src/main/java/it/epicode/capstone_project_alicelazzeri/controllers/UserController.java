package it.epicode.capstone_project_alicelazzeri.controllers;

import it.epicode.capstone_project_alicelazzeri.entities.User;
import it.epicode.capstone_project_alicelazzeri.exceptions.BadRequestException;
import it.epicode.capstone_project_alicelazzeri.exceptions.NoContentException;
import it.epicode.capstone_project_alicelazzeri.exceptions.NotFoundException;
import it.epicode.capstone_project_alicelazzeri.payloads.UserRegisterRequestDTO;
import it.epicode.capstone_project_alicelazzeri.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET http://localhost:8080/api/users + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        if (users.isEmpty()) {
            throw new NoContentException("No users were found.");
        } else {
            ResponseEntity<Page<User>> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/users/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);

        User user = userOptional.orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));

        return ResponseEntity.ok(user);
    }

    // PUT http://localhost:8080/api/users/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<User> updateUser(
            @PathVariable long id,
            @RequestBody @Validated UserRegisterRequestDTO userDto,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE http://localhost:8080/api/users/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/api/users/upload + bearer token

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.userService.uploadImage(image);
    }
}
