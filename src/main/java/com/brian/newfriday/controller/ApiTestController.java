package com.brian.newfriday.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiTestController {
    @GetMapping("/hello")
    public String apiTest(){
        System.out.println("helly");
        return "helly";
    }
}