package it.epicode.the_plant_based_hub_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserRegisterRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.UserService;
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
@CrossOrigin
@Tag(name = "User API", description = "Operations related to users")

public class UserController {

    @Autowired
    private UserService userService;

    // GET http://localhost:8080/api/users + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all users", description = "Retrieve all users",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
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
    @Operation(summary = "Get user by ID", description = "Retrieve a user by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);
        User user = userOptional.orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found"));
        return ResponseEntity.ok(user);
    }

    // POST http://localhost:8080/api/users

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new user", description = "Create a new user",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<User> saveUser(
            @Parameter(description = "User data to be created")
            @RequestBody @Validated UserRegisterRequestDTO userDto,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            User savedUser = userService.saveUser(userDto);
            ResponseEntity<User> responseEntity = new ResponseEntity<>(savedUser, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/users/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a user", description = "Update an existing user",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated user data")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a user", description = "Delete a user by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/api/users/{id}/avatar/upload + bearer token

    @PostMapping("/{id}/avatar/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Upload user avatar", description = "Upload an avatar for a user",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar uploaded successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> uploadAvatar(
            @Parameter(description = "ID of the user for whom the avatar is uploaded")
            @PathVariable Long id,
            @Parameter(description = "Avatar file")
            @RequestParam("avatar") MultipartFile image)
            throws IOException {
        String url = userService.uploadAvatar(id, image);
        return ResponseEntity.ok(url);
    }

    // DELETE http://localhost:8080/api/users/{id}/avatar/delete?publicId=publicIdToBeDeleted + bearer token

    @DeleteMapping("/{id}/avatar/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Delete user avatar", description = "Delete a user's avatar",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "User or avatar not found")
    })
    public ResponseEntity<String> deleteAvatar(
            @Parameter(description = "ID of the user whose avatar is deleted")
            @PathVariable Long id,
            @Parameter(description = "Public ID of the avatar to be deleted")
            @RequestParam("publicId") String publicId)
            throws IOException {
        String message = userService.deleteAvatar(id, publicId);
        return ResponseEntity.ok(message);
    }

    // PUT http://localhost:8080/api/users/{id}/avatar/update + bearer token

    @PutMapping("/{id}/avatar/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Update user avatar", description = "Update an avatar for a user",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar updated successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User or avatar not found")
    })
    public ResponseEntity<String> updateAvatar(
            @Parameter(description = "ID of the user whose avatar is updated")
            @PathVariable Long id,
            @Parameter(description = "Public ID of the current avatar")
            @RequestParam("publicId") String publicId,
            @Parameter(description = "Updated avatar file")
            @RequestParam("avatar") MultipartFile updatedImage)
            throws IOException {
        String url = userService.updateImage(id, publicId, updatedImage);
        return ResponseEntity.ok(url);
    }
}
