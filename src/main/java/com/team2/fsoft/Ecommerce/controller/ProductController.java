package com.team2.fsoft.Ecommerce.controller;


import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.UserLog;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductRes;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.service.ProductService;
import com.team2.fsoft.Ecommerce.utils.SequenceGeneratorService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    final ProductService productService;
    private ApplicationEventPublisher publisher;

    private  SequenceGeneratorService sequenceGenerator;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

     private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    @PreAuthorize("hasAuthority('SHOPPER')")
    @CachePut(value = "products", key = "#result.id")
    public Product create(@RequestBody ProductReq productReq) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Product product =  productService.save(productReq);
        UserLog userLog = new UserLog();
        userLog.id= sequenceGenerator.getNextValue();
        userLog.module="product";
        userLog.action="ADD";
        userLog.email= email;
        userLog.createAt = LocalDateTime.now();

        this.publisher.publishEvent(userLog);


        return  product;

    }

    @GetMapping("/{id}")
    @Cacheable(value = "products", key = "#id")
    public ProductRes GetById(@PathVariable @Positive long id) throws InterruptedException {
        System.out.println("with out cache");
       sleep(10000);
        return  productService.getById(id);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(value = "products", key = "#id")
    @PreAuthorize("hasAuthority('SHOPPER') or hasAuthority('ADMIN')")
    public  MessagesResponse Delete(@PathVariable @Positive long id) {
        return  productService.deleteById(id);
    }

    @PostMapping("/api/search")
    public PageDTO<ProductDetailResponse> GetLists(@RequestBody ApiParameter apiParameter){
        return  productService.getLists(apiParameter);
    }
}
