package com.authorize.userauthentication.controller;


import com.authorize.userauthentication.models.Users;
import com.authorize.userauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Users signUp(@RequestBody Users user) {
        System.out.println(user);
        return userService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.login(user);
    }

}
