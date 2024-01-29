package com.example.BookService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookResponse extends BookDTO
{
    private Long bookId;
}
