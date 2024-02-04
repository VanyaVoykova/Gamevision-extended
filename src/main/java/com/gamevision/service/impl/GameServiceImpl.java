package com.gamevision.service.impl;

import com.gamevision.errorhandling.exceptions.GameNotFoundException;
import com.gamevision.errorhandling.exceptions.GameTitleExistsException;
import com.gamevision.errorhandling.exceptions.UserNotFoundException;
import com.gamevision.model.binding.GameAddBindingModel;
import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.GenreEntity;
import com.gamevision.model.entity.PlaythroughEntity;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.enums.GenreNameEnum;
import com.gamevision.model.servicemodels.GameAddServiceModel;
import com.gamevision.model.servicemodels.GameEditServiceModel;
import com.gamevision.model.view.GameCardViewModel;
import com.gamevision.model.view.GameCarouselViewModel;
import com.gamevision.model.view.GameViewModel;
import com.gamevision.repository.GameRepository;
import com.gamevision.repository.GenreRepository;
import com.gamevision.repository.UserRepository;
import com.gamevision.service.GameService;
import com.gamevision.service.PlaythroughService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, GenreRepository genreRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<GameCardViewModel> getAllGames(Pageable pageable) {
        return gameRepository.findAll(pageable)
                .map(this::mapGameEntityToCardView);
    }

    @Override
    public List<GameCarouselViewModel> getGamesForCarousel() {
        List<GameCardViewModel> allGamesCardViews = gameRepository.findAll().stream().map(this::mapGameEntityToCardView).collect(Collectors.toList());

        if (allGamesCardViews.isEmpty()) {
            return null;
        }

        Collections.shuffle(allGamesCardViews);
        int maxIndex = Math.min(10, allGamesCardViews.size()); //last index excluded, so it should take all the way to the last if size < 10
        List<GameCardViewModel> gamesOfTheWeekCardViews = allGamesCardViews.subList(0, maxIndex);

        List<GameCarouselViewModel> carouselGames = new ArrayList<>();

        for (GameCardViewModel cardView : gamesOfTheWeekCardViews) {
            GameCarouselViewModel carouselView = new GameCarouselViewModel()
                    .setId(cardView.getId())
                    .setTitle(cardView.getTitle())
                    .setTitleImageUrl(cardView.getTitleImageUrl())
                    .setDescription(cardView.getDescription())
                    .setFirst(false);

            carouselGames.add(carouselView);
        }

        carouselGames.get(0).setFirst(true);

        return carouselGames;
    }

    //todo uncomment to activate caching
    //Clear all games cache
    // @CacheEvict(cacheNames = "games", allEntries = true)
    @Override
    public void refreshCache() {

    }

    //todo uncomment to activate caching
//Clear Home page carousel cache
    //@CacheEvict(cacheNames = "carouselGames", allEntries = true)
    @Override
    public void refreshCarouselCache() { //for the Home page carousel
    }


    @Override
    public GameAddServiceModel addGame(GameAddBindingModel gameAddbindingModel) {
        GameEntity existingGameWithTitle = gameRepository.findByTitle(gameAddbindingModel.getTitle()).orElse(null);

        if (existingGameWithTitle != null) { //game with that name EXISTS
            throw new GameTitleExistsException(); //"A game with that title already exists."
        }

        UserEntity addedByUser = userRepository.findByUsername(gameAddbindingModel.getAddedBy()).orElseThrow(UserNotFoundException::new);
        GameEntity gameToAdd = modelMapper.map(gameAddbindingModel, GameEntity.class); //maps it alright

        gameToAdd.setAddedBy(addedByUser);

        //List<String> in Service Model -> Set<GenreEntity> in GameEntity; list in SM never empty
        Set<GenreEntity> genres = new LinkedHashSet<>(); //LHS to keep them ordered as they appear in the enum for consistency - easy to compare games visually
        for (String genreName : gameAddbindingModel.getGenres()) {
            GenreEntity genreEntity = genreRepository.findByName(GenreNameEnum.valueOf(genreName)); //entity's name is GenreNameEnum - RPG(Role-playing), ....
            genres.add(genreEntity);
        }

        gameToAdd.setGenres(genres);
        //ADD EMPTY COLLECTIONS or they are null, MM won't initialize empty collections!!!
        gameToAdd.setPlaythroughs(new HashSet<>());
        gameToAdd.setComments(new LinkedHashSet<>()); //Linked to keep order of addition

        GameEntity addedGameFromRepo = gameRepository.save(gameToAdd); //shouldn't throw unless DB  is down... then error.html should show itself

        return new GameAddServiceModel()
                .setId(addedGameFromRepo.getId())
                .setTitle(addedGameFromRepo.getTitle())
                .setAddedBy(addedGameFromRepo.getAddedBy().getUsername());
    }

    @Override //Doesn't include playthroughs
    public void editGame(Long gameId, GameEditServiceModel gameEditServiceModel) {

        GameEntity gameToEdit = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        GameEntity existingGameWithSameTitleAsTheNewTitle = gameRepository.findByTitle(gameEditServiceModel.getTitle()).orElse(null); //if null -> OK, proceed

        //Ensure it's a DIFFERENT game, so you don't get "A game with that title already exists." because you found the game you want to edit (same id).
        if (existingGameWithSameTitleAsTheNewTitle != null && !Objects.equals(existingGameWithSameTitleAsTheNewTitle.getId(), gameToEdit.getId())) {
            throw new GameTitleExistsException(); //has static final message
        }

        //Clear to go, set new fields
        gameToEdit.setTitle(gameEditServiceModel.getTitle())
                .setTitleImageUrl(gameEditServiceModel.getTitleImageUrl())
                .setDescription(gameEditServiceModel.getDescription());

        gameToEdit.getGenres().clear();
        for (String genreName : gameEditServiceModel.getGenres()) {
            GenreEntity genreEntity = genreRepository.findByName(GenreNameEnum.valueOf(genreName)); //entity's name is GenreNameEnum - RPG(Role-playing), ....
            gameToEdit.getGenres().add(genreEntity);
        }

        gameRepository.save(gameToEdit);
    }

    @Override
    public GameEntity getGameByTitle(String gameTitle) {
        return gameRepository.findByTitle(gameTitle).orElseThrow(GameNotFoundException::new);
    }

    @Override
    public Long getGameIdByTitle(String gameTitle) {
        GameEntity game = gameRepository.findByTitle(gameTitle).orElseThrow(GameNotFoundException::new);
        return game.getId();
    }

    @Override
    public GameViewModel getGameViewModelById(Long id) {
        GameEntity gameEntity = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        GameViewModel gameViewModel = modelMapper.map(gameEntity, GameViewModel.class);

        //Map the Set<GenreEntity> to a List<String> for the view model
        List<String> genresAsStrings = gameEntity.getGenres()
                .stream()
                .map(genreEntity -> genreEntity.getName().getGenreName())
                .collect(Collectors.toList());

        gameViewModel.setGenres(genresAsStrings);

        return gameViewModel;


    }

    @Override
    public String getGameTitleById(Long id) {
        GameEntity game = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        return game.getTitle();
    }

    @Override
    public GameEntity getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
    }

    @Override
    public GameEntity saveGame(GameEntity gameEntity) {

        return gameRepository.save(gameEntity);
    }

    @Override
    public void deleteGameById(Long id) {

        GameEntity entityToDelete = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        entityToDelete.getPlaythroughs().clear(); //delete all playthroughs to ensure none are left orphaned
        gameRepository.save(entityToDelete);
        gameRepository.deleteById(id);
    }

    @Override//Careful,avoid cyclic dependency
    public void removePlaythroughFromGameByGameIdAndPlaythroughId(Long gameId, Long playthroughId) {
        GameEntity gameToLoseAPlaythrough = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        PlaythroughEntity playthroughToRemove = gameToLoseAPlaythrough.getPlaythroughs().stream().filter(playthrough -> playthrough.getId().equals(playthroughId)).findFirst().get();
        gameToLoseAPlaythrough.getPlaythroughs().remove(playthroughToRemove); //removed from the GameEntity's Set<PlaythroughEntity>
        gameRepository.save(gameToLoseAPlaythrough);
    }

    @Override
    public GameCardViewModel mapGameEntityToCardView(GameEntity gameEntity) {
        GameCardViewModel gameCardView = modelMapper.map(gameEntity, GameCardViewModel.class);
        List<String> genresAsStrings = gameEntity.getGenres()
                .stream()
                .map(genreEntity -> genreEntity.getName().getGenreName())
                .collect(Collectors.toList());
        gameCardView.setGenres(genresAsStrings);


        //Prevent out of bounds when description length is shorter
        int maxLength = Math.min(gameEntity.getDescription().length(), 400);

        //ViewModel's .getDescription() will be null when pre-mapping, get it from the entity and set it separately
        String rawShortDescriptionCut = gameEntity.getDescription().substring(0, maxLength);

        if (rawShortDescriptionCut.lastIndexOf(" ") == -1) { //-1 if there are no intervals
            gameCardView.setDescription(rawShortDescriptionCut + "...");
        } else { //there are intervals
            String shortDescriptionCutAtLastWhitespace = rawShortDescriptionCut.substring(0, rawShortDescriptionCut.lastIndexOf(" ")) + "...";
            gameCardView.setDescription(shortDescriptionCutAtLastWhitespace); //goal is to avoid cutting mid-word
        }

        return gameCardView;
    }


}
