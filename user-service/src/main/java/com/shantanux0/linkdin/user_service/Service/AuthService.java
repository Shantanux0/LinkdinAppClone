package com.shantanux0.linkdin.user_service.Service;


import com.shantanux0.linkdin.user_service.DTO.LoginRequestDto;
import com.shantanux0.linkdin.user_service.DTO.SignupRequestDto;
import com.shantanux0.linkdin.user_service.DTO.UserDto;
import com.shantanux0.linkdin.user_service.Entity.User;
import com.shantanux0.linkdin.user_service.Exception.BadRequestException;
import com.shantanux0.linkdin.user_service.Exception.ResourceNotFoundException;
import com.shantanux0.linkdin.user_service.Repository.UserRepository;

import com.shantanux0.linkdin.user_service.Utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
