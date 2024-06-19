package it.epicode.the_plant_based_hub_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.the_plant_based_hub_backend.entities.Comment;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.NoContentException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.CommentRequestDTO;
import it.epicode.the_plant_based_hub_backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
@Tag(name = "Comment API", description = "Operations related to comments")

public class CommentController {

    @Autowired
    private CommentService commentService;

    // GET http://localhost:8080/api/comments + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get all comments", description = "Retrieve all comments",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of comments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No comments found")
    })
    public ResponseEntity<Page<Comment>> getAllComments(Pageable pageable) {
        Page<Comment> comments = commentService.getAllComments(pageable);
        if (comments.isEmpty()) {
            throw new NoContentException("No comments are present in this list");
        } else {
            ResponseEntity<Page<Comment>> responseEntity = new ResponseEntity<>(comments, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/comments/{id} + bearer token

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get comment by ID", description = "Retrieve a comment by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Comment> getCommentById(@Parameter(description = "ID of the comment to be retrieved") @PathVariable long id) {
        Comment comment = commentService.getCommentById(id);
        ResponseEntity<Comment> responseEntity = new ResponseEntity<>(comment, HttpStatus.OK);
        return responseEntity;
    }

    // GET http://localhost:8080/api/comments/recipe/{recipeId} + bearer token

    @GetMapping("/recipe/{recipeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get comments by recipe ID", description = "Retrieve comments by recipe ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "204", description = "No comments found")
    })
    public ResponseEntity<List<Comment>> getCommentsByRecipeId(@Parameter(description = "ID of the recipe to retrieve comments for") @PathVariable long recipeId) {
        List<Comment> comments = commentService.getCommentsByRecipeId(recipeId);
        if (comments.isEmpty()) {
            throw new NoContentException("No comments found for the given recipe.");
        } else {
            ResponseEntity<List<Comment>> responseEntity = new ResponseEntity<>(comments, HttpStatus.OK);
            return responseEntity;
        }
    }

    // POST http://localhost:8080/api/comments + bearer token

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Create a new comment", description = "Create a new comment",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Comment> saveComment(
            @Parameter(description = "Comment data to be created")
            @RequestBody @Validated CommentRequestDTO commentPayload,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Comment savedComment = commentService.saveComment(commentPayload);
            ResponseEntity<Comment> responseEntity = new ResponseEntity<>(savedComment, HttpStatus.CREATED);
            return responseEntity;
        }
    }

    // PUT http://localhost:8080/api/comments/{id} + bearer token

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Update a comment", description = "Update an existing comment",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Comment> updateComment(
            @Parameter(description = "ID of the comment to be updated")
            @PathVariable long id,
            @Parameter(description = "Updated comment data")
            @RequestBody @Validated CommentRequestDTO updatedComment,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Comment commentToBeUpdated = commentService.updateComment(id, updatedComment);
            ResponseEntity<Comment> responseEntity = new ResponseEntity<>(commentToBeUpdated, HttpStatus.OK);
            return responseEntity;
        }
    }

    // DELETE http://localhost:8080/api/comments/{id} + bearer token

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Delete a comment", description = "Delete a comment by ID",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Void> deleteComment( @Parameter(description = "ID of the comment to be deleted") @PathVariable long id) {
        commentService.deleteComment(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }


}
