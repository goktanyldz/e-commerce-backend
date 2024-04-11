package com.goktan.ecommercebackend.service;

import com.goktan.ecommercebackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public interface UserService {

    public User findUserById(Long userId) throws UsernameNotFoundException ;
}
