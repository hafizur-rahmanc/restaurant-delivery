Feature: Admin Location List Profile
	This feature file contains all the Admin Location List tests
	
Background:
	Given Admin is on Admin Location List page
#Requirement 1 - Verify the page header	
Scenario: Verify that Admin is in the correct page
	Then Admin sees page header "Locations List"

#Requirement 3 - update and delete button should be displayed	
Scenario: Varify that Admin is able to see update and delete button
	Then Admin sees update and delete button

#Requirement 3 - After update, the Admin should be able to see the updated information
Scenario: Verify that Admin successfully updated account information
	When Admin updates a location successfully
	Then Admin sees the updated information and alert message 
	
Scenario: Verify that Admin can see the add item button
	Then Add button should be displayed