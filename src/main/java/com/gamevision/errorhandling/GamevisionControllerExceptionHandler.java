package com.gamevision.errorhandling;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Order(1)
@ControllerAdvice(annotations = Controller.class) //for regular Controllers
public class GamevisionControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND) //can't be always that, but heh
    public ModelAndView onObjectNotFound(Exception ex) {
        { //try without model but with MVC
            ModelAndView modelAndView = new ModelAndView("error");
            //modelAndView.setView("error.html");
            modelAndView.addObject("errorCause", ex.getCause());
            modelAndView.addObject("errorMessage", ex.getMessage());
            System.out.println(ex.getMessage());
            return modelAndView;
        }
    }
}