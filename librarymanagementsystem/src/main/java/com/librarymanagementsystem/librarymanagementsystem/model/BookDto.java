package com.librarymanagementsystem.librarymanagementsystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private Integer bookId;
    private  String bookName;
    private  String bookAuthor;
    private  String bookCatagory;
    private  Integer bookPrice;
}
