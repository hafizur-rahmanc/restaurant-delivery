Feature: Account Info Profile
	This feature file contains all the Account Info tests

Background:
	Given User is on account details page
	
#Requirement 1 - All fields are mandatory
Scenario: All fields are mandatory
	When User clears the email field
	And User clicks the update button
	Then User sees Missing Credentials Message

Scenario: Varify password and re-enter password is same
	When User enters different password or re-enter password value
	And User clicks the update button 
	Then User sees that "Password does not match!"
	
Scenario: Varify invalid form field data
	When User enters invalid data to a field
	And User clicks the update button
	Then User sees alert message "One of the fields is not formatted correctly!"

#Requirement 3 - After update, the user should be able to see the updated information
Scenario: User successfully update account information
	When User updates account information successfully
	Then User sees the updated information and alert message