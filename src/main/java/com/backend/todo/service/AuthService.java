package com.backend.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.todo.dto.LoginRequestDTO;
import com.backend.todo.dto.LoginResponseDTO;
import com.backend.todo.dto.RegisterRequestDTO;
import com.backend.todo.enums.Role;
import com.backend.todo.exception.BadRequestException;
import com.backend.todo.exception.UnauthorizedException;
import com.backend.todo.model.User;
import com.backend.todo.repository.UserRepository;
import com.backend.todo.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public void register(RegisterRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }

    // LOGIN (returns JWT)
    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtUtil.generateToken( user.getUsername(), user.getRole().name() );  

        return new LoginResponseDTO(token);
    }
}
