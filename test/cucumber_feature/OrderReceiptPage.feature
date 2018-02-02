Feature: Order Receipt
	This feature file contains all the order receipt tests

Background:
	Given User navigated to their order receipt page
	
#Requirement #1: Complete receipt information should be displayed
Scenario: Verify that order receipt information displayed correctly
	Then User sees receipt information for each item

#Requirement #2: Logout should be available
#Requirement #3: Link to see menu items once more
#Requirement #4: Link to view all order made previously
Scenario: Verify all the available links
	Then User sees logout link
	And User sees menu items link
	And User sees past orders link