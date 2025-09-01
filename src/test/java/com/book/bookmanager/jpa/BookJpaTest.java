package com.book.bookmanager.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.book.bookmanager.model.Book;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookJpaTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testJpaMapping() {
		Book saved = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 20));
		assertThat(saved.getTitle()).isEqualTo("test");
		assertThat(saved.getAuthor()).isEqualTo("test");
		assertThat(saved.getCategory()).isEqualTo("test");
		assertThat(saved.getPrice()).isEqualTo(20);
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getId()).isPositive();

		// Per vedere identifier generato
		LoggerFactory.getLogger(BookJpaTest.class).info("Saved: {}", saved);
	}
}
