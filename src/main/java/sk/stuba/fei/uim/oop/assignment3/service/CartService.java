package sk.stuba.fei.uim.oop.assignment3.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.entity.Cart;
import sk.stuba.fei.uim.oop.assignment3.entity.ShoppingListItem;
import sk.stuba.fei.uim.oop.assignment3.repository.CartRepository;
import sk.stuba.fei.uim.oop.assignment3.repository.ShoppingListItemRepository;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ShoppingListItemRepository shoppingListItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ShoppingListItemRepository shoppingListItemRepository) {
        this.cartRepository = cartRepository;
        this.shoppingListItemRepository = shoppingListItemRepository;
    }


    public Cart create() {
        return this.cartRepository.save(new Cart());
    }

    public Optional<Cart> findById(Long id) {
        return this.cartRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if (this.cartRepository.existsById(id)) {
            this.cartRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Cart addAmountToShoppingListItem(Long cartId, ShoppingListItem item) {
        Optional<Cart> opt = this.cartRepository.findById(cartId);
        if(opt.isEmpty()) {
            return null;
        }
        Cart cart = opt.get();
        ShoppingListItem inCartItem = cart.findShoppingListItem(item.getProductId());
        if(inCartItem != null) {
            inCartItem.setAmount(inCartItem.getAmount() + item.getAmount());
        }
        else {
            this.shoppingListItemRepository.save(item);
            cart.addShoppingListItem(item);
        }
        return this.cartRepository.save(cart);
    }

    public Cart payById(Long id) {
        Optional<Cart> opt = cartRepository.findById(id);
        if(opt.isEmpty()) {
            return null;
        }
        Cart cart = opt.get();
        if(cart.isPayed()) {
            return null;
        }
        cart.setPayed(true);
        return cartRepository.save(cart);
    }
}
