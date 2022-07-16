package sk.stuba.fei.uim.oop.assignment3.entity;

import lombok.*;
import sk.stuba.fei.uim.oop.assignment3.request.ProductRequest;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "product_sequence")
public class Product {
    @Id
    @GeneratedValue(generator = "product_sequence")
    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private String unit;
    private Integer price;

    public Product() {}

    public Product(String name, String description, int amount, String unit, int price) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.amount = request.getAmount();
        this.unit = request.getUnit();
        this.price = request.getPrice();
    }


}


