package com.example.BookService.mapper;

import com.example.BookService.dto.BookDTO;
import com.example.BookService.dto.BookFilter;
import com.example.BookService.dto.BookResponse;
import com.example.BookService.model.Book;
import com.example.BookService.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BookMapperDelegate implements BookMapper
{
    @Autowired
    @Qualifier("delegate")
    private BookMapper delegate;


    @Override
    public BookResponse bookToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setBookId(book.getId());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory().getName());
        return response;
    }

    @Override
    public Book requestToBook(BookDTO request) {
        Book book = new Book();
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        Category category = new Category();
        category.setName(request.getCategory());
        book.setCategory(category);
        return book;
    }

    @Override
    public Book filterToBook(BookFilter filter) {
        Book book = new Book();
        book.setName(filter.getName());
        book.setAuthor(filter.getAuthor());
        return book;
    }
}
