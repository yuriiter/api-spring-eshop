package sk.stuba.fei.uim.oop.assignment3.response;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.entity.Product;

@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private String unit;
    private Integer price;

    public ProductResponse(Product p) {
        id = p.getId();
        name = p.getName();
        description = p.getDescription();
        amount = p.getAmount();
        unit = p.getUnit();
        price = p.getPrice();
    }
}
