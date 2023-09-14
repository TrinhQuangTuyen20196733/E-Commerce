package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.response.ShopResponse;
import com.team2.fsoft.Ecommerce.entity.Shop;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopMapper implements Mapper<Shop, ShopResponse> {

    @Override
    public Shop toEntity(ShopResponse dto) {
        return null;
    }

    @Override
    public ShopResponse toDTO(Shop entity) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Shop, ShopResponse> typeMap =  modelMapper.createTypeMap(Shop.class,ShopResponse.class);
        return modelMapper.map(entity, ShopResponse.class);
    }

    @Override
    public List<ShopResponse> toDTOList(List<Shop> entityList) {
        return null;
    }

    @Override
    public List<Shop> toEntityList(List<ShopResponse> dtoList) {
        return null;
    }
}
