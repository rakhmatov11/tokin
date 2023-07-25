package it.city.tokenvalidation.repository;

import it.city.tokenvalidation.entity.PayType;
import it.city.tokenvalidation.projection.CustomPayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payType", collectionResourceRel = "list", excerptProjection = CustomPayType.class)
public interface PayTypeRepository extends JpaRepository<PayType, Integer> {
}
