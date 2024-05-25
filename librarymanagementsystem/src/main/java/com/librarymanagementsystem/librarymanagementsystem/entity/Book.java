package com.librarymanagementsystem.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_table")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private  String bookName;
    private  String bookAuthor;
    private  String bookCatagory;
    private  Integer bookPrice;
}
