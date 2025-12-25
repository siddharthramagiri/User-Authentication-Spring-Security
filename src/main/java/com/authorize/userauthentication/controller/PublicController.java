package com.authorize.userauthentication.controller;

import com.authorize.userauthentication.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping
    public ResponseEntity<ResponseDto> publicRoute() {
        return ResponseEntity.ok(new ResponseDto("Public Route Accessible to everyone"));
    }
}
