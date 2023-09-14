package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailRequest;
import com.team2.fsoft.Ecommerce.dto.response.ShopResponse;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.entity.Shop;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDetailMapper implements Mapper<ProductDetail, ProductDetailRequest> {
    @Override
    public ProductDetail toEntity(ProductDetailRequest dto) {
        return null;
    }

    @Override
    public ProductDetailRequest toDTO(ProductDetail entity) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ProductDetail, ProductDetailRequest> typeMap =  modelMapper.createTypeMap(ProductDetail.class,ProductDetailRequest.class);
        return modelMapper.map(entity, ProductDetailRequest.class);
    }

    @Override
    public List<ProductDetailRequest> toDTOList(List<ProductDetail> entityList) {
        return null;
    }

    @Override
    public List<ProductDetail> toEntityList(List<ProductDetailRequest> dtoList) {
        return null;
    }
}
