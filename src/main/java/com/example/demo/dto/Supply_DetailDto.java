package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supply_DetailDto {
    private int supply_detail_id;
    private int supply_id;
    private int batch_id;
    private int quantity;
}
