package com.book.bookmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.bookmanager.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	Book findByTitle(String title);

	List<Book> findByTitleAndPrice(String title, long price);

	List<Book> findByTitleOrPrice(String title, long price);
	
}
