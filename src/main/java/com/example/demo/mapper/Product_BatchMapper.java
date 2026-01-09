package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Product_BatchMapper {
    List<Integer> product_BatchSelect(int product_id);

}
