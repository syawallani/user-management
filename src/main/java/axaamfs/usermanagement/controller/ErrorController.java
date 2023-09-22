package axaamfs.usermanagement.controller;

import axaamfs.usermanagement.dto.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        WebResponse<String> webResponse = new WebResponse<>();
        webResponse.setError(exception.getMessage());
        log.error("Constraint Exception", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception){
        WebResponse<String> webResponse = new WebResponse<>();
        webResponse.setError(exception.getReason());
        log.error("API Exception:", exception);
        return ResponseEntity.status(exception.getStatusCode()).body(webResponse);
    }
}
