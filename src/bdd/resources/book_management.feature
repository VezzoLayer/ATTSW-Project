Feature: Book management

	Scenario: Create a new book
		Given I am on the home page
		When I click on "New Book"
		And I fill the book form with title "new book", author "new author", category "new category", price "20"
		And I submit the form
		Then I should see "new book", "new author", "new category", "20" in the books table