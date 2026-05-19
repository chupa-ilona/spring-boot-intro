package spring.springbootintro.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.springbootintro.dto.UserRegistrationRequestDto;
import spring.springbootintro.dto.UserResponseDto;
import spring.springbootintro.exception.RegistrationException;
import spring.springbootintro.mapper.UserMapper;
import spring.springbootintro.model.Role;
import spring.springbootintro.model.RoleName;
import spring.springbootintro.model.User;
import spring.springbootintro.repository.RoleRepository;
import spring.springbootintro.repository.UserRepositoty;
import spring.springbootintro.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoty userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email: "
                    + requestDto.getEmail()
                    + " already exists");
        }
        User user = userMapper.toModel(requestDto);
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RegistrationException("Can't find "
                        + RoleName.ROLE_USER + " by name"));

        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
