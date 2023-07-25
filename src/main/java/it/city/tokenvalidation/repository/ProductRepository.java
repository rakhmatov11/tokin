package it.city.tokenvalidation.repository;


import it.city.tokenvalidation.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
