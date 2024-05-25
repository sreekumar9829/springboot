package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.entity.User;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String addUser(UserDto userDto) throws UserNotFoundException;
    Page<UserDto> getAll(int offset,int pageSize) throws  UserNotFoundException;
    String delete(Integer id) throws UserNotFoundException;
    String updateUser(UserDto userDto) throws  UserNotFoundException;

    UserDto getUserById(Integer id) throws UserNotFoundException;

}
