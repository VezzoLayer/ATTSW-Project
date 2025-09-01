package com.book.bookmanager.controllers;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.services.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookWebController.class)
public class BookWebControllerTest {

	@Autowired
	private MockMvc mvc;

	// @MockBean deprecated in favor of @MockitoBean
	@MockitoBean
	private BookService bookService;

	@Test
	public void testStatus200() throws Exception {
		mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testReturnHomeView() throws Exception {
		ModelAndViewAssert.assertViewName(mvc.perform(get("/")).andReturn().getModelAndView(), "index");
	}

	@Test
	public void testHomeViewShowsBooksWithNoMessage() throws Exception {
		List<Book> books = asList(new Book(1L, "test", "test", "test", 10));

		when(bookService.getAllBooks()).thenReturn(books);

		mvc.perform(get("/")).andExpect(view().name("index")).andExpect(model().attribute("books", books));
	}
}
