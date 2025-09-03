package com.book.bookmanager.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.services.BookService;

@Controller
public class BookWebController {

	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String BOOK_ATTRIBUTE = "book";
	private static final String BOOKS_ATTRIBUTE = "books";

	private BookService bookService;

	// Contructor Injection suggerito da SonarCloud
	public BookWebController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Book> allBooks = bookService.getAllBooks();

		model.addAttribute(BOOKS_ATTRIBUTE, allBooks);
		model.addAttribute(MESSAGE_ATTRIBUTE, allBooks.isEmpty() ? "No book to show" : "");

		return "index";
	}

	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable long id, Model model) {
		Book bookById = bookService.getBookById(id);

		model.addAttribute(BOOK_ATTRIBUTE, bookById);
		model.addAttribute(MESSAGE_ATTRIBUTE, bookById == null ? "No book found with id: " + id : "");

		return "edit";
	}

	@GetMapping("/new")
	public String newBook(Model model) {
		model.addAttribute(BOOK_ATTRIBUTE, new Book());
		model.addAttribute(MESSAGE_ATTRIBUTE, "");

		return "edit";
	}

	@PostMapping("/save")
	public String saveBook(Book book) {
		final Long id = book.getId();

		if (id == null) {
			bookService.insertNewBook(book);
		} else {
			bookService.updateBookById(id, book);
		}

		return "redirect:/";
	}
}
