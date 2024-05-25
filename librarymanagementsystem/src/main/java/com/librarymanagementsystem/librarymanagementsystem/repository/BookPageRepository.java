package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookPageRepository extends PagingAndSortingRepository<Book,Integer> {
   // Page<Book> findAll(Pageable pageable);
    Page<Book>findByBookName(String bookName,Pageable pageable);
    Page<Book>findAll(Pageable pageable);
}
