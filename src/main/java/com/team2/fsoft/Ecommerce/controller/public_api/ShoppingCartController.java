package com.team2.fsoft.Ecommerce.controller.public_api;

import com.team2.fsoft.Ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping_cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @GetMapping("/count")
    public  int getCount() {
        return  shoppingCartService.getCount();
    }
}
