package com.example.demo.mapper;

import com.example.demo.dto.Supply_DetailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Supply_DetailMapper {
    int Supply_DetailInsert(Supply_DetailDto supply_detailDto);

}
