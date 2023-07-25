package it.city.tokenvalidation.projection;

import it.city.tokenvalidation.entity.Price;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Price.class)
public interface CustomPrice {
    Integer getId();
    double getFromDistance();
    double getToDistance();
    double getDeliveryPrice();
}
