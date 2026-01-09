package com.example.demo.service;

import com.example.demo.dto.Order_ItemDto;
import com.example.demo.mapper.Order_ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Order_ItemService {
    private final Order_ItemMapper order_itemMapper;

    public int order_ItemInsert(Order_ItemDto order_itemDto) {
        return order_itemMapper.order_ItemInsert(order_itemDto);
    }


}
