package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersMapper ordersMapper;
    private final StoreMapper storeMapper;
    private final Order_ItemMapper order_itemMapper;
    private final SupplyMapper supplyMapper;
    private final Product_BatchMapper product_batchMapper;
    private final Supply_DetailMapper supply_detailMapper;
    private final Hq_InventoryMapper hq_inventoryMapper;

    public OrdersListDto ordersList(int product_id) {
        return ordersMapper.ordersList(product_id);
    }

    @Transactional
    public void createOrder(OrdersRequestDto ordersRequestDto, int store_id) {
        StoreDetailDto storeDetailDto=storeMapper.StoreDetail(store_id);

        int commission = (int) (ordersRequestDto.getTotal_supply_amount() * storeDetailDto.getCommission_rate()/100.0);
        // 발주 생성
        OrdersDto ordersDto=new OrdersDto();
        ordersDto.setStore_id(store_id);
        ordersDto.setTotal_supply_amount(ordersRequestDto.getTotal_supply_amount());
        ordersDto.setCommission_amount(commission);
        ordersDto.setOrder_date(new Date());
        ordersMapper.ordersInsert(ordersDto);
        // 지급 생성
        int order_id = ordersDto.getOrder_id();
        SupplyDto supplyDto=new SupplyDto();
        supplyDto.setStore_id(store_id);
        supplyDto.setOrder_id(order_id);
        supplyDto.setSupply_date(new Date());
        supplyDto.setHq_supply_price(ordersRequestDto.getTotal_supply_amount());

        supplyMapper.supplyInsert(supplyDto);

        int supply_id = supplyDto.getSupply_id();

        for(OrdersItemRequestDto item : ordersRequestDto.getOrder_item()) {
            // 발주 상세 생성
            Order_ItemDto order_itemDto = new Order_ItemDto();
            order_itemDto.setOrder_id(order_id);
            order_itemDto.setProduct_id(item.getProduct_id());
            order_itemDto.setOrder_qty(item.getOrder_qty());

            order_itemMapper.order_ItemInsert(order_itemDto);

            List<Integer> batch_idList = product_batchMapper.product_BatchSelect(item.getProduct_id());
            int qty = item.getOrder_qty();
            for(int batch_id : batch_idList) {
                if(qty <= 0) break;
                int usedQty;
                List<Hq_InventoryDto> inventoryList=hq_inventoryMapper.hq_InventorySelect(batch_id);

                for(Hq_InventoryDto inv : inventoryList) {
                    if(qty <= 0) break;
                    if(inv.getHq_quantity() >= qty) {
                        usedQty = qty;
                        inv.setHq_quantity(inv.getHq_quantity() - qty);
                        qty = 0;
                    }else {
                        usedQty = inv.getHq_quantity();
                        qty -= inv.getHq_quantity();
                        inv.setHq_quantity(0);

                    }
                    if(usedQty > 0) {
                        hq_inventoryMapper.hq_InventoryUpdate(inv);
                        // 지급 상세 생성
                        Supply_DetailDto supply_detailDto=new Supply_DetailDto();
                        supply_detailDto.setSupply_id(supply_id);
                        supply_detailDto.setBatch_id(batch_id);
                        supply_detailDto.setQuantity(usedQty);
                        supply_detailMapper.Supply_DetailInsert(supply_detailDto);
                    }
                }
            }
            if (qty > 0) {
                throw new RuntimeException("상품(ID:" + item.getProduct_id() + ")의 본사 재고가 부족합니다.");
            }
        }

    }
}
