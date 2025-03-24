package com.authorize.userauthentication.repository;

import com.authorize.userauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
