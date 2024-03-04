package com.gamevision.web.rest;

import com.gamevision.errorhandling.exceptions.GameNotFoundException;
import com.gamevision.errorhandling.exceptions.UserNotFoundException;
import com.gamevision.errors.ErrorApiResponse;
import com.gamevision.model.binding.CommentBindingModel;
import com.gamevision.model.servicemodels.CommentAddServiceModel;
import com.gamevision.model.view.CommentViewModel;
import com.gamevision.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //comments/forum - very basic version for general comments, no thread/topic creation,
@RequestMapping("/api")
public class CommentRestController {
    private final CommentService commentService;


    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/games/{gameId}/comments")
    public ResponseEntity<List<CommentViewModel>> getCommentsForGame(@PathVariable("gameId") Long gameId) {

        return ResponseEntity.ok(commentService.getAllCommentsForGame(gameId)); //sorted by LocalDateTime in the service
        //can be found on /api/{gameId}/comments as JSON objects
    }

    @PostMapping(value = "/games/{gameId}/comments")
    //the post actually comes games/id and not games/id/comments (there's the get)

    public ResponseEntity<CommentViewModel> createComment(@PathVariable("gameId") Long gameId,
                                                          @AuthenticationPrincipal UserDetails userDetails, @Validated @RequestBody CommentBindingModel commentAddBindingModel) {
//The only validation we have to do for the binding model is the text length, which for this specific case is also done in the HTML as well, so should be ok

        CommentAddServiceModel commentAddServiceModel = new CommentAddServiceModel()
                .setAuthorName(userDetails.getUsername())
                .setGameId(gameId)
                .setText(commentAddBindingModel.getText());

        CommentViewModel createdComment = commentService.addComment(commentAddServiceModel);//throws UserNotFound and GameNotFound

//No try-catch here, see  @ExceptionHandler - one for each GameNotFound and UserNotFound
        return ResponseEntity
                .created(URI.create(String.format("/api/games/%d/comments/%d", gameId, createdComment.getId()))) //local URL with /comments
                .body(createdComment); //this URI is important, needs the id of the created comment

    }

    @ExceptionHandler({GameNotFoundException.class}) //overrides the response with custom ErrorApiResponse
    public ResponseEntity<ErrorApiResponse> handleGameNotFound() {
        return ResponseEntity.status(404).body(new ErrorApiResponse("Game not found!", 1004));
    }

    @ExceptionHandler({UserNotFoundException.class}) //overrides the response with custom ErrorApiResponse
    public ResponseEntity<ErrorApiResponse> handleUserNotFound() {
        return ResponseEntity.status(404).body(new ErrorApiResponse("User not found!", 1004));
    }
}
