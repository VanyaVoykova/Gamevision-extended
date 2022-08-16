package com.gamevision.web;

import com.gamevision.errorhandling.exceptions.GameNotFoundException;
import com.gamevision.errorhandling.exceptions.GameTitleExistsException;
import com.gamevision.model.binding.GameAddBindingModel;
import com.gamevision.model.binding.GameEditBindingModel;
import com.gamevision.model.enums.GenreNameEnum;
import com.gamevision.model.servicemodels.GameAddServiceModel;
import com.gamevision.model.servicemodels.GameEditServiceModel;
import com.gamevision.model.user.GamevisionUserDetails;
import com.gamevision.model.view.GameCardViewModel;
import com.gamevision.model.view.GameViewModel;
import com.gamevision.service.GameService;
import com.gamevision.service.PlaythroughService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GameController {
    private final GameService gameService;
    private final PlaythroughService playthroughService;
    private final ModelMapper modelMapper;

    public GameController(GameService gameService, PlaythroughService playthroughService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.playthroughService = playthroughService;
        this.modelMapper = modelMapper;
    }


    //Skip pagination for now?
//FIXME - fix Paging in TEMPLATE
    @GetMapping("/games/all")
    public String allGames(Model model,
                           @PageableDefault(sort = "title",
                                   direction = Sort.Direction.ASC,
                                   page = 0,
                                   size = 12) Pageable pageable) {
        Page<GameCardViewModel> allGames = gameService.getAllGames(pageable);
        if (allGames.isEmpty()) {
            model.addAttribute("noGames", "There are currently no games available.");
            return "games-all";
        }

        model.addAttribute("games", gameService.getAllGames(pageable));
        return "games-all";
    }

    //Todo: may add search and sorting by genres later, search by genre with check boxes
    //manually in URL: &sort=genre


    //TODO: admins can see an "Add a playthrough" button
    @GetMapping("/games/{id}") //Don't forget the ("id") in @PathVariable("id")
    public String gamePage(@PathVariable("id") Long id, Model model) {
        GameViewModel gameViewModel = gameService.getGameViewModelById(id);

        model.addAttribute("game", gameViewModel);
        return "game";
    }


    //For ADMINS:

    @GetMapping("/games/add")
    //Check if Model model is necessary her/or still needed below - SS takes care for UserRegistrationBindingmodel or?
    public String addGame(Model model) { //model here to add attribute below

        //Text for the genre checkboxes labels
        //   List<String> allGenres = GenreNameEnum.values().stream().collect(Collectors.toList());
        model.addAttribute("allGenres", GenreNameEnum.values());// for th: each, text to display the full name with .getName() for the checkboxes
        return "game-add";
    }

    //TODO cannot make checkboxes stay checked or be pre-checked when editing; addFlashAttribute
    @PostMapping("/games/add") //hopefully the model will keep the genres from the @GetMapping;
    //@RequestParam(required = false)  @RequestParam be null, in order to check later i/o using defaultValue = ""
    public String addGameSubmit(@RequestParam(required = false) List<String> genre, @Valid GameAddBindingModel gameAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal GamevisionUserDetails userDetails, Model model) {
        List<String> chosenGenres = genre; //need a different name from genre because it's used for the checkboxes' text
        //  if (genre != null && !genre.isEmpty()) {
        //      chosenGenres.addAll(genre); //save them up here if any genres are chosen, so we can secure them in the flash attribute
        //  }
        model.addAttribute("chosenGenres", genre); //Seems you can have model attribute and the Flash Attribute with the same name


        //TODO: Figure out how to keep selected genres with redirectAttributes, add it to games/add when figured out
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);
            redirectAttributes.addFlashAttribute("chosenGenres", chosenGenres); //TODO: try to keep selected genres, this just adds the list
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gameAddBindingModel", bindingResult);
            return "redirect:/games/add";
        }


        try {
            if (genre == null || genre.isEmpty()) { //TODO: doesn't cut it, it tries to create it without genres! It's ok with chosen genres.
                redirectAttributes.addFlashAttribute("errorMessage", "Please select at least one genre."); //or with model???
                redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel); //binding result itself shouldn't have errors, so skipped here
                return "redirect:/games/add";
            }
            gameAddBindingModel.setGenres(genre); //List<String> from the checkboxes
            gameAddBindingModel.setAddedBy(userDetails.getUsername()); //Get the username from the Principal separately, allow it to be null upon initial binding

            //List<String> playthroughTitles and List<String> playthroughVideoUrls -> to PlaythroughEntity with nested for-cycle in @Service

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel); //binding result itself shouldn't have errors, so skipped here
            redirectAttributes.addFlashAttribute("chosenGenres", genre); //TODO: try to keep selected genres - doesn't cut it
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage()); //or with model???
            System.out.println(e.getMessage());

            return "redirect:/games/add";
        }

        try {
            GameAddServiceModel gameAddServiceModel = gameService.addGame(gameAddBindingModel);
            Long gameId = gameAddServiceModel.getId(); //saves the game, returns its ID
            return "redirect:/games/" + gameId;
        } catch (GameTitleExistsException ex) {
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage()); //or with model???
            return "redirect:/games/add";
        }

    }


    @GetMapping("/games/{id}/edit")
    public String editGame(@PathVariable("id") Long id, Model model) {
      //don't catch GameNotFoundException, it has to show itself in this case
            GameViewModel gameViewModel = gameService.getGameViewModelById(id); //throws GameNotFoundException
            if (!model.containsAttribute("gameEditBindingModel")) {
                model.addAttribute("gameEditBindingModel", new GameEditBindingModel());
            }

            model.addAttribute("gameViewModel", gameViewModel);

            //All genres for the checkboxes' labels
            model.addAttribute("allGenres", GenreNameEnum.values());// for th: each, text to display the full name with .getGenreName()


            //TODO: pre-fill checkboxes for the genres
            //th if in HTML compare if genre checkbox label is equal to one of the String genres of the VM, if equal -> check the checkbox!
            //Genres come as:    private Set<GenreEntity> genres;
            List<String> chosenGenres = gameViewModel.getGenres(); //genres from the entity
            model.addAttribute("chosenGenres", chosenGenres);
            return "game-edit";

    }

    @PostMapping("/games/{id}/edit") //"genre" is the <input> name, so the request param's name has to be the same
    public String editGameSubmit(@PathVariable("id") Long id, @RequestParam(required = false) List<String> genre, GameEditBindingModel gameEditBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        List<String> chosenGenres = genre; //need a different name from genre for the flash attribute because it's used for the checkboxes' text


        //TODO: Figure out how to keep selected genres with redirectAttributes, add it to games/add when figured out
        System.out.println("javax.persistence.RollbackException: Error while committing the transaction" + "When editing other fields but NOT the title");


        try {
            if (genre == null) { //doesn't fix the problem with NO genres BUT at least we get an error message when no genre is selected
                redirectAttributes.addFlashAttribute("errorMessage", "Please select at least one genre."); //no need to add to model
                redirectAttributes.addFlashAttribute("gameEditBindingModel", gameEditBindingModel);
                // redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gameAddBindingModel", bindingResult);
                return "redirect:/games/" + id + "/edit";
            }

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("gameEditBindingModel", gameEditBindingModel); //binding result itself shouldn't have errors, so skipped here
                redirectAttributes.addFlashAttribute("chosenGenres", chosenGenres); //TODO: try to keep selected genres - doesn't cut it
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gameEditBindingModel", bindingResult);

                return "redirect:/games/" + id + "/edit";
            }

            gameEditBindingModel.setGenres(genre); //List<String> from the checkboxes

            GameEditServiceModel gameEditServiceModel = modelMapper.map(gameEditBindingModel, GameEditServiceModel.class); //not very necessary but yey for ModelMapper

            //Two possible errors: GameNotFoundException and title UNIQUENESS violation (another game with the new title exists) - thrown by gameService
            gameService.editGame(id, gameEditServiceModel); //saves the game, so it can get the gameId; then calls the PlaythroughRepo to save the playthrough with the game id, then adds the  playthrough to the saved game
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("gameEditBindingModel", gameEditBindingModel); //binding result itself shouldn't have errors, so skipped here
            redirectAttributes.addFlashAttribute("chosenGenres", chosenGenres); //TODO: try to keep selected genres - doesn't cut it
            model.addAttribute("errorMessage", e.getMessage()); //or with model???
            System.out.println(e.getMessage());

            return "redirect:/games/" + id + "/edit"; //Make sure actual id is passed here, not "redirect:/games/{id}/edit"; NOT "redirect:/games/id/edit";
        }

        return "redirect:/games/" + id;

    }

    @GetMapping("/games/{id}/delete")
    public String deleteGame(@PathVariable("id") Long id, Model model) {
        try {
            gameService.deleteGameById(id);
        } catch (GameNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage()); //or with model???
        }

        return "redirect:/games/all";
    }


    // No More @ModelAttribute, Spring Security takes care of it... or not
    //check with this just in case - seems necessary
    @ModelAttribute("gameAddBindingModel")
    public GameAddBindingModel gameAddbindingModel() {
        return new GameAddBindingModel();
    }

}
