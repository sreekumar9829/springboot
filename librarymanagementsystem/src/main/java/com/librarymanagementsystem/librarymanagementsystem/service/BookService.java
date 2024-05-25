package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.exception.BooknOtFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Page<BookDto> getAll(Integer offset,Integer pageSize,Integer id, String userName,String password) throws BooknOtFoundException;
    String addBook(Integer id,String userName,String password, BookDto bookDto) throws BooknOtFoundException;
    Page<BookDto> serachByName(Integer offset,Integer pageSize,Integer id, String userName,String password,String bookName) throws BooknOtFoundException;
    Page<BookDto> sortByPrice(Integer offset,Integer pageSize,Integer id, String userName,String password) throws BooknOtFoundException;


}
