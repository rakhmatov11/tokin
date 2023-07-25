package it.city.tokenvalidation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KitchenDto {
    private Integer id;
    private String name;
    private List<Integer> productsId;
    private List<ProductDto> productDto;
    private List<Integer> categoryId;
    private List<CategoryDto> categoryDto;
    private UUID attachmentId;
    private AttachmentDto attachmentDto;
    private Timestamp deliveryTime;
    private double deliveryPrice;
    private boolean isDiscount;
    private double discountPrice;
    private String info;
    private Timestamp startTime;
    private Timestamp finishTime;
    private boolean isOpen;
    private String lan;
    private String lat;


    public KitchenDto(Integer id, String name, UUID attachmentId, Timestamp deliveryTime, double deliveryPrice,
                      boolean isDiscount, double discountPrice, String info, Timestamp startTime, Timestamp finishTime,
                      boolean isOpen, String lan, String lat) {
        this.id = id;
        this.name = name;
        this.attachmentId = attachmentId;
        this.deliveryTime = deliveryTime;
        this.deliveryPrice = deliveryPrice;
        this.isDiscount = isDiscount;
        this.discountPrice = discountPrice;
        this.info = info;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.isOpen = isOpen;
        this.lan=lan;
        this.lat=lat;
    }



}