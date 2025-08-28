package com.book.bookmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.bookmanager.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
}
