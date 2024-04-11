package com.goktan.ecommercebackend.repository;

import com.goktan.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailId(@Param("email") String email);
}
