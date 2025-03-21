package com.authorize.userauthentication.repository;

import com.authorize.userauthentication.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


    Users findByUsername(String username);
}
