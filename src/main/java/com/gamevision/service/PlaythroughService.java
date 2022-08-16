package com.gamevision.service;

import com.gamevision.model.binding.PlaythroughAddBindingModel;
import com.gamevision.model.entity.PlaythroughEntity;
import com.gamevision.model.view.PlaythroughViewModel;

import java.util.List;

public interface PlaythroughService {
    void addPlaythrough(Long gameId, PlaythroughAddBindingModel playthroughAddBindingModel, String username);

    // PlaythroughEntity addPlaythroughWhenAddingGame(Long gameId, String title, String videoUrl, String description, String username);

    List<PlaythroughViewModel> getAllPlaythroughsForGame(Long gameId);

// void deletePlaythroughByGameIdAndPlaythroughId(Long gameId, Long playthroughId);

    void deletePlaythroughById(Long id);

    PlaythroughEntity getPlaythroughById(Long id);
}
