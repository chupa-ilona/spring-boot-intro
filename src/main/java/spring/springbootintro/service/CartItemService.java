package spring.springbootintro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;

public interface CartItemService {

    CartItemDto save(CreateCartItemRequestDto cartItemDto);

    Page<CartItemDto> findAll(Pageable pageable);

    CartItemDto findById(Long id);

    CartItemDto update(Long id, CreateCartItemRequestDto cartItemDto);

    void delete(Long id);
}
