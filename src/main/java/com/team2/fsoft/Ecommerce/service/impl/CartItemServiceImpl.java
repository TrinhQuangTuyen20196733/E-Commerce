package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import com.team2.fsoft.Ecommerce.repository.CartItemRepository;
import com.team2.fsoft.Ecommerce.repository.ProductDetailRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShoppingCartRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductRepository productRepositoty;

    @Autowired
    ProductDetailRepository productDetailRepository;
    @Override
    public MessagesResponse add(CartItem cartItem) {
        MessagesResponse ms = new MessagesResponse();
        var authentication =  SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail)authentication.getPrincipal();
        var userId = user.getId();
        var shoppingCart = shoppingCartRepository.findByUserId(userId).get();
        try {
            Optional<ProductDetail> productDetail = productDetailRepository.findById(cartItem.getProduct().getId());
            if (productDetail.get().getInStock() > 0) {
                CartItem cartItem1 = new CartItem();
                cartItem1.setShoppingCart(shoppingCart);
                cartItem1.setProduct(productDetail.get().getProduct());
                cartItemRepository.save(cartItem1);
                shoppingCart.setCount(shoppingCart.getCount() + 1);
                shoppingCartRepository.save(shoppingCart);
                productDetail.get().setInStock((productDetail.get().getInStock()) - 1);
                productDetail.get().setSoldQuantity((productDetail.get().getSoldQuantity()) + 1);
                productDetailRepository.save(productDetail.get());
            } else {
                ms.code = 500;
                ms.message = " Không đủ hàng";
            }
        }
        catch (Exception e) {
            ms.code=500;
            ms.message="Không thể thêm mới item này vào giỏ hàng";
        }

        ms.data= shoppingCart.getCount();
        return ms;
    }

    @Override
    public MessagesResponse getMe() {
        MessagesResponse ms = new MessagesResponse();
        try {
            var authentication =  SecurityContextHolder.getContext().getAuthentication();
            var user = (UserDetail)authentication.getPrincipal();
            var userId = user.getId();
            var shoppingCart = shoppingCartRepository.findByUserId(userId).get();
            var list = cartItemRepository.findShoppingCartId(shoppingCart.getId()).get();
            ms.data = list;
        }
        catch (Exception e) {
            ms.code = 500;
            ms.message=" Internal Server Error";
        }
        return  ms;
    }

    @Override
    public MessagesResponse delete(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var cartItem = cartItemRepository.findById(id).get();
            ShoppingCart shoppingCart = cartItem.getShoppingCart();
            cartItemRepository.delete(cartItem);
            shoppingCart.setCount(shoppingCart.getCount()-1);
        }
        catch (Exception e) {
            ms.code = 500;
            ms.message=" Internal Server Error";
        }
        return  ms;
    }
}
