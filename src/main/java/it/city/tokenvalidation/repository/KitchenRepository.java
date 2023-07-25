package it.city.tokenvalidation.repository;

import it.city.tokenvalidation.entity.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen,Integer> {
    boolean existsByName(String name);
}