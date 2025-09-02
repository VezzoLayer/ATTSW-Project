package com.book.bookmanager.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.services.BookService;

@Controller
public class BookWebController {

	private BookService bookService;

	// Contructor Injection suggerito da SonarCloud
	public BookWebController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Book> allBooks = bookService.getAllBooks();

		model.addAttribute("books", allBooks);
		model.addAttribute("message", allBooks.isEmpty() ? "No book to show" : "");

		return "index";
	}

	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable long id, Model model) {
		Book bookById = bookService.getBookById(id);

		model.addAttribute("book", bookById);
		model.addAttribute("message", bookById == null ? "No book found with id: " + id : "");

		return "edit";
	}
}
