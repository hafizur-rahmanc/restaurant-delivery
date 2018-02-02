Feature: Admin User List Profile
	This feature file contains all the Admin User List tests
	
Background:
	Given Admin is on Admin User List page
	
Scenario: Verify that Admin is in the correct page
	Then Admin sees page header "Regular Users List"
	
Scenario: Varify that Admin is able to see update and delete button
	Then Admin sees update and delete button
	
Scenario: Verify that Admin can click the update button and go to Admin get user page
	When Admin clicks the update button of a user
	Then Admin is send to the admin get user page 