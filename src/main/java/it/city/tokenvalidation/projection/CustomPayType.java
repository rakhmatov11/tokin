package it.city.tokenvalidation.projection;

import it.city.tokenvalidation.entity.PayType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = PayType.class)
public interface CustomPayType {
    Integer getId();
    String getName();
}
