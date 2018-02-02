Feature: Review Order Profile
	This feature file contains all the review order page tests
	
Background:
	Given User has multiple items in their Cart
	When User selects a location

#Requirement #1: All items should have the image, name and price
Scenario: Verify thar each item has image, name and price
	Then User sees the image, name and price of each item in their cart
	
#Requirement #2: Each item has delete button
Scenario: Varify that each item has delete button
	Then User sees delete button for each item in their cart
	
#Requirement #3: After clicking on confirm, user is sent to payment page
Scenario: Verify that after clicking process, button user is sent to payment page
	And User clicks the process order button
	Then User is sent to payment Page

#Requirement #4: After click on cancel order, user is sent to menu items page
Scenario: Verify that after clicking cancel order, user is sent to menu items page
	And User cancels their purchase
	Then User is sent back to Menu Page

#Requirement #5: After clicking on delete, user should be sent back to Review Order page
Scenario: Verify that removing an item from cart should not display again
	And User clicks on the delete button
	Then User is sent back to review order page
	And User no longer sees that item in the cart