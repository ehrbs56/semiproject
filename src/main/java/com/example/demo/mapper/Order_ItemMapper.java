package com.example.demo.mapper;

import com.example.demo.dto.Order_ItemDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Order_ItemMapper {
    int order_ItemInsert(Order_ItemDto order_itemDto);

}
