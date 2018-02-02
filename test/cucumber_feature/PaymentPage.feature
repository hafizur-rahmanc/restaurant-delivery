Feature: Payment Information
	This feature contains all the payment info test cases

Background:
		Given User confirmed order
		
#Requirement #1: All fields should be formatted correctly
Scenario: Veryfy that all the fields should be formatted correctly
	When User enters credit card "11111111111111111", security code "111", and zipcode "1137"
	And User click the process button
	Then User sees alert message "One of the fields is not formatted correctly!"