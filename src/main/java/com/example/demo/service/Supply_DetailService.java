package com.example.demo.service;

import com.example.demo.dto.Supply_DetailDto;
import com.example.demo.mapper.Supply_DetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Supply_DetailService {
    private final Supply_DetailMapper supply_detailMapper;

    public int Supply_DetailInsert(Supply_DetailDto supply_detailDto) {
        return supply_detailMapper.Supply_DetailInsert(supply_detailDto);
    }

}
