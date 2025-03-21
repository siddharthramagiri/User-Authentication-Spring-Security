package com.authorize.userauthentication.service;

import com.authorize.userauthentication.models.UserPrincipal;
import com.authorize.userauthentication.models.Users;
import com.authorize.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if(user == null) {
            System.out.println("USER NOT FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

        return new UserPrincipal(user);
    }
}
