Feature: admin reservations
  Description: the admin can accept and reject reservations
  Actor: owner

  Scenario:
    Given user is logged in and the user is a admin
    And user clicked reservations button
    Then the reservations should shown successfully


  Scenario:
    Given user clicked accept button
    Then the house will be accepted

  Scenario:
    Given user clicked reject button
    Then the house will be rejected
