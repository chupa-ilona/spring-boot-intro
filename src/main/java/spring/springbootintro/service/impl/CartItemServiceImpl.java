package spring.springbootintro.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.mapper.CartItemMapper;
import spring.springbootintro.model.CartItem;
import spring.springbootintro.repository.CartItemRepository;
import spring.springbootintro.service.CartItemService;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto save(CreateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.save(cartItemMapper.toModel(requestDto));
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public Page<CartItemDto> findAll(Pageable pageable) {
        return cartItemRepository
                .findAll(pageable)
                .map(cartItemMapper::toDto);
    }

    @Override
    public CartItemDto findById(Long id) {
        return cartItemMapper.toDto(cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not find cartItem with id: "
                        + id)));
    }

    @Override
    public CartItemDto update(Long id, CreateCartItemRequestDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not find cartItem with id: "
                        + id));

        cartItemMapper.updateCartItemFromDto(cartItemDto, cartItem);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book by id: " + id);
        }
        cartItemRepository.deleteById(id);

    }
}
