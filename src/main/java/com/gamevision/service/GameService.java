package com.gamevision.service;

import com.gamevision.model.binding.GameAddBindingModel;
import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.servicemodels.GameAddServiceModel;
import com.gamevision.model.servicemodels.GameEditServiceModel;
import com.gamevision.model.view.GameCardViewModel;
import com.gamevision.model.view.GameCarouselViewModel;
import com.gamevision.model.view.GameViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {
    Page<GameCardViewModel> getAllGames(Pageable pageable);

    List<GameCarouselViewModel> getGamesForCarousel();

    void refreshCache();

    void refreshCarouselCache();

    GameAddServiceModel addGame(GameAddBindingModel gameAddBindingModel);

    void editGame(Long gameId, GameEditServiceModel gameEditServiceModel);

    GameEntity getGameByTitle(String gameTitle);

    Long getGameIdByTitle(String gameTitle);

    GameViewModel getGameViewModelById(Long id);

    String getGameTitleById(Long id);

    GameEntity getGameById(Long id);

    GameEntity saveGame(GameEntity gameEntity);

    void deleteGameById(Long id);


    void removePlaythroughFromGameByGameIdAndPlaythroughId(Long gameId, Long playthroughId);

    GameCardViewModel mapGameEntityToCardView(GameEntity gameEntity);

    // void removePlaythroughFromPlaythroughsByGameIdAndPlaythroughId(Long gameId, Long playthroughId);

}
