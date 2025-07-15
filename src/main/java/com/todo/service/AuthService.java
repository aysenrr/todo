package com.todo.service;

import com.todo.dto.LoginRequestDto;
import com.todo.dto.LoginResponse;
import com.todo.dto.RegisterRequestDto;

public interface AuthService {

    String register(RegisterRequestDto registerRequestDto);
    LoginResponse login(LoginRequestDto loginRequestDto);

}
