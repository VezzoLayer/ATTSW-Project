package com.book.bookmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.book.bookmanager.services.BookService;

@Controller
public class BookWebController {

	@Autowired
	private BookService bookService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "index";
	}
}
