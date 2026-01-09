package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrdersController {
    private final ProductService productService;
    private final OrdersService ordersService;


    @GetMapping("/branch/orders")
    public String productList(Model model) {
        model.addAttribute("list", productService.productList());
        return "branch/orders";
    }
    @GetMapping("/branch/orders/add")
    @ResponseBody
    public ProductSelectDto productSelect(@RequestParam int product_id) {
        return productService.productSelect(product_id);
    }

    @PostMapping("/branch/orders")
    @ResponseBody
    public Map<String, Object> createOrder(@RequestBody OrdersRequestDto ordersRequestDto,
                                           @SessionAttribute("loginDto") LoginDto loginDto) {
        System.out.println("진입:" + ordersRequestDto);
        Map<String, Object> result = new HashMap<>();
        System.out.println("loginDto 컨트롤러 진입시: " + loginDto);
        try {
            // 서비스 호출
            ordersService.createOrder(ordersRequestDto, loginDto.getStore_id());
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "fail");
            result.put("msg","실패원인 :"+ e.getMessage());
        }
        return result;
    }

}
