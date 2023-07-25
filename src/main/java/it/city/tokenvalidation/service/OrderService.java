package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Kitchen;
import it.city.tokenvalidation.entity.Order;
import it.city.tokenvalidation.entity.Product;
import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.payload.ApiResponse;
import it.city.tokenvalidation.payload.OrderDto;
import it.city.tokenvalidation.repository.KitchenRepository;
import it.city.tokenvalidation.repository.OrderRepository;
import it.city.tokenvalidation.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final KitchenRepository kitchenRepository;
    final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, KitchenRepository kitchenRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.kitchenRepository = kitchenRepository;
        this.productRepository = productRepository;
    }

    public OrderDto getOneOrder(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getOrder"));
        return new OrderDto(
                null,
                null,
                order.getPrice(),
                order.getProductAmount(),
                order.getLat(),
                order.getLan(),
                order.getDeliveryPrice(),
                order.getTotalAmount(),
                order.getOrderNumber(),
                order.getOrderPayStatus(),
                order.getOrderStatus(),
                order.getPayType(),
                order.getClient(),
                order.getAgent()
        );
    }

    public List<OrderDto> getOrderList(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(
                    null,
                    null,
                    order.getPrice(),
                    order.getProductAmount(),
                    order.getLat(),
                    order.getLan(),
                    order.getDeliveryPrice(),
                    order.getTotalAmount(),
                    order.getOrderNumber(),
                    order.getOrderPayStatus(),
                    order.getOrderStatus(),
                    order.getPayType(),
                    order.getClient(),
                    order.getAgent()
            );
            ordersDto.add(orderDto);
        }
        return ordersDto;
    }

    public ApiResponse addOrder(OrderDto orderDto,User user){
        Kitchen kitchen = kitchenRepository.findById(orderDto.getKitchenId()).orElseThrow(() -> new ResourceAccessException("getKitchen"));
        List<Product> products = new ArrayList<>();
        for (Integer productId : orderDto.getProductsId()) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceAccessException("getProduct"));
            products.add(product);
        }
        double totalAmount = orderDto.getPrice() * orderDto.getProductAmount() + kitchen.getDeliveryPrice();
        Order order = new Order();
        order.setProducts(products);
        order.setKitchen(kitchen);
        order.setPrice(orderDto.getPrice());
        order.setProductAmount(orderDto.getProductAmount());
        order.setLat(order.getLat());
        order.setLan(orderDto.getLan());
        order.setDeliveryPrice(kitchen.getDeliveryPrice());
        order.setTotalAmount(totalAmount);
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderPayStatus(orderDto.getOrderPayStatus());
        order.setOrderStatus(order.getOrderStatus());
        order.setPayType(orderDto.getPayType());
        order.setClient(user);
        order.setAgent(orderDto.getAgent());
        orderRepository.save(order);
        return new ApiResponse("Successfully saved order",true);
    }



    public ApiResponse deleteOrder(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getOrder"));
        orderRepository.delete(order);
        return new ApiResponse("delete Order",true);
    }

    public ApiResponse editOrder(UUID id,OrderDto orderDto){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getOrder"));
        Kitchen kitchen = kitchenRepository.findById(orderDto.getKitchenId()).orElseThrow(() -> new ResourceAccessException("getKitchen"));
        List<Product> products = new ArrayList<>();
        for (Integer productId : orderDto.getProductsId()) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceAccessException("getProduct"));
            products.add(product);
        }
        double totalAmount = orderDto.getPrice() * orderDto.getProductAmount() + kitchen.getDeliveryPrice();
        order.setProducts(products);
        order.setKitchen(kitchen);
        order.setPrice(orderDto.getPrice());
        order.setProductAmount(orderDto.getProductAmount());
        order.setLat(order.getLat());
        order.setLan(orderDto.getLan());
        order.setDeliveryPrice(kitchen.getDeliveryPrice());
        order.setTotalAmount(totalAmount);
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderPayStatus(orderDto.getOrderPayStatus());
        order.setOrderStatus(order.getOrderStatus());
        order.setPayType(orderDto.getPayType());
        orderRepository.save(order);
        return new ApiResponse("Successfully saved order",true);
    }
}
