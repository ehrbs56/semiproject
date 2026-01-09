package com.example.demo.service;

import com.example.demo.dto.Hq_InventoryDto;
import com.example.demo.mapper.Hq_InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Hq_InventoryService {
    private final Hq_InventoryMapper hq_inventoryMapper;

    public List<Hq_InventoryDto> hq_InventorySelect(int batch_id) {
        return hq_inventoryMapper.hq_InventorySelect(batch_id);
    }

    public int hq_InventoryUpdate(Hq_InventoryDto hq_inventoryDto) {
        return hq_inventoryMapper.hq_InventoryUpdate(hq_inventoryDto);
    }
}
