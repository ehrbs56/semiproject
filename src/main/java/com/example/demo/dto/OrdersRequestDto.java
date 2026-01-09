package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersRequestDto {
    private int total_supply_amount;
    private List<OrdersItemRequestDto> order_item;

}
