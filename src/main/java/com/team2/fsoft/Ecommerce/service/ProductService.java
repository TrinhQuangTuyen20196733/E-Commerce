package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;

public interface ProductService {
    MessagesResponse save(ProductReq productReq) ;

    MessagesResponse getItems(ApiParameter apiParameter);

    MessagesResponse deleteById(long id);

    MessagesResponse getById(long id);
}
