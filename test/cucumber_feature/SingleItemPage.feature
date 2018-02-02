Feature: Item Review Profile
	This feature file contains all the item review page tests
	
Background:
	Given User navigated to review an Item
	
#Requirement #1: Logout should be available
#Requirement #2: The menu page should be the only page with a link to Review Item Page
Scenario: Verify that the logout link is available
	Then logout link should be displayed

#Requirement #3: The user should be able to write a review
#Requirement #4: The user should be able to see the newly created review
Scenario: Verify that user is able to write a review and see it
	When User writes a review
	And User submits a review
	Then User should be able to see the submitted review