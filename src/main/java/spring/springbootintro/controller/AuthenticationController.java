package spring.springbootintro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.UserRegistrationRequestDto;
import spring.springbootintro.dto.UserResponseDto;
import spring.springbootintro.service.impl.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserServiceImpl userService;

    @PostMapping
    public UserResponseDto register(@RequestBody UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

}
