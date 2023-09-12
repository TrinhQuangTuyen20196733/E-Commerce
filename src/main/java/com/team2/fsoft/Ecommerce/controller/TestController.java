package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.LoginRequest;
import com.team2.fsoft.Ecommerce.dto.response.LoginResponse;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.security.JWTService;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/user")
    public String test() {
        return "user";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
