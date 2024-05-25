package com.librarymanagementsystem.librarymanagementsystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String password;
}
