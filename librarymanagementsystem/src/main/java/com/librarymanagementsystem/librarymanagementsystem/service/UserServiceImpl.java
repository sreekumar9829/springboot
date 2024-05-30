package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.entity.User;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.UserDto;
import com.librarymanagementsystem.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public String addUser(UserDto userDto) throws UserNotFoundException {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return "data added successfully";
    }

    @Override
    public Page<UserDto> getAll(int offset, int pageSize) throws UserNotFoundException {
        Pageable pageable = PageRequest.of(offset, pageSize);
        Page<User> userList = userRepository.findAll(pageable);
        List<UserDto> userDto = userList.stream().map(user -> {
            UserDto userDtoList = new UserDto();
            userDtoList.setUserEmail(user.getUserEmail());
            userDtoList.setUserName(user.getUserName());
            userDtoList.setUserId(user.getUserId());
            userDtoList.setPassword(user.getPassword());
            return userDtoList;
        }).collect(Collectors.toList());

        return new PageImpl<>(userDto, PageRequest.of(offset, pageSize), userList.getTotalElements());
    }

    @Override
    public String delete(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("user is not present");
        }
        userRepository.delete(user.get());

        return "user deleted sucessfully";
    }

    @Override
    public String updateUser(UserDto userDto) throws UserNotFoundException {
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setUserEmail(userDto.getUserEmail());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return "User updated successfully";
    }

    @Override
    public UserDto getUserById(Integer id) throws UserNotFoundException {
       Optional<User> userList=userRepository.findById(id);
       if (!userList.isPresent()){
           throw  new UserNotFoundException("user not found");
       }
        User user=userList.get();
        UserDto userDto=new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
