Feature: Admin Item List Profile
	This feature file contains all the Admin Item List tests
	
Background:
	Given Admin is on Admin Item List page
	
Scenario: Verify that Admin is in the correct page
	Then Admin sees page header "Items List"
	
Scenario: Varify that Admin is able to see update and delete button
	Then Admin sees update and delete button

Scenario: Varify invalid form field data
	When Admin enters invalid data to a field
	And Admin clicks the update button
	Then Admin sees alert message "One of the fields is not formatted correctly!"

#Requirement 3 - After update, the Admin should be able to see the updated information
Scenario: Verify that Admin successfully updated account information
	When Admin updates account information successfully
	Then Admin sees the updated information and alert message 
	
Scenario: Verify that Admin can see the add item button
	Then Add button should be displayed