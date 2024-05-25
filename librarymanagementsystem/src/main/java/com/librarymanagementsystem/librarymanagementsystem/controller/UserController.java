package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.UserDto;
import com.librarymanagementsystem.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) throws UserNotFoundException{
        String response=userService.addUser(userDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{offset}/{pageSize}")
    public PagedModel<EntityModel<UserDto>> getUserAll(@PathVariable int offset, @PathVariable int pageSize ,
                                                       PagedResourcesAssembler<UserDto> assembler) throws UserNotFoundException{
        Page<UserDto> userDto=userService.getAll(offset,pageSize);
        PagedModel<EntityModel<UserDto>> resources = assembler.toModel(userDto);
        return resources;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) throws UserNotFoundException{
        String response=userService.delete(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto)throws UserNotFoundException{
        userDto.setUserId(id);
        String response=userService.updateUser(userDto);
        return  new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getByID(@PathVariable Integer id) throws UserNotFoundException{
        UserDto userDto=userService.getUserById(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);

    }

}
