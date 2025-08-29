package com.book.bookmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.bookmanager.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
}
