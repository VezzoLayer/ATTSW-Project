package com.book.bookmanager;

import java.util.Objects;

public class Book {
	
	private Long id;
	private String title;
	private String category;
	private long price;
	
	public Book(Long id, String title, String category, long price) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", category=" + category + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, id, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(category, other.category) && Objects.equals(id, other.id) && price == other.price
				&& Objects.equals(title, other.title);
	}
}
