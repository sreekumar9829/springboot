package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.exception.BooknOtFoundException;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Page<BookDto> getAll(Integer offset,Integer pageSize,Integer id, String userName,String password) throws BooknOtFoundException,UserNotFoundException;
    String addBook(Integer id,String userName,String password, BookDto bookDto) throws BooknOtFoundException,UserNotFoundException;
    List<BookDto> serachByName(Integer id, String userName, String password, String bookName) throws BooknOtFoundException, UserNotFoundException;
   List<BookDto> sortByPrice(Integer id, String userName,String password) throws BooknOtFoundException,UserNotFoundException;


}
