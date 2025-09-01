package com.book.bookmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.services.BookService;

@RestController
public class BookRestController {

	@Autowired
	private BookService bookService;

	@GetMapping("/api/books")
	public List<Book> allBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/api/books/{id}")
	public Book oneBook(@PathVariable long id) {
		return bookService.getBookById(id);
	}

}
