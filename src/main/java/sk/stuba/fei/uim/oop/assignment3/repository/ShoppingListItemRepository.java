package sk.stuba.fei.uim.oop.assignment3.repository;

import org.springframework.data.repository.CrudRepository;
import sk.stuba.fei.uim.oop.assignment3.entity.ShoppingListItem;

import java.util.List;

public interface ShoppingListItemRepository extends CrudRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findAll();
}
