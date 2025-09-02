package com.book.bookmanager.controllers;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import com.book.bookmanager.services.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookWebController.class)
public class BookWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;

	@MockitoBean
	private BookService bookService;

	@Test
	public void testHomePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertThat(page.getTitleText()).isEqualTo("Books");
	}

	@Test
	public void testHomePageWithNoBooks() throws Exception {
		when(bookService.getAllBooks()).thenReturn(emptyList());
		
		HtmlPage page = this.webClient.getPage("/");
		
		assertThat(page.getBody().getTextContent()).contains("No books");
	}
}
