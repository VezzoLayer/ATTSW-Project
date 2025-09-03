package com.book.bookmanager.controllers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlForm;
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

		String expectedTableContent = """
				Books
				ID Title Author Category Price
				1 book1 author1 category1 10 Edit
				2 book2 author2 category2 20 Edit""";

		// replace /t con spazi bianchi e rimuove /r
		assertThat(table.asNormalizedText().replace("\t", " ").replace("\r", "")).isEqualTo(expectedTableContent);

		page.getAnchorByHref("/edit/1");
		page.getAnchorByHref("/edit/2");
	}

	@Test
	public void testEditPageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/edit/1");

		assertThat(page.getTitleText()).isEqualTo("Books");
	}

	@Test
	public void testEditNonExistingBook() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(null);

		HtmlPage page = this.webClient.getPage("/edit/1");

		assertThat(page.getBody().getTextContent()).contains("No book found with id: 1");
	}

	@Test
	public void testEditExistingBook() throws Exception {
		when(bookService.getBookById(1))
				.thenReturn(new Book(1L, "original title", "original author", "original category", 10));
		HtmlPage page = this.webClient.getPage("/edit/1");

		// Get the form that we are dealing with
		final HtmlForm form = page.getFormByName("book_form");

		// check fields are filled with the correct values and modify their values
		form.getInputByValue("original title").setValueAttribute("modified title");
		form.getInputByValue("original author").setValueAttribute("modified author");
		form.getInputByValue("original category").setValueAttribute("modified category");
		form.getInputByValue("10").setValueAttribute("20");

		// Now submit the form by clicking the button and get back the second page
		form.getButtonByName("btn_submit").click();

		// verifica che il book modificato è aggiornato con i valori corretti
		verify(bookService).updateBookById(1L,
				new Book(1L, "modified title", "modified author", "modified category", 20));
	}

	@Test
	public void testEditNewBook() throws Exception {
		HtmlPage page = this.webClient.getPage("/new");

		// Get the form that we are dealing with
		final HtmlForm form = page.getFormByName("book_form");

		// Retrieve fields by their names and change their values
		form.getInputByName("title").setValueAttribute("new title");
		form.getInputByName("author").setValueAttribute("new author");
		form.getInputByName("category").setValueAttribute("new category");
		form.getInputByName("price").setValueAttribute("10");

		// Submit the form by clicking the button and get back the second page.
		form.getButtonByName("btn_submit").click();

		// verifica che il book sia inserito con i valori corretti dal servizio
		verify(bookService).insertNewBook(new Book(null, "new title", "new author", "new category", 10));
	}

	@Test
	public void testHomePageShouldProvideALinkForCreatingANewBook() throws Exception {
		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getAnchorByText("New Book").getHrefAttribute()).isEqualTo("/new");
	}
}
