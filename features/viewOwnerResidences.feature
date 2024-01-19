Feature: Owner residences
  Description: the owner can view his residences and when clicked on one of the residences it shows all floors with houses and he can update houses information and he can add residences
  Actor: owner


Scenario:
  Given user is logged in and the user is a owner
  And user clicked residences button
  Then his residences should be shown

Scenario:
  Given user in the residences pane
  And user clicked show houses button of one residence
  Then all available floors in this residence should be shown with the houses inside it

Scenario:
  Given user in the houses of the residence pane
  And user clicked show more button of one house
  Then All information about this house should be shown

Scenario:
  Given user in the house information pane and he updated some values of this house
  And user clicked update button of the update house pane
  Then The information of this house should be updated according to the data that the user puts
