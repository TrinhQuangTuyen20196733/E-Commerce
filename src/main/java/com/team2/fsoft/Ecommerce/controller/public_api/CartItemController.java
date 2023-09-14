package com.team2.fsoft.Ecommerce.controller.public_api;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;
import com.team2.fsoft.Ecommerce.service.CartItemService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart_item")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;

    @PostMapping()
    public MessagesResponse addToCart(@RequestBody CartItem cartItem) {
        return cartItemService.add(cartItem);
    }
    @GetMapping("/GetMe")
    public  MessagesResponse getMe() {
        return cartItemService.getMe();
    }

    @DeleteMapping("/{id}")
    public MessagesResponse delete(@PathVariable @Positive Long id) {
        return  cartItemService.delete(id);
    }


}
