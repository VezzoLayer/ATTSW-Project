package com.book.bookmanager.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
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

	@Test
	public void testInsertNewBookShouldSetIdToNullAndReturnsSavedBook() {
		Book toSave = spy(new Book(50L, "tosave", "test", "test", 10));
		Book saved = new Book(1L, "saved", "test", "test", 20);

		when(bookRepository.save(any(Book.class))).thenReturn(saved);

		Book result = bookService.insertNewBook(toSave);

		assertThat(result).isSameAs(saved);

		InOrder inOrder = inOrder(toSave, bookRepository);
		inOrder.verify(toSave).setId(null);
		inOrder.verify(bookRepository).save(toSave);
	}

	@Test
	public void testUpdateBookByIdSetsIdToArgumentAndReturnsSavedBook() {
		Book replacement = spy(new Book(null, "replacement book", "author", "category", 10));
		Book replaced = new Book(1L, "saved book", "author", "category", 20);
		
		when(bookRepository.save(any(Book.class))).thenReturn(replaced);
		
		Book result = bookService.updateBookById(1L, replacement);
		
		assertThat(result).isSameAs(replaced);
		
		InOrder inOrder = inOrder(replacement, bookRepository);
		inOrder.verify(replacement).setId(1L);
		inOrder.verify(bookRepository).save(replacement);
	}
}
