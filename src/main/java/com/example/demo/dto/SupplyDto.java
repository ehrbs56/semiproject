package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplyDto {
    private int supply_id;
    private int store_id;
    private int order_id;
    private Date supply_date;
    private int hq_supply_price;

}
