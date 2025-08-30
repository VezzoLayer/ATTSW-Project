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

	public Book getBookById(long id) {
		return bookRepository.findById(id).orElse(null);
	}

	public Book insertNewBook(Book book) {
		book.setId(null);
		return bookRepository.save(book);
	}

	public Book updateEmployeeById(long id, Book replacement) {
		replacement.setId(id);
		return bookRepository.save(replacement);
	}
}
