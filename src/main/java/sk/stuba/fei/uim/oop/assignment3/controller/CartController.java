package sk.stuba.fei.uim.oop.assignment3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.entity.Cart;
import sk.stuba.fei.uim.oop.assignment3.entity.Product;
import sk.stuba.fei.uim.oop.assignment3.entity.ShoppingListItem;
import sk.stuba.fei.uim.oop.assignment3.response.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.service.CartService;
import sk.stuba.fei.uim.oop.assignment3.service.ProductService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;


    @PostMapping("/cart")
    public ResponseEntity<CartResponse> createCart() {
        return new ResponseEntity<>(new CartResponse(cartService.create()), HttpStatus.CREATED);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable(name = "id") Long id) {
        Optional<Cart> opt = this.cartService.findById(id);
        if(opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Cart cart = opt.get();
        return new ResponseEntity<>(new CartResponse(cart), HttpStatus.OK);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<CartResponse> deleteById(@PathVariable(name = "id") Long id) {
        if(this.cartService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cart/{id}/add")
    public ResponseEntity<CartResponse> addToCart(@PathVariable(name = "id") Long cartId, @RequestBody ShoppingListItem item) {
        Optional<Product> optProduct = productService.getById(item.getProductId());
        Optional<Cart> optCart = cartService.findById(cartId);

        if(optProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(optCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = optProduct.get();
        Cart cart = optCart.get();

        if(cart.isPayed() || product.getAmount() < item.getAmount()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.addProductAmount(product.getId(), -1 * (item.getAmount()));
        cartService.addAmountToShoppingListItem(cartId, item);
        return new ResponseEntity<>(new CartResponse(cart), HttpStatus.OK);
    }

    @GetMapping("/cart/{id}/pay")
    public ResponseEntity<String> sumPay(@PathVariable(name = "id") Long id) {

        Optional<Cart> opt = cartService.findById(id);
        if(opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Cart cart = opt.get();
        if(cart.isPayed()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ShoppingListItem> shoppingList = cart.getShoppingList();
        int sum = 0;
        for(ShoppingListItem item : shoppingList) {
            Long productId = item.getProductId();
            Optional<Product> optProduct = productService.getById(productId);
            if(optProduct.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Product product = optProduct.get();
            sum += (product.getPrice() * item.getAmount());
        }


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(TEXT_PLAIN);


        cartService.payById(cart.getId());
        return new ResponseEntity<>(String.valueOf(sum), httpHeaders, HttpStatus.OK);
    }

}
