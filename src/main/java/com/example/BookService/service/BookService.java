package com.example.BookService.service;

import com.example.BookService.configuration.properties.AppCacheProperties;
import com.example.BookService.model.Book;
import com.example.BookService.model.Category;
import com.example.BookService.repository.BookRepository;
import com.example.BookService.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BookService {
    private final BookRepository repository;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = AppCacheProperties.CacheNames.BOOKS, key = "#category")
    public List<Book> findByCategory(String category) {
        return repository.findAllByCategoryName(category);
    }

    @Cacheable(value = AppCacheProperties.CacheNames.BOOKS_BY_NAME_AND_AUTHOR, key = "#probe.getName + #probe.getAuthor")
    public Book findOne(Book probe) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "category");
        Example<Book> example = Example.of(probe, matcher);
        return repository.findOne(example).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS, key = "#category", allEntries = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS_BY_NAME_AND_AUTHOR, key = "#book.getName + #book.getAuthor")
    })
    @Transactional
    public Book save(Book book) {
        Category category = categoryRepository.save(book.getCategory());
        book.setCategory(category);
        return repository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS, key = "#category", allEntries = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS_BY_NAME_AND_AUTHOR, allEntries = true)
    })
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
