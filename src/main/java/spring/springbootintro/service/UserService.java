package spring.springbootintro.service;

import spring.springbootintro.dto.UserRegistrationRequestDto;
import spring.springbootintro.dto.UserResponseDto;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto);

}
