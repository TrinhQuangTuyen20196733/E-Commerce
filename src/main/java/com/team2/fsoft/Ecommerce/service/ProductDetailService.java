package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailRequest;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;

public interface ProductDetailService {
    MessagesResponse showInfo(long id);

    MessagesResponse updateInfo(ProductDetailRequest productDetailRequest, long id);
}
