package com.shantanux0.linkdin.user_service.DTO;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
}
