package com.example.demo.mapper;

import com.example.demo.dto.SupplyDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplyMapper {

    int supplyInsert(SupplyDto supplyDto);


}
