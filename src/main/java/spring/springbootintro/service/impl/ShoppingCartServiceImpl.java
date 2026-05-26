package spring.springbootintro.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.dto.ShoppingCartDto;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.mapper.ShoppingCartMapper;
import spring.springbootintro.model.Book;
import spring.springbootintro.model.CartItem;
import spring.springbootintro.model.ShoppingCart;
import spring.springbootintro.repository.BookRepository;
import spring.springbootintro.repository.ShoppingCartRepository;
import spring.springbootintro.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto getCart(Long userId) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addCartItemToCart(Long userId, CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId);

        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        } else {
            Book book = bookRepository.findById(requestDto.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Can't find book by id: "
                            + requestDto.getBookId()));

            CartItem newCartItem = new CartItem();
            newCartItem.setShoppingCart(shoppingCart);
            newCartItem.setBook(book);
            newCartItem.setQuantity(requestDto.getQuantity());

            shoppingCart.getCartItems().add(newCartItem);
        }

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItem(Long userId, Long cartItemId, CartItemDto updateDto) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId);
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item by id: "
                        + cartItemId));

        cartItem.setQuantity(updateDto.getQuantity());

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto deleteCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId);

        CartItem cartItemToRemove = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item by id: "
                        + cartItemId));

        shoppingCart.getCartItems().remove(cartItemToRemove);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find shopping cart "
                        + "for user with id: " + userId));
    }
}
