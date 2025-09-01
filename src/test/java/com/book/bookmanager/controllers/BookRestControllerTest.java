package com.book.bookmanager.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.services.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookRestController.class)
public class BookRestControllerTest {

	@Autowired
	private MockMvc mvc;

	// @MockBean deprecated in favor of @MockitoBean
	@MockitoBean
	private BookService bookService;

	@Test
	public void testAllBooksEmpty() throws Exception {
		this.mvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));
		// Check lista JSON vuota
	}

	@Test
	public void testAllBooksWhenThereIsSome() throws Exception {
		when(bookService.getAllBooks()).thenReturn(
				asList(new Book(1L, "1st book", "test", "test", 10), new Book(2L, "2nd book", "test", "test", 20)));
		this.mvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].title", is("1st book")))
				.andExpect(jsonPath("$[0].author", is("test"))).andExpect(jsonPath("$[0].category", is("test")))
				.andExpect(jsonPath("$[0].price", is(10))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].title", is("2nd book"))).andExpect(jsonPath("$[1].author", is("test")))
				.andExpect(jsonPath("$[1].category", is("test"))).andExpect(jsonPath("$[1].price", is(20)));
	}

	@Test
	public void testOneBookByIdWithExistingBook() throws Exception {
		when(bookService.getBookById(anyLong())).thenReturn(new Book(1L, "1st book", "test", "test", 10));
		this.mvc.perform(get("/api/books/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.title", is("1st book")))
				.andExpect(jsonPath("$.author", is("test"))).andExpect(jsonPath("$.category", is("test")))
				.andExpect(jsonPath("$.price", is(10)));
	}
}
