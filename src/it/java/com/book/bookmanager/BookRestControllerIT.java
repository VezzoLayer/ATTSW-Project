package com.book.bookmanager;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.repositories.BookRepository;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookRestControllerIT {

	@Autowired
	private BookRepository bookRepository;

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		RestAssured.port = port;

		// Pulisce il database
		bookRepository.deleteAll();
		bookRepository.flush();
	}

	@Test
	public void testNewBook() {
		// Crea un book con POST
		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(new Book(null, "new book", "author", "category", 10)).when().post("/api/books/new");

		Book savedBook = response.getBody().as(Book.class);

		// Lo legge indietro dalla repository
		assertThat(bookRepository.findById(savedBook.getId())).contains(savedBook);
	}

	@Test
	public void testUpdateBook() throws Exception {
		// Crea un Book con la repository
		Book savedBook = bookRepository
				.save(new Book(null, "original title", "original author", "original category", 10));

		// Lo modifico con PUT
		given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(new Book(null, "modified title", "modified author", "modified category", 20)).when()
				.put("/api/books/update/" + savedBook.getId()).then().statusCode(200).body("id",
						equalTo(savedBook.getId().intValue()), "title", equalTo("modified title"), "author",
						equalTo("modified author"), "category", equalTo("modified category"), "price", equalTo(20));
	}
}
