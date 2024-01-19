Feature: owner houses
  Description: the owner can view his houses
  Actor: owner

Scenario:
  Given user is logged in and the user is a owner
  Then his houses should be shown


Scenario:
  Given user clicked addHouse button and inserts into fields valid data and clicked add
  Then the house should be added to the database
