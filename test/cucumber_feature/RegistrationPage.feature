Feature: Registration Profile
	This feature file contains all the registration tests

Background:
	Given User is on Registration page
	
#Requirement 1 - All fields are mandatory
Scenario: All fields are mandatory
	When User forgets to enter email field
	Then User sees Missing Credentials Message

Scenario: Varify password and re-enter password is same
	When User enters different password and re-enter password value 
	Then User sees that "Password does not match!"
	
Scenario: Varify invalid form field data
	When User enters invalid data to a field
	Then User sees alert message "One of the fields is not formatted correctly!"
	
#Requirement 2 - Page Header says "User Details"
Scenario: Verify Page Header
	Then User sees Registration Page Header "User Details"

#Requirement 3 - After register, the user should be sent to the login page
Scenario: User successfully registers
	When User successfully registers
	Then User is redirected to Login Page