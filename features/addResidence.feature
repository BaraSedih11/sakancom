Feature: add residence
  Description: the owner can add new residence
  Actor: owner

  Scenario:
    Given user is logged in and the user is a owner
    And user clicked addResidence button
    And user filled all fields with valid residence information and clicked add
    Then the residence should added successfully
