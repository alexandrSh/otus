package ru.otus.helloworld.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping(path = "/health")
    public ResponseEntity<Result> status() {
        return ResponseEntity.ok(new Result("OK"));
    }
}
