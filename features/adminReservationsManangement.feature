Feature: admin's view of reservations
  description: the admin can view the current reservations in the system
  actor: admin

  Scenario:
    Given the user who is admin is logged in to the system
    And the user presses on the reservations button in the sidebar
    Then all of the current available reservations should be displayed

