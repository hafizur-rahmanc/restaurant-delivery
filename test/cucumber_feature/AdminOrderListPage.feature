Feature: Admin Order List Profile
	This feature file contains all the Admin Order List tests
	
Background:
	Given Admin is on Admin Order List page
	
Scenario: Verify that Admin is in the correct page
	Then Admin sees page header "Order's List"
	
Scenario: Varify that Admin is able to see delete button
	Then Admin sees delete button