Feature: Book management

	Scenario: Create a new book
		Given I am on the home page
		When I click on "New Book"
		And I fill the book form with title "new book", author "new author", category "new category", price "20"
		And I submit the form
		Then I should see "new book", "new author", "new category", "20" in the books table
		
	Scenario: Edit an existing book
		Given I have a book with title "old title", author "old author", category "old category", price "10"
		When I go to the home page
		And I click on the edit link for "old title"
		And I update the book form with title "new title", author "new author", category "new category", price "15"
		And I submit the form
		Then I should see "new title", "new author", "new category", "15" in the books table