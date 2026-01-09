package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order_ItemDto {
    private int order_item_id;
    private int order_id;
    private int product_id;
    private int order_qty;

}
