package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
    private int user_id;
    private String login_id;
    private String password;
    private int role;

    private int owner_id;
    private int store_id;
    private String owner_name;
    private String owner_phone;

}
