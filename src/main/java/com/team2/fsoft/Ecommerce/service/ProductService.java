package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Product;

public interface ProductService {
    MessagesResponse save(ProductRequest productReq) ;

    MessagesResponse getItems(ApiParameter apiParameter);

    MessagesResponse deleteById(long id);

    MessagesResponse getById(long id);
}
