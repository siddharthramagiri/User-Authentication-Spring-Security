package com.authorize.userauthentication.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ApplicationController {

    @GetMapping("/")
    public static String Empty() {
        return "Empty Page routing to ('/') ";
    }
}
