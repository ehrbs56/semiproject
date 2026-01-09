package com.example.demo.mapper;

import com.example.demo.dto.Hq_InventoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Hq_InventoryMapper {

    List<Hq_InventoryDto> hq_InventorySelect(int batch_id);
    int hq_InventoryUpdate(Hq_InventoryDto hq_inventoryDto);


}
