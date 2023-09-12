package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/test")
    public String  test(@RequestBody @Valid  User user) {
    return "OK";
    }
}
