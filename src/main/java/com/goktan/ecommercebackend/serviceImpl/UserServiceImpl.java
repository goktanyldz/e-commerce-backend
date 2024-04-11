package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.cafeutils.CafeUtils;
import com.goktan.ecommercebackend.config.CostumerUsersDetailsService;
import com.goktan.ecommercebackend.config.JwtFilter;
import com.goktan.ecommercebackend.config.JwtUtil;
import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.repository.UserRepository;
import com.goktan.ecommercebackend.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService  {

    UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User findUserById(Long userId) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }

        throw new UsernameNotFoundException("user not found with by id");
    }



}
