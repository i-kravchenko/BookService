package com.example.BookService.repository;

import com.example.BookService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{
    List<Book> findAllByCategoryName(String category);
}
