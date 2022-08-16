package com.gamevision.web;

import com.gamevision.model.view.GameCarouselViewModel;
import com.gamevision.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    private final GameService gameService;

    public HomeController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<GameCarouselViewModel> carouselOfTheWeek = gameService.getGamesForCarousel();
        if (carouselOfTheWeek == null) { //service returns null if empty
            modelAndView.addObject("noGames", "I have no time for games.");
            modelAndView.addObject("quoteAuthor", "- Sylvanas Windrunner, Warcraft");
        } else {
            modelAndView.addObject("carouselGames", carouselOfTheWeek);
        }

        return modelAndView;
    }
}
