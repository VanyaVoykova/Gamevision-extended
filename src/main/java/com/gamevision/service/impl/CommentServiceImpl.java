package com.gamevision.service.impl;

import com.gamevision.model.entity.CommentEntity;

import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.servicemodels.CommentAddServiceModel;
import com.gamevision.model.view.CommentViewModel;
import com.gamevision.repository.CommentRepository;

import com.gamevision.service.CommentService;
import com.gamevision.service.GameService;
import com.gamevision.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final GameService gameService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, GameService gameService, UserService userService) {
        this.commentRepository = commentRepository;
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public List<CommentViewModel> getAllCommentsForGame(Long gameId) {
        GameEntity game = gameService.getGameById(gameId); //throws GameNotFoundException

        return game.getComments().stream()
                .sorted(Comparator.comparing(CommentEntity::getDateTimeCreated)).map(this::mapCommentEntityToCommentViewModel).collect(Collectors.toList()); //sorted by date

    }

    @Override
    public CommentViewModel addComment(CommentAddServiceModel commentAddServiceModel) {

        //todo return ServiceModel instead, don't put foreign entities here
        UserEntity author = userService.findUserByUsername(commentAddServiceModel.getAuthorName());//throws UserNotFound

        //todo return ServiceModel instead, don't put foreign entities here
        GameEntity game = gameService.getGameById(commentAddServiceModel.getGameId()); //throws GameNotFound


        CommentEntity commentEntity = new CommentEntity();
        commentEntity
                .setAuthor(author)
                .setText(commentAddServiceModel.getText())
                .setLikesCounter(0)
                .setDateTimeCreated(LocalDateTime.now());

        CommentEntity savedCommentEntity = commentRepository.save(commentEntity); //use to get commentId

        game.getComments().add(savedCommentEntity);
        gameService.saveGame(game);

        return mapCommentEntityToCommentViewModel(savedCommentEntity);
    }


    private CommentViewModel mapCommentEntityToCommentViewModel(CommentEntity entity) {
        CommentViewModel commentViewModel = new CommentViewModel();
        commentViewModel
                .setId(entity.getId())
                .setAuthorUsername(entity.getAuthor().getUsername())
                .setText(entity.getText())
                .setLikesCounter(entity.getLikesCounter())
                .setDateTimeCreated(entity.getDateTimeCreated().toString());

        return commentViewModel;

    }
}
