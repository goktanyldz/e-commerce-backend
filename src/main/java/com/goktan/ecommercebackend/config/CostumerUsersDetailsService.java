package com.goktan.ecommercebackend.config;

import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CostumerUsersDetailsService  implements UserDetailsService {
    UserRepository userRepository;

    public CostumerUsersDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private User userDetail;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = userRepository.findByEmailId(username);
        if (!Objects.isNull(userDetail)){
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
        }else throw new UsernameNotFoundException("user not found");
    }

    public User getUserDetail(){
        return userDetail;
    }
}
