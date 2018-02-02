Feature: Admin Get User Profile
	This feature file contains all the Admin Get User tests

Background:
	Given Admin is on the Admin Get User page
	
Scenario: Verify that Admin is in the correct page
	Then Admin sess page header as "User Account Info"

#Requirement 1 - All fields are mandatory
Scenario: All fields are mandatory
	When Admin clears the email field
	And Admin clicks the update button
	Then Admin sees Missing Credentials Message

#Requirement 2 - validation
Scenario: Varify password and re-enter password is same
	When Admin enters different password or re-enter password value
	And Admin clicks the update button 
	Then Admin sees that "Password does not match!"
	
Scenario: Varify invalid form field data
	When Admin enters invalid data to a field
	And Admin clicks the update button
	Then Admin sees alert message "One of the fields is not formatted correctly!"
	
#Requirement 3 - After update, the Admin should be able to see the updated information
Scenario: Verify that Admin successfully updated account information
	When Admin updates account information successfully
	Then Admin sees the updated information and alert message
	
Scenario: Verify that Admin can see the review delete button
	Then Review delete button should be displayed