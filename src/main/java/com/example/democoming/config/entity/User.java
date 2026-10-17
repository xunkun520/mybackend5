package com.example.democoming.config.entity;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

@Service
public class User {
    @NotBlank(message = "名字不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

