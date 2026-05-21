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
import spring.springbootintro.dto.UserLoginRequestDto;
import spring.springbootintro.dto.UserLoginResponseDto;
import spring.springbootintro.dto.UserRegistrationRequestDto;
import spring.springbootintro.dto.UserResponseDto;
import spring.springbootintro.security.AuthenticationService;
import spring.springbootintro.service.UserService;

@Tag(name = "Authentication management",
        description = "Endpoints for managing user registration and authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping
    @RequestMapping("/registration")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @Operation(summary = "Register a new user",
            description = "Registers a new user in the system and returns the user details")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping
    @RequestMapping("/login")
    @Operation(summary = "Login a user",
            description = "Logs in a user and returns the user details")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        String token = authenticationService.authenticate(requestDto);
        return new UserLoginResponseDto(token);
    }

}
