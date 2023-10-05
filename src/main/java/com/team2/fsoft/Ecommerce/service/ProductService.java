package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductRes;
import com.team2.fsoft.Ecommerce.entity.Product;


import java.util.List;

public interface ProductService {
    Product save(ProductReq productReq) ;


    MessagesResponse deleteById(long id);

    ProductRes getById(long id);

    PageDTO<ProductDetailResponse> getLists(ApiParameter apiParameter);
}
