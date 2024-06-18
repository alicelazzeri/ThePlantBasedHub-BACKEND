package it.epicode.the_plant_based_hub_backend.controllers;

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
public class CommentController {

    @Autowired
    private CommentService commentService;

    // GET http://localhost:8080/api/comments + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    public ResponseEntity<Comment> getCommentById(@PathVariable long id) {
        Comment comment = commentService.getCommentById(id);
        ResponseEntity<Comment> responseEntity = new ResponseEntity<>(comment, HttpStatus.OK);
        return responseEntity;
    }

    // GET http://localhost:8080/api/comments/recipe/{recipeId} + bearer token

    @GetMapping("/recipe/{recipeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<Comment>> getCommentsByRecipeId(@PathVariable long recipeId) {
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
    public ResponseEntity<Comment> saveComment(
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
    public ResponseEntity<Comment> updateComment(
            @PathVariable long id,
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
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }


}
