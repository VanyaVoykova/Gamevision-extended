package com.gamevision.service;

import com.gamevision.model.servicemodels.CommentAddServiceModel;
import com.gamevision.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<CommentViewModel> getAllCommentsForGame(Long gameId);
    CommentViewModel addComment(CommentAddServiceModel commentAddServiceModel);
}
