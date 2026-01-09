package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersDto {
    private int order_id;
    private int store_id;
    private Date order_date;
    private int total_supply_amount;
    private int commission_amount;
}

