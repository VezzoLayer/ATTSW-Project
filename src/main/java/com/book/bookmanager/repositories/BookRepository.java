package com.book.bookmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.book.bookmanager.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	Book findByTitle(String title);
	
	Book findByCategory(String category);
	
	Book findByAuthor(String string);

	List<Book> findByTitleAndPrice(String title, long price);

	List<Book> findByTitleOrPrice(String title, long price);
	
	@Query("Select b from Book b where b.price < :threshold")
	List<Book> findAllBooksWithLowPrice(@Param("threshold") long threshold);
	
	
}
