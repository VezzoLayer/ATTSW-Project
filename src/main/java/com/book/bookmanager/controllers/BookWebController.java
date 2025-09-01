package com.book.bookmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookWebController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
