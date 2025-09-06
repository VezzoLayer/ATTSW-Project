package com.book.bookmanager.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookWebAppSteps {

	private static int port = Integer.parseInt(System.getProperty("server.port", "8080"));
	private static String baseUrl;

	private WebDriver driver;

	@Before
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Given("I am on the home page")
	public void i_am_on_the_home_page() {
		driver.get(baseUrl);
	}

	@When("I click on {string}")
	public void i_click_on(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}

	@When("I fill the book form with title {string}, author {string}, category {string}, price {string}")
	public void i_fill_the_book_form_with_title_author_category_price(String title, String author, String category,
			String price) {
		driver.findElement(By.name("title")).sendKeys(title);
		driver.findElement(By.name("author")).sendKeys(author);
		driver.findElement(By.name("category")).sendKeys(category);
		driver.findElement(By.name("price")).sendKeys(price);
	}

	@When("I submit the form")
	public void i_submit_the_form() {
		driver.findElement(By.name("btn_submit")).click();
	}

	@Then("I should see {string}, {string}, {string}, {string} in the books table")
	public void i_should_see_in_the_books_table(String title, String author, String category, String price) {
		String tableText = driver.findElement(By.id("books_table")).getText();
		assertThat(tableText).contains(title, author, category, price);
	}
}
