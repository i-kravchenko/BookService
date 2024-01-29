package com.example.BookService.controller;

import com.example.BookService.dto.BookDTO;
import com.example.BookService.dto.BookFilter;
import com.example.BookService.dto.BookResponse;
import com.example.BookService.mapper.BookMapper;
import com.example.BookService.model.Book;
import com.example.BookService.service.BookService;
import com.example.BookService.utils.BeanUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController
{
    private final BookService service;
    private final BookMapper mapper;

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponse>> findByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.findByCategory(category).stream().map(mapper::bookToResponse).toList());
    }

    @GetMapping("/one")
    public ResponseEntity<BookResponse> findOne(@RequestBody @Valid BookFilter filter) {
        return ResponseEntity.ok(mapper.bookToResponse(service.findOne(mapper.filterToBook(filter))));
    }

    @PostMapping
    public ResponseEntity<BookDTO> add(@RequestBody @Valid BookDTO request) {
        Book book = mapper.requestToBook(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.bookToResponse(service.save(book)));
    }

    @PutMapping("/{book}")
    public ResponseEntity<BookDTO> update(@PathVariable Book book, @RequestBody BookDTO request) {
        BeanUtils.copyNonNullProperties(mapper.requestToBook(request), book);
        service.save(book);
        return ResponseEntity.ok(mapper.bookToResponse(book));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
