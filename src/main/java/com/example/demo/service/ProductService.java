package com.example.demo.service;

import com.example.demo.dto.ProductListDto;
import com.example.demo.dto.ProductSelectDto;
import com.example.demo.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public List<ProductListDto> productList() {
        return productMapper.productList();
    }

    public ProductSelectDto productSelect(int product_id) {
        return productMapper.productSelect(product_id);
    }

}
