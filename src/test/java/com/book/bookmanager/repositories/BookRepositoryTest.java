package com.book.bookmanager.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.bookmanager.model.Book;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

	@Autowired
	private BookRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testRepositoryContainsExactlyOneBook() {
		Book book = new Book(null, "test", "test", "test", 20);
		Book bookSaved = entityManager.persistFlushFind(book);
		Collection<Book> books = repository.findAll();
		assertThat(books).containsExactly(bookSaved);
	}

	@Test
	public void testFindByBookTitle() {
		Book bookShouldBeFound = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 20));
		Book bookFound = repository.findByTitle("test");
		assertThat(bookFound).isEqualTo(bookShouldBeFound);
	}

	@Test
	public void testFindByTitleAndPrice() {
		entityManager.persistFlushFind(new Book(null, "test", "test", "test", 10));
		Book bookShouldBeFound = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 20));
		List<Book> bookFound = repository.findByTitleAndPrice("test", 20L);
		assertThat(bookFound).containsExactly(bookShouldBeFound);
	}

	@Test
	public void testFindByTitleOrPrice() {
		Book book1 = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 10));
		Book book2 = entityManager.persistFlushFind(new Book(null, "Another Book", "test", "test", 20));
		entityManager.persistFlushFind(new Book(null, "Should Not Be Found", "test", "test", 10));
		List<Book> found = repository.findByTitleOrPrice("test", 20L);
		assertThat(found).containsExactly(book1, book2);
	}

	@Test
	public void testFindAllBooksWithLowPrice() {
		Book book1 = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 10));
		Book book2 = entityManager.persistFlushFind(new Book(null, "Another Book", "test", "test", 20));
		entityManager.persistFlushFind(new Book(null, "Should Not Be Found", "test", "test", 30));
		List<Book> found = repository.findAllBooksWithLowPrice(25L);
		assertThat(found).containsExactly(book1, book2);
	}
}
