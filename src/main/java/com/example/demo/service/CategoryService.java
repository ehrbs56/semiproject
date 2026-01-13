package com.example.demo.service;


import com.example.demo.dto.CategoryDto;
import com.example.demo.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> categoryList() {
        return categoryMapper.categoryList();
    }
}
