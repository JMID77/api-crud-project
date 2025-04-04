package com.api.crud.apiCrudProject.interaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    public HelloController() {
        // Constructor
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
