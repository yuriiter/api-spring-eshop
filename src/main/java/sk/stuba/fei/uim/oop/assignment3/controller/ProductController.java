package sk.stuba.fei.uim.oop.assignment3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.container.AmountContainer;
import sk.stuba.fei.uim.oop.assignment3.entity.Product;
import sk.stuba.fei.uim.oop.assignment3.request.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.response.ProductResponse;
import sk.stuba.fei.uim.oop.assignment3.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;


    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return this.service.getAll();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "id") Long id) {
        Optional<Product> opt = this.service.getById(id);
        if(opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProductResponse(opt.get()), HttpStatus.OK);
    }

    @GetMapping("/product/{id}/amount")
    public ResponseEntity<AmountContainer> getProductAmount(@PathVariable(name = "id") Long id) {
        Optional<Product> opt = this.service.getById(id);
        if(opt.isPresent()) {
            AmountContainer response = new AmountContainer((opt.get()).getAmount());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return new ResponseEntity<>(new ProductResponse(this.service.create(request)), HttpStatus.CREATED);
    }

    @PostMapping("/product/{id}/amount")
    public ResponseEntity<AmountContainer> addProductAmount(@PathVariable(name = "id") Long id, @RequestBody AmountContainer amountContainer) {
        int amount = amountContainer.getAmount();
        Product updated = this.service.addProductAmount(id, amount);
        if(updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new AmountContainer(updated.getAmount()), HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductRequest request) {
        Product updated = this.service.update(id, request);
        if(updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProductResponse(updated), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "id") Long id) {
        if(this.service.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
