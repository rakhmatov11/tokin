package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.OrderDto;
import it.city.tokenvalidation.security.CurrentUser;
import it.city.tokenvalidation.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public HttpEntity<?> getOrderList(){
        return ResponseEntity.ok(orderService.getOrderList());
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOrder(@PathVariable UUID id){
        ApiResponse apiResponse = orderService.deleteOrder(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping
    public HttpEntity<?> addOrder(@RequestBody OrderDto orderDto, @CurrentUser User user){
        ApiResponse apiResponse = orderService.addOrder(orderDto, user);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editOrder(@PathVariable UUID id, @RequestBody OrderDto orderDto){
        ApiResponse apiResponse = orderService.editOrder(id, orderDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneOrder(@PathVariable UUID id){
        OrderDto oneOrder = orderService.getOneOrder(id);
        return ResponseEntity.ok(oneOrder);
    }

}
