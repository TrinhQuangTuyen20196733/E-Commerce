package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import com.team2.fsoft.Ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper implements Mapper<Product, ProductRequest> {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product toEntity(ProductRequest dto) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ProductRequest, Product> typeMap = modelMapper.createTypeMap(ProductRequest.class, Product.class);
        var product = modelMapper.map(dto, Product.class);
        var category = categoryRepository.findByName(dto.category).get();
        product.setCategory(category);
        return product;

    }

    @Override
    public ProductRequest toDTO(Product entity) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Product, ProductRequest> typeMap = modelMapper.createTypeMap(Product.class, ProductRequest.class);
        var productReq = modelMapper.map(entity, ProductRequest.class);

        productReq.setCategory(entity.getCategory().getName());
        return productReq;
    }

    @Override
    public List<ProductRequest> toDTOList(List<Product> entityList) {
        return entityList.stream().map(entity ->
                toDTO(entity)
        ).collect(Collectors.toList());

    }

    @Override
    public List<Product> toEntityList(List<ProductRequest> dtoList) {
        return null;
    }
}
