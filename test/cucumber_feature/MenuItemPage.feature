Feature: Menu Item Profile
	This feature file contains all the manu item page tests

Background: 
	Given User is logged in
	
#Requirement 1: Logout should be available
Scenario: Varify Logout link is Available
	When User navigates to menu item page
	Then User sees logout link

#Requirement 2: Items can be added to cart by clicking a button
Scenario: Adding Item to Cart
	When User adds item to cart
	And User clicks add button from the modal to confirm purchase
	Then User sees process order button

#Requirement 3: Verify that clicking the process order button should redirect to the location page
Scenario: Varify Process Order
	When User adds item to cart
	And User clicks add button from the modal to confirm purchase
	And User clicks process order button
	Then User is sent to locations page