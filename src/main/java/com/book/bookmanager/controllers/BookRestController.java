package com.book.bookmanager.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.bookmanager.model.Book;

@RestController
public class BookRestController {

	@GetMapping("/api/books")
	public List<Book> allBooks() {
		return Collections.emptyList();
	}

}
