package it.epicode.the_plant_based_hub_backend.services;

import it.epicode.the_plant_based_hub_backend.entities.Comment;
import it.epicode.the_plant_based_hub_backend.entities.Recipe;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.entities.CommentRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    // GET all comments

    @Transactional(readOnly = true)
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    // GET comment by ID

    @Transactional(readOnly = true)
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Comment with ID: " + id + " not found."));
    }

    // POST saving comment

    @Transactional
    public Comment saveComment(CommentRequestDTO commentPayload) {
        Comment comment = mapToEntity(commentPayload);
        return commentRepository.save(comment);
    }

    // PUT updating comment

    @Transactional
    public Comment updateComment(long id, CommentRequestDTO updatedComment) {
        Comment commentToBeUpdated = this.getCommentById(id);
        if (commentToBeUpdated == null) {
            throw new NotFoundException("Comment with id: " + id + " not found.");
        } else {
            updateCommentFromDTO(commentToBeUpdated, updatedComment);
            return commentRepository.save(commentToBeUpdated);
        }
    }

    // DELETE comment

    @Transactional
    public void deleteComment(long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment with id: " + id + " not found.");
        } else {
            commentRepository.deleteById(id);
        }
    }

    // Map CommentDTO to Comment entity (converts CommentDTO to a Comment entity instance in order to save or
    // update data on db via CommentRepository)

    public Comment mapToEntity(CommentRequestDTO commentRequestDTO) {
        Recipe recipe = recipeService.getRecipeById(commentRequestDTO.recipeId());
        User user = userService.getUserByIdOrThrow(commentRequestDTO.userId());
        Comment comment = Comment.builder()
                .withRecipeRating(commentRequestDTO.recipeRating())
                .withCommentText(commentRequestDTO.commentText())
                .withUser(user)
                .withRecipe(recipe)
                .build();
        return comment;
    }

    // update already existing comment from CommentDTO

    public void updateCommentFromDTO(Comment existingComment, CommentRequestDTO commentRequestDTO) {
        Recipe recipe = recipeService.getRecipeById(commentRequestDTO.recipeId());
        User user = userService.getUserByIdOrThrow(commentRequestDTO.userId());
        existingComment.setRecipeRating(commentRequestDTO.recipeRating());
        existingComment.setCommentText(commentRequestDTO.commentText());
        existingComment.setUser(user);
        existingComment.setRecipe(recipe);
    }

    // GET comments by recipe id

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByRecipeId(long recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }

}
