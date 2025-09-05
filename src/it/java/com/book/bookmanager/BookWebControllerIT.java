package com.book.bookmanager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	@Test
	public void testEditPageNewBook() {
		driver.get(baseUrl + "/new");

		driver.findElement(By.name("title")).sendKeys("new book");
		driver.findElement(By.name("author")).sendKeys("new author");
		driver.findElement(By.name("category")).sendKeys("new category");
		driver.findElement(By.name("price")).sendKeys("80");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(bookRepository.findByTitle("new book")).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(new Book(null, "new book", "new author", "new category", 80L));
	}

	@Test
	public void testEditPageUpdateBook() {
		Book testBook = bookRepository.save(new Book(null, "test book", "test author", "test category", 10));
		driver.get(baseUrl + "/edit/" + testBook.getId());

		final WebElement titleField = driver.findElement(By.name("title"));
		titleField.clear();
		titleField.sendKeys("modified book");

		final WebElement authorField = driver.findElement(By.name("author"));
		authorField.clear();
		authorField.sendKeys("modified author");

		final WebElement categoryField = driver.findElement(By.name("category"));
		categoryField.clear();
		categoryField.sendKeys("modified category");

		final WebElement priceField = driver.findElement(By.name("price"));
		priceField.clear();
		priceField.sendKeys("20");

		driver.findElement(By.name("btn_submit")).click();

		assertThat(bookRepository.findByTitle("modified book")).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(new Book(null, "modified book", "modified author", "modified category", 20L));
	}
}
