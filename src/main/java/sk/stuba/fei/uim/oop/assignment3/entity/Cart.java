package sk.stuba.fei.uim.oop.assignment3.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "cart_sequence")
public class Cart {
    @Id
    @GeneratedValue(generator = "cart_sequence")
    private Long id;
    @OneToMany(targetEntity = ShoppingListItem.class, mappedBy = "id", fetch = FetchType.LAZY)
    private List<ShoppingListItem> shoppingList = new ArrayList<>();
    private boolean payed = false;

    public Cart() {}
    public Cart(List<ShoppingListItem> shoppingList, boolean payed) {
        this.shoppingList = new ArrayList<>(shoppingList);
        this.payed = payed;
    }

    public void addShoppingListItem(Long id, int amount) {
        this.shoppingList.add(new ShoppingListItem(id, amount));
    }

    public boolean removeShoppingListItem(Long id) {
        ShoppingListItem find = shoppingList.stream().filter(x -> x.getProductId().equals(id))
                .findAny()
                .orElse(null);
        if(find == null) return false;
        shoppingList.remove(find);
        return true;
    }
    public ShoppingListItem findShoppingListItem(Long productId) {
        return shoppingList.stream().filter(x -> x.getProductId().equals(productId))
                .findAny()
                .orElse(null);
    }
    public void addShoppingListItem(ShoppingListItem item) {
        this.shoppingList.add(item);
    }

}
