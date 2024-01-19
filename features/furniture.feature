Feature: furniture section
  Description: through this window the tenant can sell and buy furniture
  Actor: tenant

Scenario:
  Given the user who is tenant is logged in to the system
  And the tenant presses on the furniture button

  Then all furniture that are published should be shown

Scenario:
  Given the user who is tenant is logged in to the system and presses on furniture buttons
  And the user presses on buy button

  Then the piece of furniture that he chose should be sold to him

Scenario:
  Given the tenant navigates to the furniture page
  And presses on add new
  And the tenant fills all the required fields
  And presses on sell

  Then the furniture piece should be added to the system

  Scenario:
    Given a tenant navigates to the furniture page
    And the tenant presses on add new
    And the tenant does not fill all the required fields
    And the tenant presses on sell

    Then an Alert is shown