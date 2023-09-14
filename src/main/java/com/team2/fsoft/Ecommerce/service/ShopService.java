package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Shop;

public interface ShopService {
    MessagesResponse save(Shop shop);

    MessagesResponse getInfo();
}
