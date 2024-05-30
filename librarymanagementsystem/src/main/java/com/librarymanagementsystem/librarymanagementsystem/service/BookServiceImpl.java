package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.entity.Book;
import com.librarymanagementsystem.librarymanagementsystem.entity.User;
import com.librarymanagementsystem.librarymanagementsystem.exception.BooknOtFoundException;
import com.librarymanagementsystem.librarymanagementsystem.exception.UserNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.model.BookDto;
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
    @Override
    public Page<BookDto> getAll(Integer offset, Integer pageSize, Integer id, String userName, String password) throws BooknOtFoundException,UserNotFoundException {
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
            Page<Book> bookPage = bookRepository.findAll(pageable);

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
            throw new UserNotFoundException("Invalid username or password");
        }
    }

    @Override
    public String addBook(Integer id, String userName, String password, BookDto bookDto) throws BooknOtFoundException,UserNotFoundException {
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
            throw new UserNotFoundException("Invalid username or password");
        }

    }

    @Override
    public List<BookDto> serachByName(Integer id, String userName, String password, String bookName) throws BooknOtFoundException,UserNotFoundException {
        Optional<User> user=userRepository.findById(id);
        User userList=user.get();
        if (userList.getUserName().equals(userName)&&userList.getPassword().equals(password)){
          List <Book> bookList=bookRepository.findByBookName(bookName);
          List<BookDto> bookDto=bookList.stream().map(book -> {
              BookDto bookDtoList=new BookDto();
              bookDtoList.setBookAuthor(book.getBookAuthor());
              bookDtoList.setBookCatagory(book.getBookCatagory());
              bookDtoList.setBookName(book.getBookName());
              bookDtoList.setBookPrice(book.getBookPrice());
              bookDtoList.setBookId(book.getBookId());
              return bookDtoList;
          }).collect(Collectors.toList());
            return bookDto;
        }else{
            throw new UserNotFoundException("Invalid username or password");
        }


    }

    @Override
    public List<BookDto> sortByPrice(Integer id, String userName, String password) throws BooknOtFoundException,UserNotFoundException{
        Optional<User> user=userRepository.findById(id);
        User userDetails=user.get();
        if (userDetails.getUserName().equals(userName)&&userDetails.getPassword().equals(password)){
            List<Book> bookPage=bookRepository.findAll();
            List<BookDto> bookDto=bookPage.stream().map(book -> {
                BookDto bookDtoList=new BookDto();
                bookDtoList.setBookAuthor(book.getBookAuthor());
                bookDtoList.setBookCatagory(book.getBookCatagory());
                bookDtoList.setBookName(book.getBookName());
                bookDtoList.setBookPrice(book.getBookPrice());
                bookDtoList.setBookId(book.getBookId());
                return bookDtoList;
            }).collect(Collectors.toList());
            return bookDto;
        }else{
            throw new UserNotFoundException("user is not available");
        }

    }



}


