package it.city.tokenvalidation.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private Integer id;
    private double fromDistance;
    private double toDistance;
    private double deliveryPrice;
}
