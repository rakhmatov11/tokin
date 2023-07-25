package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.enums.OrderPayStatus;
import it.city.tokenvalidation.entity.enums.OrderStatus;
import it.city.tokenvalidation.entity.template.AbsNameEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends AbsNameEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_product",
                joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;
    @ManyToOne(optional = false)
    private Kitchen kitchen;
    private double price;
    private String lat;
    private String lan;
    private double deliveryPrice;
    private Integer productAmount;
    private double totalAmount;
    private Integer orderNumber;
    @Enumerated(EnumType.STRING)
    private OrderPayStatus orderPayStatus;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne(optional = false)
    private PayType payType;
    @ManyToOne(optional = false)
    private User client;        //Buyurtma beruvchi mijoz
    @ManyToOne(optional = false)
    private User agent;
}