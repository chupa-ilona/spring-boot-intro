package spring.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.UserRegistrationRequestDto;
import spring.springbootintro.dto.UserResponseDto;
import spring.springbootintro.service.UserService;

@Tag(name = "Authentication management",
        description = "Endpoints for managing user registration and authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping
    @RequestMapping("/registration")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @Operation(summary = "Register a new user",
            description = "Registers a new user in the system and returns the user details")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

}
