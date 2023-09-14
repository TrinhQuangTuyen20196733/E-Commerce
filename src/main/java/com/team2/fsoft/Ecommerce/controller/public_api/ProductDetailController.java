package com.team2.fsoft.Ecommerce.controller.public_api;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_detail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @PutMapping("/updateInfo")
    public MessagesResponse updateProductDetail(@RequestBody ProductDetailRequest productDetail, @RequestParam long id) {
        return productDetailService.updateInfo(productDetail, id);
    }

    @GetMapping("/showInfo")
    public MessagesResponse updateProductDetail(@RequestParam long id ) {
        return productDetailService.showInfo(id);
    }
}
