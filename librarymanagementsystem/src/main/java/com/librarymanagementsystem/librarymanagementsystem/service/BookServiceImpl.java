package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.entity.Book;
import com.librarymanagementsystem.librarymanagementsystem.entity.User;
import com.librarymanagementsystem.librarymanagementsystem.exception.BooknOtFoundException;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.BookDto;
import com.librarymanagementsystem.librarymanagementsystem.model.UserDto;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookPageRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements  BookService{
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookPageRepository bookPageRepository;
    @Override
    public Page<BookDto> getAll(Integer offset, Integer pageSize, Integer id, String userName, String password) throws BooknOtFoundException {
        // Fetch the user by ID
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new BooknOtFoundException("User not found");
        }

        User user = userOptional.get();

        // Validate username and password
        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            Pageable pageable = PageRequest.of(offset, pageSize);

            // Fetch paginated list of books
            Page<Book> bookPage = bookPageRepository.findAll(pageable);

            if (bookPage.isEmpty()) {
                throw new BooknOtFoundException("No books are available");
            }

            // Map Book entities to BookDto objects
            List<BookDto> bookDtoList = bookPage.getContent().stream().map(book -> {
                BookDto bookDto = new BookDto();
                bookDto.setBookId(book.getBookId());
                bookDto.setBookCatagory(book.getBookCatagory());
                bookDto.setBookAuthor(book.getBookAuthor());
                bookDto.setBookName(book.getBookName());
                bookDto.setBookPrice(book.getBookPrice());
                return bookDto;
            }).collect(Collectors.toList());

            // Return a paginated list of BookDto objects
            return new PageImpl<>(bookDtoList, pageable, bookPage.getTotalElements());
        } else {
            throw new BooknOtFoundException("Invalid username or password");
        }
    }

    @Override
    public String addBook(Integer id, String userName, String password, BookDto bookDto) throws BooknOtFoundException {
       Optional<User>userList=userRepository.findById(id);
       User user=userList.get();
        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {

            Book book=new Book();
            book.setBookAuthor(bookDto.getBookAuthor());
            book.setBookCatagory(bookDto.getBookCatagory());
            book.setBookName(bookDto.getBookName());
            book.setBookPrice(bookDto.getBookPrice());
            book.setBookId(bookDto.getBookId());
            bookRepository.save(book);
            return "book added successfully";
        } else {
            throw new BooknOtFoundException("Invalid username or password");
        }

    }

    @Override
    public Page<BookDto> serachByName(Integer offset, Integer pageSize, Integer id, String userName, String password, String bookName) throws BooknOtFoundException {
        Pageable pageable=PageRequest.of(offset,pageSize);
        Optional<User> user=userRepository.findById(id);
        User userList=user.get();
        if (userList.getUserName().equals(userName)&&userList.getPassword().equals(password)){
          Page <Book> bookList=bookPageRepository.findByBookName(bookName,pageable);
          List<BookDto> bookDto=bookList.stream().map(book -> {
              BookDto bookDtoList=new BookDto();
              bookDtoList.setBookAuthor(book.getBookAuthor());
              bookDtoList.setBookCatagory(book.getBookCatagory());
              bookDtoList.setBookName(book.getBookName());
              bookDtoList.setBookPrice(book.getBookPrice());
              bookDtoList.setBookId(book.getBookId());
              return bookDtoList;
          }).collect(Collectors.toList());
            return new PageImpl<>(bookDto, PageRequest.of(offset, pageSize), bookList.getTotalElements());
        }else{
            throw new BooknOtFoundException("Invalid username or password");
        }


    }

    @Override
    public Page<BookDto> sortByPrice(Integer offset, Integer pageSize, Integer id, String userName, String password) throws BooknOtFoundException {
        Pageable pageable=PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC, "bookPrice"));
        Optional<User> user=userRepository.findById(id);
        User userDetails=user.get();
        if (userDetails.getUserName().equals(userName)&&userDetails.getPassword().equals(password)){
            Page<Book> bookPage=bookPageRepository.findAll(pageable);
            List<BookDto> bookDto=bookPage.stream().map(book -> {
                BookDto bookDtoList=new BookDto();
                bookDtoList.setBookAuthor(book.getBookAuthor());
                bookDtoList.setBookCatagory(book.getBookCatagory());
                bookDtoList.setBookName(book.getBookName());
                bookDtoList.setBookPrice(book.getBookPrice());
                bookDtoList.setBookId(book.getBookId());
                return bookDtoList;
            }).collect(Collectors.toList());
            return new PageImpl<>(bookDto,PageRequest.of(offset,pageSize),bookPage.getTotalElements());
        }else{
            throw new BooknOtFoundException("user is not available");
        }

    }



}


