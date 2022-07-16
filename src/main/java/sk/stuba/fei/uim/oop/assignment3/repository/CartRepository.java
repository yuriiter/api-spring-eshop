package sk.stuba.fei.uim.oop.assignment3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.oop.assignment3.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    List<Cart> findAll();
}
