package com.team2.fsoft.Ecommerce.controller.shopper;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Shop;
import com.team2.fsoft.Ecommerce.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopper")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @PostMapping()
    MessagesResponse createShop(@RequestBody Shop shop){
        return shopService.save(shop);
    }
    @GetMapping("/getInfo")
    MessagesResponse GetInfo(){
        return  shopService.getInfo();
    }

}