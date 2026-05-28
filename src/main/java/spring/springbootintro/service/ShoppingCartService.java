package spring.springbootintro.service;

import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.dto.ShoppingCartDto;

public interface ShoppingCartService {

    ShoppingCartDto getCart(Long userId);

    ShoppingCartDto addCartItemToCart(Long userId, CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateCartItem(Long userId, Long cartItemId, CartItemDto updateDto);

    ShoppingCartDto deleteCartItem(Long userId, Long cartItemId);

}
