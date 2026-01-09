package com.example.demo.service;

import com.example.demo.dto.SupplyDto;
import com.example.demo.mapper.SupplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyMapper supplyMapper;

    public int supplyInsert(SupplyDto supplyDto) {
        return supplyMapper.supplyInsert(supplyDto);
    }
}
