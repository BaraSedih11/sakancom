Feature: viewing all available houses
  Description: the tenant can see all available houses and see their details or reserve them
  Actor: tenant

Scenario:
  Given the tenant is logged in to the system
  And the tenant presses on apartments

  Then all available apartments should be displayed

Scenario:
  Given the tenant who is logged in to the system presses on apartments
  And the tenant presses on details of a specific house

  Then the details of the house are shown

Scenario:
  Given the tenant who is logged in presses on apartments
  And the tenant presses on the reserve button of any house

  Then a reservation is created