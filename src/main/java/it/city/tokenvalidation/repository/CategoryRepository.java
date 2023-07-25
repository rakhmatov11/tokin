package it.city.tokenvalidation.repository;


import it.city.tokenvalidation.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
