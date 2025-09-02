package com.book.bookmanager.controllers;

import static java.util.Arrays.asList;
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
import org.htmlunit.html.HtmlTable;

import com.book.bookmanager.model.Book;
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

	@Test
	public void testHomePageWithBooksShouldShowThemInATableWithCorrectText() throws Exception {
		when(bookService.getAllBooks()).thenReturn(asList(new Book(1L, "book1", "author1", "category1", 10),
				new Book(2L, "book2", "author2", "category2", 20)));

		HtmlPage page = this.webClient.getPage("/");

		assertThat(page.getBody().getTextContent()).doesNotContain("No books");

		HtmlTable table = page.getHtmlElementById("books_table");

		// replace /t con spazi bianchi e rimuove /r
		assertThat(table.asNormalizedText().replace("\t", " ").replace("\r", ""))
				.isEqualTo("Books\n" + "ID Title Author Category Price\n" + "1 book1 author1 category1 10\n"
						+ "2 book2 author2 category2 20");
	}

	@Test
	public void testEditPageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/edit/1");

		assertThat(page.getTitleText()).isEqualTo("Books");
	}

	@Test
	public void testEditNonExistentingBook() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(null);

		HtmlPage page = this.webClient.getPage("/edit/1");

		assertThat(page.getBody().getTextContent()).contains("No book found with id: 1");
	}
}
