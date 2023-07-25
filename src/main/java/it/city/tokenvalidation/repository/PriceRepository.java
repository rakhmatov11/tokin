package it.city.tokenvalidation.repository;

import it.city.tokenvalidation.entity.Price;
import it.city.tokenvalidation.projection.CustomPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "price", collectionResourceRel = "list", excerptProjection = CustomPrice.class)
public interface PriceRepository extends JpaRepository<Price, Integer> {
}
