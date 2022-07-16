package sk.stuba.fei.uim.oop.assignment3.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.entity.ShoppingListItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItemResponse {
    private Long productId;
    private int amount;

    public ShoppingListItemResponse(ShoppingListItem item) {
        this.productId = item.getProductId();
        this.amount = item.getAmount();
    }
}
