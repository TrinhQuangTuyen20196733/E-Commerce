package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.Size;
import com.team2.fsoft.Ecommerce.repository.CategoryRepository;
import com.team2.fsoft.Ecommerce.repository.SizeRepository;
import com.team2.fsoft.Ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;
    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }
}
