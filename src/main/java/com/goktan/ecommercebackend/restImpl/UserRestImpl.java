package com.goktan.ecommercebackend.restImpl;

import com.goktan.ecommercebackend.cafeutils.CafeUtils;
import com.goktan.ecommercebackend.rest.UserRest;
import com.goktan.ecommercebackend.serviceImpl.CostumerUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class UserRestImpl implements UserRest {
   @Autowired
    CostumerUserServiceImpl costumerUserService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return costumerUserService.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return costumerUserService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
