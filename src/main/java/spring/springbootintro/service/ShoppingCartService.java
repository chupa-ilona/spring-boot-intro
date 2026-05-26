package spring.springbootintro.service;

import org.springframework.stereotype.Service;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.dto.ShoppingCartDto;

@Service
public interface ShoppingCartService {

    ShoppingCartDto getCart(Long userId);

    ShoppingCartDto addCartItemToCart(Long userId, CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateCartItem(Long userId, Long cartItemId, CartItemDto updateDto);

    ShoppingCartDto deleteCartItem(Long userId, Long cartItemId);

}
