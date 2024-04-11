package com.goktan.ecommercebackend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CostumerUserService {

    ResponseEntity<String> signUp(Map<String,String> requestMap);
    ResponseEntity<String> login(Map<String,String> requestMap);
}
