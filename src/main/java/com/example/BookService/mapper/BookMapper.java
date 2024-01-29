package com.example.BookService.mapper;

import com.example.BookService.dto.BookDTO;
import com.example.BookService.dto.BookFilter;
import com.example.BookService.dto.BookResponse;
import com.example.BookService.model.Book;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(BookMapperDelegate.class)
public interface BookMapper
{
    BookResponse bookToResponse(Book book);

    Book requestToBook(BookDTO request);

    Book filterToBook(BookFilter filter);
}
