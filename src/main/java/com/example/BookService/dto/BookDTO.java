package com.example.BookService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO
{
    @NotBlank(message = "book name is required")
    private String name;
    @NotBlank(message = "book author is required")
    private String author;
    @NotBlank(message = "book category is required")
    private String category;
}
