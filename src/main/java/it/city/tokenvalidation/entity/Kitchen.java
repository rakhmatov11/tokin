package it.city.tokenvalidation.entity;

import it.city.tokenvalidation.entity.template.AbsNameEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kitchen extends AbsNameEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToOne(optional = false)
    private Attachment attachment;

    @Column(nullable = false)
    private Timestamp deliveryTime;

    @Column(nullable = false)
    private double deliveryPrice;

    private boolean isDiscount;

    private double discountPrice;

    private String info;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp finishTime;

    private boolean isOpen;

    private String lat;

    private String lan;


//
//
}

