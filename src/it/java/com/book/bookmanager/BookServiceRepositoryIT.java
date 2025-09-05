package com.book.bookmanager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.repositories.BookRepository;
import com.book.bookmanager.services.BookService;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BookService.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // usa Postgres, non H2 in-memory
public class BookServiceRepositoryIT {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testServiceCanInsertIntoRepository() {
		Book savedBook = bookService.insertNewBook(new Book(null, "book", "author", "category", 10));

		assertThat(bookRepository.findById(savedBook.getId())).isPresent();
	}

	@Test
	public void testServiceCanUpdateRepository() {
		Book savedBook = bookRepository.save(new Book(null, "book", "author", "category", 10));

		Book modifiedBook = bookService.updateBookById(savedBook.getId(),
				new Book(savedBook.getId(), "modified book", "modified author", "modified category", 20));

		assertThat(bookRepository.findById(savedBook.getId())).contains(modifiedBook);
	}
}
