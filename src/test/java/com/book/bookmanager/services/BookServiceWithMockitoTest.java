package com.book.bookmanager.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

	@Test
	public void testGetBookByIdCorrectlyFound() {
		Book book = new Book(1L, "test", "test", "test", 10);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		assertThat(bookService.getBookById(1)).isSameAs(book);
	}
	
	@Test
	public void testGetBookByIdNotFound() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThat(bookService.getBookById(1)).isNull();
	}
}
