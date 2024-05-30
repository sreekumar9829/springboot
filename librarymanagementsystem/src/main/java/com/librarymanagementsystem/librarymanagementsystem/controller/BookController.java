package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.exception.BooknOtFoundException;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.BookDto;
import com.librarymanagementsystem.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping()
    public PagedModel<EntityModel<BookDto>> getAllBooks(
            @RequestParam Integer offset,
            @RequestParam Integer pageSize,
            @RequestParam Integer id,
            @RequestParam String username,
            @RequestParam String password,
            PagedResourcesAssembler<BookDto> assembler) throws BooknOtFoundException,UserNotFoundException {

        Page<BookDto> bookDto = bookService.getAll(offset, pageSize, id, username, password);
        PagedModel<EntityModel<BookDto>> resource = assembler.toModel(bookDto);
        return resource;
    }

    @PostMapping()
    public ResponseEntity<String> addBooks(
            @RequestParam Integer id,
            @RequestParam String username,
            @RequestParam String password,
            @RequestBody BookDto bookDto) throws BooknOtFoundException,UserNotFoundException{

        String response = bookService.addBook(id, username, password, bookDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchbyname")
    public ResponseEntity<List<BookDto>> searchByBookName(
            @RequestParam Integer id,
            @RequestParam String bookname,
            @RequestParam String username,
            @RequestParam String password
            ) throws BooknOtFoundException, UserNotFoundException {

         List<BookDto> bookDto = bookService.serachByName(id, username, password,bookname);
         return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }
    @GetMapping("/sortbyprice")
    public ResponseEntity<List<BookDto>> sortByPrice(
            @RequestParam Integer id,
            @RequestParam String username,
            @RequestParam String password
          ) throws BooknOtFoundException,UserNotFoundException {

        List<BookDto> bookDto = bookService.sortByPrice(id, username, password);

        return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }

}
