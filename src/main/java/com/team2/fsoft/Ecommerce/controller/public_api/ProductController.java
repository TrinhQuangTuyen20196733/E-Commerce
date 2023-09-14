package com.team2.fsoft.Ecommerce.controller.public_api;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.service.ProductService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping
    public MessagesResponse create(@RequestBody ProductRequest productReq) {
        return  productService.save(productReq);
    }
    @PostMapping("/GetItems")
    public  MessagesResponse GetItems(@RequestBody ApiParameter apiParameter){
        return  productService.getItems(apiParameter);
    }
    @GetMapping("/{id}")
    public  MessagesResponse GetById(@PathVariable @Positive long id) {
        return  productService.getById(id);
    }
    @DeleteMapping("/{id}")
    public  MessagesResponse Delete(@PathVariable @Positive long id) {
        return  productService.deleteById(id);
    }
}
