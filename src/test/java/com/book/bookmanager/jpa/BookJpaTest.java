package com.book.bookmanager.jpa;

import org.junit.Test;
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
		Book saved = entityManager.persistFlushFind(new Book(null, "test", "test", "test", 1000));
	}
}
