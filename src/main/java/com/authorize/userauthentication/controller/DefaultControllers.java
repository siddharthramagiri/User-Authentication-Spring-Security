package com.authorize.userauthentication.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DefaultControllers {

    @GetMapping("/")
    public String home() {
        return "Page Opened Route ('/')";
    }

    @GetMapping("/products")
    public String products() {
        return "Page Route ('/products')";
    }
}
