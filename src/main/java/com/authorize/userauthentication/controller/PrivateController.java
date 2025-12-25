package com.authorize.userauthentication.controller;

import com.authorize.userauthentication.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public ResponseEntity<ResponseDto> privateRoute() {
        return ResponseEntity.ok(new ResponseDto("Private Route Accessed by Login"));
    }
}
