package it.city.tokenvalidation.payload;

import it.city.tokenvalidation.entity.PayType;
import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.entity.enums.OrderPayStatus;
import it.city.tokenvalidation.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<Integer> productsId;
    private List<ProductDto> productsDto;
    private Integer kitchenId;
    private KitchenDto kitchenDto;
    private double price;
    private Integer productAmount;
    private String lat;
    private String lan;
    private double deliveryPrice;
    private double totalAmount;
    private Integer orderNumber;
    private OrderPayStatus orderPayStatus;
    private OrderStatus orderStatus;
    private PayType payType;//TO`LOV TURI YA`NI IN-PERSON VA ONLINEDAN BIRINI
    private User client;   // BUYURTMA BERUVCHI MIJOZ
    private User agent;

    public OrderDto(List<ProductDto> productsDto, KitchenDto kitchenDto, double price, Integer productAmount, String lat, String lan, double deliveryPrice, double totalAmount, Integer orderNumber, OrderPayStatus orderPayStatus, OrderStatus orderStatus, PayType payType, User client, User agent) {
        this.productsDto = productsDto;
        this.kitchenDto = kitchenDto;
        this.price = price;
        this.productAmount = productAmount;
        this.lat = lat;
        this.lan = lan;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
        this.orderNumber = orderNumber;
        this.orderPayStatus = orderPayStatus;
        this.orderStatus = orderStatus;
        this.payType = payType;
        this.client = client;
        this.agent = agent;
    }
}
