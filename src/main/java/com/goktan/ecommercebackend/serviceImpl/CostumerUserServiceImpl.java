package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.cafeutils.CafeUtils;
import com.goktan.ecommercebackend.config.CostumerUsersDetailsService;
import com.goktan.ecommercebackend.config.JwtFilter;
import com.goktan.ecommercebackend.config.JwtUtil;
import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.repository.UserRepository;
import com.goktan.ecommercebackend.service.CostumerUserService;
import com.goktan.ecommercebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class CostumerUserServiceImpl  implements CostumerUserService {

    UserRepository userRepository;
    CostumerUsersDetailsService costumerUsersDetailsService;
    AuthenticationManager authenticationManager;
    JwtUtil jwtUtil;
    JwtFilter jwtFilter;

    public CostumerUserServiceImpl(UserRepository userRepository, CostumerUsersDetailsService costumerUsersDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtFilter jwtFilter) {
        this.userRepository = userRepository;
        this.costumerUsersDetailsService = costumerUsersDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtFilter = jwtFilter;
    }
    private boolean validateSignUpMap(Map<String,String> requestMap){
        if( requestMap.containsKey("name") && requestMap.containsKey("mobile")
                && requestMap.containsKey("email")  && requestMap.containsKey("password") && requestMap.containsKey("lastName")
        ){
            return  true;
        }
        return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setLastName(requestMap.get("lastName"));
        user.setMobile(requestMap.get("mobile"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setRole("user");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            if (validateSignUpMap(requestMap)){
                User user = userRepository.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userRepository.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully registered.", HttpStatus.OK);
                }else {
                    return CafeUtils.getResponseEntity("Email already exits.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity("Invalid data", HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> login(Map<String,String> requestMap) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));

            if (auth.isAuthenticated()){
                return new ResponseEntity<String>("{\"token\":\""+
                        jwtUtil.generateToken(costumerUsersDetailsService.getUserDetail().getEmail(),costumerUsersDetailsService.getUserDetail().getRole()+"\"}"),
                        HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("{}",e);
        }
        return  new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}"
                ,HttpStatus.BAD_REQUEST);
    }
}
