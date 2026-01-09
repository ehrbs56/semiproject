package com.example.demo.mapper;

import com.example.demo.dto.OrdersDto;
import com.example.demo.dto.OrdersListDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper {

    OrdersListDto ordersList(int product_id);

    int ordersInsert(OrdersDto dto);
}
