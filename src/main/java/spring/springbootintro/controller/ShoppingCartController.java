package spring.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.dto.ShoppingCartDto;
import spring.springbootintro.model.User;
import spring.springbootintro.service.ShoppingCartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getCart(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto addCartItemToCart(Authentication authentication,
                                             @RequestBody @Valid CreateCartItemRequestDto requestDto
                                             ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addCartItemToCart(user.getId(), requestDto);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update quantity of a book in the shopping cart",
            description = "Updates the quantity of an existing item in the user's shopping cart.")
    public ShoppingCartDto updateCartItem(Authentication authentication,
                                          @PathVariable Long cartItemId,
                                          @RequestBody @Valid CartItemDto updateDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCartItem(user.getId(), cartItemId, updateDto);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove a book from the shopping cart",
            description = "Removes an item completely from the user's shopping cart.")
    public void removeCartItem(Authentication authentication, @PathVariable Long cartItemId) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItem(user.getId(), cartItemId);
    }

}





