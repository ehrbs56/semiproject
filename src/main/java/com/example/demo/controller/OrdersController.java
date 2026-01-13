package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrdersController {
    private final ProductService productService;
    private final OrdersService ordersService;
    private final CategoryService categoryService;


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
        Map<String, Object> result = new HashMap<>();
        try {
            ordersService.createOrder(ordersRequestDto, loginDto.getStore_id());
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "fail");
            result.put("msg","실패원인 :"+ e.getMessage());
        }
        return result;
    }

    @GetMapping("/branch/orders/ordersList")
    public String ordersList(Model model, @SessionAttribute("loginDto") LoginDto loginDto) {
        List<CategoryDto> categoryList = categoryService.categoryList();
        List<ProductListDto> productList = productService.productList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("list", ordersService.ordersList(loginDto.getStore_id()));
        return "branch/ordersList";
    }

    @GetMapping("/branch/orders/ordersDetail")
    public String ordersDetail(Model model, @RequestParam int order_id) {
        OrdersHeaderDto ordersHeaderDto = ordersService.ordersHeader(order_id);
        model.addAttribute("ordersHeaderDto", ordersHeaderDto);
        model.addAttribute("list", ordersService.ordersDetail(order_id));
        return "branch/ordersDetail";
    }

    @PostMapping("/branch/orders/select")
    @ResponseBody
    public List<OrdersListDto> orderSelect(@SessionAttribute(value = "loginDto", required = false) LoginDto loginDto,
                                           @RequestBody Map<String, Object> map) {
        if (loginDto == null) {
            throw new RuntimeException("로그인이 필요합니다. 세션이 만료되었을 수 있습니다.");
        }
        
        if (map.containsKey("category_id") && map.get("category_id") != null) {
            try {
                Object catId = map.get("category_id");
                if (catId instanceof String && !((String) catId).isEmpty()) {
                    map.put("category_id", Integer.parseInt((String) catId));
                } else if (catId instanceof Number) {
                    map.put("category_id", ((Number) catId).intValue());
                }
            } catch (NumberFormatException e) {
                map.remove("category_id");
            }
        }
        
        if (map.containsKey("product_id") && map.get("product_id") != null) {
            try {
                Object prodId = map.get("product_id");
                if (prodId instanceof String && !((String) prodId).isEmpty()) {
                    map.put("product_id", Integer.parseInt((String) prodId));
                } else if (prodId instanceof Number) {
                    map.put("product_id", ((Number) prodId).intValue());
                }
            } catch (NumberFormatException e) {
                map.remove("product_id");
            }
        }
        
        map.put("store_id", loginDto.getStore_id());
        
        return ordersService.ordersSelect(map);
    }

}
