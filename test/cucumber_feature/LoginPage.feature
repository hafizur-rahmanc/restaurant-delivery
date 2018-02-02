Feature: Login Profile
	This feature file contains all the login tests
Background:
	Given User is on home page
	When User clicks on login link
	
#Login profile requirements #1 & #2
Scenario: Verify login page input fields
	Then User sees text input field for username
	And User sees masked input field for password

#Login profile requirement #4
Scenario: Verify login page header
	Then User sees login page header as "Restaurant Delivery"

#Login profile requirements #3 & #5
Scenario: Verify that user is able to login using valid username and password
	And User logs in using valid username as "user" and password as "user"
	Then User is sent to menu item page

#Login profile Requirements #3 & #5
Scenario: Verify that Admin is able to login using valid username and password
	And User logs in as Admin using valid username as "admin" and password as "admin"
	Then User is sent to admin home page
	
#Login profile requirements #6
Scenario: Verify that user can not login using invalid credentials
	And User logs in using invalid username as "user" and password as "user123"
	Then User should see error message as "Invalid username or password!"