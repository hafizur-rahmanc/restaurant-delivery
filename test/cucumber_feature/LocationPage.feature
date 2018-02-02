Feature: Location Profile
	This feature file contains all the location page tests

Background:
		Given User has item in cart
#Requirement 1: Each location should have a button to be selected
Scenario: Varify that each location has a select location button
	When User processes order
	Then User should see a button for each Location
	
#Requirement 2: Selecting a location should send to the review order page
Scenario: Varify that user is sent to the review order page
	When User processes order
	And User selects a location
	Then User is sent to the review order page