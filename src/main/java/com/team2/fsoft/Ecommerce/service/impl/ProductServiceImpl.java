package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductMapper;
import com.team2.fsoft.Ecommerce.repository.CustomProductRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShopRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomProductRepository customProductRepository;

    @Override
    public MessagesResponse save(ProductRequest productReq) {
        MessagesResponse ms = new MessagesResponse();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail) authentication.getPrincipal();
        var userId = user.getId();
        var shopOptional = shopRepository.findByUserId(userId);
        if (shopOptional.isPresent()) {
            var product = productMapper.toEntity(productReq);
            product.setShop(shopOptional.get());
            productRepository.save(product);

        } else {
            ms.code = 500;
            ms.message = " Internal Server Error!";
        }
        return ms;
    }

    @Override
    public MessagesResponse getItems(ApiParameter apiParameter) {
        MessagesResponse ms = new MessagesResponse();

        try {
            var product = customProductRepository.getByFilter(apiParameter);
            ms.data = productMapper.toDTOList(product);
        } catch (Exception e) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return ms;
    }

    @Override
    public MessagesResponse deleteById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            productRepository.deleteById(id);

        } catch (Exception ex) {
            ms.code = 500;
            ms.message = "Lỗi khi thao tác xóa sản phẩm. Vui lòng thử lại!";
        }
        return ms;
    }

    @Override
    public MessagesResponse getById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var product = productRepository.findById(id).get();
            ms.data = productMapper.toDTO(product);
        } catch (Exception ex) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return  ms;
    }
}
