package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductRes;
import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailMapper;
import com.team2.fsoft.Ecommerce.repository.*;
import com.team2.fsoft.Ecommerce.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    final ProductDetailMapper productDetailMapper;
    final ShopRepository shopRepository;

    final ProductRepository productRepository;
    final CustomProductRepository customProductRepository;

    final CategoryRepository categoryRepository;

    final ProductDetailRepository productDetailRepository;

    public ProductServiceImpl(ProductDetailMapper productDetailMapper, ShopRepository shopRepository, ProductRepository productRepository, CustomProductRepository customProductRepository,CategoryRepository categoryRepository,ProductDetailRepository productDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.customProductRepository = customProductRepository;
        this.categoryRepository=categoryRepository;
        this.productDetailRepository=productDetailRepository;
    }

    @Override
    @Transactional
    public MessagesResponse save(ProductReq productReq) {
        Product product = new Product();
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setCategory(categoryRepository.findByCode(productReq.getCategory()).get());
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var shopOptional = shopRepository.findByUserEmail(email);
        if (shopOptional.isPresent()) {
            product.setShop(shopOptional.get());
            productRepository.save(product);
            productReq.productDetailReqList.forEach(productDetailReq -> {
                ProductDetail productDetail = productDetailMapper.toEntity(productDetailReq);
                productDetail.setProduct(product);
                productDetailRepository.save(productDetail);
            });

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
//            ms.data = pr.toDTOList(product);
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
            Product product = productRepository.findById(id).get();
        ms.data =  product.getProductDetailList().stream().map(productDetail ->
              new ProductRes(product.getId(),product.getName(),product.getDescription(),productDetail.getOriginPrice(),
                      productDetail.getPrice(),product.getCategory().getCode(),productDetail.getColor().getCode(),productDetail.getSize().getCode(),productDetail.getInStock(),productDetail.getSoldQuantity())).collect(Collectors.toList());


        } catch (Exception ex) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return  ms;
    }
}
