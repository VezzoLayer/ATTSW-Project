package com.book.bookmanager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.repositories.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookWebControllerIT {

	@Autowired
	private BookRepository bookRepository;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseUrl;

	@Before
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new HtmlUnitDriver();

		// Pulisce il database
		bookRepository.deleteAll();
		bookRepository.flush();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void testHomePageWhenBooksArePresent() {
		Book testBook = bookRepository.save(new Book(null, "test book", "test author", "test category", 10));
		driver.get(baseUrl);

		// La tabella mostra il test book
		assertThat(driver.findElement(By.id("books_table")).getText()).contains("test book", "test author",
				"test category", "10", "Edit");

		driver.findElement(By.cssSelector("a[href*='/edit/" + testBook.getId() + "']"));
	}

	@Test
	public void testHomePageWhenNoBooksArePresent() {
		driver.get(baseUrl);

		assertThat(driver.findElement(By.tagName("body")).getText()).contains("No books");
	}
}
