package sk.stuba.fei.uim.oop.assignment3.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.oop.assignment3.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
