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

    //Just uncouple PlaythroughEntity from GameEntity @Lazy PlaythroughService to avoid cycling dependency problem (encountered while trying to delete a PlaythroughEntity)
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, GenreRepository genreRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    //Skip pagination for now
    //@Cacheable("games") //disabled in order to show add game functionality, or added game won't be displayed right away
    @Override
    public Page<GameCardViewModel> getAllGames(Pageable pageable) { //Page, not list!!!
        //TODO: check if Limit description length for game cards works correctly
        //no stream, no collect to list, returning Page instead
        return gameRepository.findAll(pageable)
                .map(this::mapGameEntityToCardView);
    }

    //Get 7 random games for the Home page
    // @Cacheable("carouselGames")
    @Override
    public List<GameCarouselViewModel> getGamesForCarousel() {
        List<GameCardViewModel> allGamesCardViews = gameRepository.findAll().stream().map(this::mapGameEntityToCardView).collect(Collectors.toList());
        if (allGamesCardViews.isEmpty()) {
            return null; //so controller knows there are no games right away and we can skip the rest of the operations
        }

        Collections.shuffle(allGamesCardViews); //shuffle the indexes
        int maxIndex = Math.min(10, allGamesCardViews.size()); //last index excluded, so it should take all the way to the last if games are fewer; 10 games
        List<GameCardViewModel> gamesOfTheWeekCardViews = allGamesCardViews.subList(0, maxIndex);


        //no time to map entity directly to carousel item, which needs just one more field to mark the first item for the necessary "active" attribute
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
        //Check if title doesn't already exist - must be unique!
        GameEntity existingGameWithTitle = gameRepository.findByTitle(gameAddbindingModel.getTitle()).orElse(null);
        if (existingGameWithTitle != null) { //game with that name EXISTS
            throw new GameTitleExistsException(); //"A game with that title already exists."
        }

        UserEntity addedByUser = userRepository.findByUsername(gameAddbindingModel.getAddedBy()).orElseThrow(UserNotFoundException::new);

        GameEntity gameToAdd = modelMapper.map(gameAddbindingModel, GameEntity.class); //maps it alright

        //todo test new lines
        //   gameToAdd.setDescription(String.format(gameAddbindingModel.getDescription()).replaceAll(""));

        gameToAdd.setAddedBy(addedByUser);

        //List<String> in SM -> Set<GenreEntity> in GameEntity; list in SM never empty
        Set<GenreEntity> genres = new LinkedHashSet<>(); //LHS to keep them ordered as they appear in the enum for consistency - easy to compare games visually
        for (String genreName : gameAddbindingModel.getGenres()) {
            GenreEntity genreEntity = genreRepository.findByName(GenreNameEnum.valueOf(genreName)); //entity's name is GenreNameEnum - RPG(Role-playing), ....
            genres.add(genreEntity);
        }

        gameToAdd.setGenres(genres); //added all at once
        //ADD EMPTY COLLECTIONS or they are null, MM won't initialize empty collections!!!
        gameToAdd.setPlaythroughs(new HashSet<>());
        gameToAdd.setComments(new LinkedHashSet<>()); //Linked to keep order of addition

        GameEntity addedGameFromRepo = gameRepository.save(gameToAdd); //shouldn't throw unless DB  is down... then error.html should show itself

        return new GameAddServiceModel()
                .setId(addedGameFromRepo.getId())
                .setTitle(addedGameFromRepo.getTitle())
                .setAddedBy(addedGameFromRepo.getAddedBy().getUsername()); //it's not null here, holds the id and all but the controller still blows up?!?
    }

    @Override //Doesn't include playthroughs
    public void editGame(Long gameId, GameEditServiceModel gameEditServiceModel) {
        //We need the id!!
        // 1. Pull orignal GameEntity to be edited from the repo
        GameEntity gameToEdit = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        // 2. check the new title - if another gameEntity (with ANOTHER id) does not have it in order to keep titles UNIQUE
        GameEntity existingGameWithSameTitleAsTheNewTitle = gameRepository.findByTitle(gameEditServiceModel.getTitle()).orElse(null); //if null -> OK, proceed

        //Ensure it's a DIFFERENT game, so you don't get "A game with that title already exists." because you found the game you want to edit (same id).
        if (existingGameWithSameTitleAsTheNewTitle != null && !Objects.equals(existingGameWithSameTitleAsTheNewTitle.getId(), gameToEdit.getId())) {
            throw new GameTitleExistsException(); //has static final message
        }

        //Clear to go, set new fields
        gameToEdit.setTitle(gameEditServiceModel.getTitle())
                .setTitleImageUrl(gameEditServiceModel.getTitleImageUrl())
                .setDescription(gameEditServiceModel.getDescription());

        gameToEdit.getGenres().clear(); //Clear the existing genres first //entity getters don't return unmodifiable collections AFAIK
        for (String genreName : gameEditServiceModel.getGenres()) {
            GenreEntity genreEntity = genreRepository.findByName(GenreNameEnum.valueOf(genreName)); //entity's name is GenreNameEnum - RPG(Role-playing), ....
            gameToEdit.getGenres().add(genreEntity);
        }

        gameRepository.save(gameToEdit); //don't forget to update the entity
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

    @Override //good practice to not have another service work directly with a repo not holding its entity
    public GameEntity saveGame(GameEntity gameEntity) {

        return gameRepository.save(gameEntity);
    }

    @Override
    public void deleteGameById(Long id) {

        GameEntity entityToDelete = gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
        entityToDelete.getPlaythroughs().clear(); //delete all playthroughs to ensure none are left orphaned
        gameRepository.save(entityToDelete); //update
        gameRepository.deleteById(id);
    }

    @Override//Careful,avoid cyclic dependency
    public void removePlaythroughFromGameByGameIdAndPlaythroughId(Long gameId, Long playthroughId) {
        GameEntity gameToLoseAPlaythrough = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        PlaythroughEntity playthroughToRemove = gameToLoseAPlaythrough.getPlaythroughs().stream().filter(playthrough -> playthrough.getId().equals(playthroughId)).findFirst().get();
        gameToLoseAPlaythrough.getPlaythroughs().remove(playthroughToRemove); //removed from the GameEntity's Set<PlaythroughEntity>
        gameRepository.save(gameToLoseAPlaythrough); //update game
    }

    @Override
    public GameCardViewModel mapGameEntityToCardView(GameEntity gameEntity) {
        GameCardViewModel gameCardView = modelMapper.map(gameEntity, GameCardViewModel.class);
        List<String> genresAsStrings = gameEntity.getGenres()
                .stream()
                .map(genreEntity -> genreEntity.getName().getGenreName())
                .collect(Collectors.toList());
        gameCardView.setGenres(genresAsStrings);

        //GenreEntity to List<String>


        //Prevent out of bounds when description length is shorter
        int maxLength = Math.min(gameEntity.getDescription().length(), 400); //limit shortened description to 400 chars

        //ViewModel's .getDescription() will be null when pre-mapping, get it from the entity and set it separately
        String rawShortDescriptionCut = gameEntity.getDescription().substring(0, maxLength);
        //for testing purposes, there may be gibberish with no white spaces, so in this case we can't use white space as a reference where to cut the String
        //System.out.println(rawShortDescriptionCut.lastIndexOf(" "));
        if (rawShortDescriptionCut.lastIndexOf(" ") == -1) { //-1 if there are no intervals
            gameCardView.setDescription(rawShortDescriptionCut + "..."); //goal is to avoid cutting mid-word
        } else { //there are intervals
            String shortDescriptionCutAtLastWhitespace = rawShortDescriptionCut.substring(0, rawShortDescriptionCut.lastIndexOf(" ")) + "...";
            gameCardView.setDescription(shortDescriptionCutAtLastWhitespace); //goal is to avoid cutting mid-word
        }

        return gameCardView;
    }


}
