package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailRequest;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailMapper;
import com.team2.fsoft.Ecommerce.repository.ProductDetailRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public MessagesResponse showInfo(long id) {
        MessagesResponse ms = new MessagesResponse();
        Optional<ProductDetail> productDetail = productDetailRepository.findById(id);
        if (productDetail.isPresent()) {
            ms.data = productDetail.get();
        } else  {
            ms.code=404;
            ms.message="Không tìm thấy thông tin sản phẩm, vui lòng thử lại!";
        }
        return  ms;
    }

    @Override
    public MessagesResponse updateInfo(ProductDetailRequest productDetailRequest, long id) {
        MessagesResponse ms = new MessagesResponse();
        Optional<ProductDetail> productDetail1 = productDetailRepository.findById(id);
        if (productDetail1.isPresent()) {
            ms.data = productDetailMapper.toDTO(productDetail1.get());
        } else  {
            ms.code=404;
            ms.message="Không tìm thấy thông tin sản phẩm, vui lòng thử lại!";
        }
        return  ms;
    }
}
