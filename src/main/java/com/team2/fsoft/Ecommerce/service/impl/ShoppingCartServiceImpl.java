package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.repository.ShoppingCartRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Override
    public int getCount() {
        var authentication =  SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail)authentication.getPrincipal();
        var userId = user.getId();

        var shoppingcart = shoppingCartRepository.findByUserId(userId).get();
        return shoppingcart.getCount();
    }
}
