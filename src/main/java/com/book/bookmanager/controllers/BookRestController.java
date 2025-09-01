package com.book.bookmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
