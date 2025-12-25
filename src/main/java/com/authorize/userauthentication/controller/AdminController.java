package com.authorize.userauthentication.controller;

import com.authorize.userauthentication.dto.ResponseDto;
import com.authorize.userauthentication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ResponseDto> adminRoute() {
        return ResponseEntity.ok(new ResponseDto("Admin Route Accessed with Admin Credentials"));
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<ResponseDto> makeAdmin(@PathVariable Long id) {
        return adminService.makeAdmin(id);
    }

}
