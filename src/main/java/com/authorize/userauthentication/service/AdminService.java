package com.authorize.userauthentication.service;

import com.authorize.userauthentication.dto.ResponseDto;
import com.authorize.userauthentication.entity.User;
import com.authorize.userauthentication.entity.type.RoleType;
import com.authorize.userauthentication.exceptionHandler.exceptions.UserNotFoundException;
import com.authorize.userauthentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public ResponseEntity<ResponseDto> makeAdmin(Long id) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with Id " + id + " not found");
        }
        User user = optionalUser.get();
        user.getRoles().add(RoleType.ADMIN);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseDto("User " + id + " is now Admin"));
    }
}
