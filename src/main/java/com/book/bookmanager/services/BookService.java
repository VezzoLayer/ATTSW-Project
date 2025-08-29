package com.book.bookmanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.repositories.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
}
