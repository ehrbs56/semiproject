package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hq_InventoryDto {
    private int hq_inventory_id;
    private int batch_id;
    private int hq_quantity;
}
