Feature: user login
  Description: the user logs in and out to the system
  Actor: user


  Scenario:
    Given the user is not logged in
    And the username is equal to <username> and the username is equal to <password>
      | username | password |
      | aydi     | 123      |
      | bara     | 123      |
      | bara     | baraa    |


    Then the user should log in to the system
    And the user type is determined

  Scenario:
    Given user not logged in
    And the password is not equal to <username> or the username is not equal to <password>
      | username | password |
      | aydi     | 123      |
      | bara     | 123      |
      | bara     | baraa    |


    Then show a message indicating that the entered data is false

  Scenario:
    Given the user presses on logout
    Then the user should be logged out of the system