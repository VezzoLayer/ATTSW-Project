package com.book.bookmanager.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

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
	public void repositoryContainsExactlyOneBookTest() {
		Book book = new Book(null, "test", "test", "test", 20);
		Book bookSaved = entityManager.persistFlushFind(book);
		Collection<Book> books = repository.findAll();
		assertThat(books).containsExactly(bookSaved);
	}
}
