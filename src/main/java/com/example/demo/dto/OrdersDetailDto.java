package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersDetailDto {
    private int order_item_id;
    private String product_name;
    private String category_name;
    private int order_qty;
    private int hq_supply_price;
    private int total_price;
}
