package com.example.demo.mapper;

import com.example.demo.dto.OrdersDetailDto;
import com.example.demo.dto.OrdersDto;
import com.example.demo.dto.OrdersHeaderDto;
import com.example.demo.dto.OrdersListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {

    int ordersInsert(OrdersDto dto);

    List<OrdersListDto> ordersList(int store_id);

    List<OrdersDetailDto> ordersDetail(int order_id);

    OrdersHeaderDto ordersHeader(int order_id);

    List<OrdersListDto> ordersSelect(Map<String, Object> map);
}
