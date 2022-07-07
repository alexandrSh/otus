package ru.otus.helloworld.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.helloworld.models.Error;

@Slf4j
@RestControllerAdvice(assignableTypes = WebController.class)
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Error> handleException(Exception ex) {
        log.error("handleException: ", ex);
        final Error apiError = new Error()
            .message(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(apiError);
    }
}
