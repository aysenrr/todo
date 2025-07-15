package com.todo.service.impl;

import com.todo.dto.LoginRequestDto;
import com.todo.dto.LoginResponse;
import com.todo.dto.RegisterRequestDto;
import com.todo.entity.User;
import com.todo.repository.UserRepository;
import com.todo.security.JwtUtil;
import com.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequestDto registerRequestDto){
        System.out.println("Register metodu çalıştı: " + registerRequestDto);
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setUsername(registerRequestDto.getUsername());
        userRepository.save(user);
        return "User registered succesfully.";
    }

    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto){
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password.");
        }

        var user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String token = jwtUtil.generateToken(user.getUsername());
        return  new LoginResponse(token);
    }
}
