package com.book.bookmanager.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.book.bookmanager.model.Book;
import com.book.bookmanager.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceWithMockitoTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Test
	public void testGetAllBooks() {
		Book book1 = new Book(1L, "first book", "test", "test", 10);
		Book book2 = new Book(2L, "second book", "test", "test", 20);
		when(bookRepository.findAll()).thenReturn(asList(book1, book2));
		assertThat(bookService.getAllBooks()).containsExactly(book1, book2);
	}
}
