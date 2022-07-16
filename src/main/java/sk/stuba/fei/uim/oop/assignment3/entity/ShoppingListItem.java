package sk.stuba.fei.uim.oop.assignment3.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@SequenceGenerator(name = "shopping_list_item_sequence")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListItem {
    @GeneratedValue(generator = "shopping_list_item_sequence")
    @Id
    private Long id;
    private Long productId;
    private int amount;

    public ShoppingListItem(Long productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
