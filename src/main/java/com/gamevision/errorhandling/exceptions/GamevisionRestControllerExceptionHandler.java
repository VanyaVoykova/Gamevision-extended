package com.gamevision.errorhandling.exceptions;

import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@Order(2)
@ControllerAdvice(annotations = RestController.class) //for regular Controllers
public class GamevisionRestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> RuntimeExceptionHandler(RuntimeException e) {

        JSONObject response = new JSONObject();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
    }
}
