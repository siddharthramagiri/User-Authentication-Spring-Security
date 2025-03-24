package com.authorize.userauthentication.service;

import com.authorize.userauthentication.entity.User;
import com.authorize.userauthentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<Object> signup(User user) {
        User u = userRepository.findByUsername(user.getUsername());
        if(!Objects.isNull(u)) {
            return new ResponseEntity<>("USER " + user.getUsername() + " ALREADY EXISTED", HttpStatus.NOT_ACCEPTABLE);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.ACCEPTED);
    }

    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        if(authentication.isAuthenticated()) {
            //Generate JWT Token
            return jwtService.generateToken(user);
        }
        return "FAILED";
    }
}
