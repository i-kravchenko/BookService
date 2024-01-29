package com.example.BookService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookFilter
{
    @NotBlank(message = "book name is required")
    private String name;
    @NotBlank(message = "book author is required")
    private String author;
}
