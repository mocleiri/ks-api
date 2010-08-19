Feature: Manage Course Proposals
  # Main goal of this project
  In order to develop curriculum to support student learning
  As a proponent
  I want to create and manage course proposals

  Scenario: Submit Proposal
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I click the link "Authors & Rationale"
    Then I should see "Proposal Title and Rationale"
    When I fill in "Proposal Title" with "Chemistry 201"
    And I fill in "Proposal Rationale" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    And I click the link "Course Logistics"
    Then I should see "Instructor"
    And I fill in the "UBC Instructor" section with "Gordon Bates"
    And I fill in the "Duration" field with "12"
    And I select "Week" from div labeled "Duration Type"
    And I select "3 Credits" from div labeled "Type"
    And I click the link "Add Additional Format"
    And I click the link "Add Activity"
    And I select "Lecture" from div labeled "Activity Type"
    And I type in "Duration" with "12"
    And I select "Week" from div labeled "Duration Type"
    And I enter in Contact Hours with "3" "per week"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Course Information"
    Then I should see "Course Identifier"
    And I fill in Course Identifier with "CHEM" and "201"
    And I fill in the "Short Course Title" section with "INTR PHYS CHEM"
    And I fill in the "Course Title" section with "Introduction to Physical Chemistry"
    And I fill in the "Course Description" textarea section with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I check "UBC Vancouver"
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Active Dates"
    Then I should see "Course active on"
    When I fill in Start Date and End Date
    And I select "Winter Semester of 2010" from a drop down with a heading of "First Expected Offering"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Financials"
    Then I should see "Course Fees"
    And I type in "Revenue" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save"
    And I wait 5 seconds
    And I click the link "Review Proposal"
    Then I should see "Proposal Brief"
    And I submit the proposal
    Then I should see "Proposal has been routed to workflow"
    And I follow "Ok"

  Scenario: Save Complete Proposal
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I click the link "Authors & Rationale"
    Then I should see "Proposal Title and Rationale"
    When I fill in "Proposal Title" with "Chemistry 201"
    And I fill in "Proposal Rationale" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    And I click the link "Course Logistics"
    Then I should see "Instructor"
    And I fill in the "Instructor" section with "Gordon Bates"
    And I fill in the "Duration" field with "12"
    And I select "Week" from div labeled "Duration Type"
    And I select "3 Credits" from div labeled "Type"
    And I click the link "Add Additional Format"
    And I click the link "Add Activity"
    And I select "Lecture" from div labeled "Activity Type"
    And I type in "Duration" with "12"
    And I select "Week" from div labeled "Duration Type"
    And I enter in Contact Hours with "3" "per week"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Course Information"
    Then I should see "Course Identifier"
    And I fill in Course Identifier with "CHEM" and "201"
    And I fill in the "Short Course Title" section with "INTR PHYS CHEM"
    And I fill in the "Course Title" section with "Introduction to Physical Chemistry"
    And I fill in the "Course Description" textarea section with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I check "UBC Vancouver"
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Active Dates"
    Then I should see "Course active on"
    When I fill in Start Date and End Date
    And I select "Winter Semester of 2010" from a drop down with a heading of "First Expected Offering"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Financials"
    Then I should see "Course Fees"
    And I type in "Revenue" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save"
    And I wait 5 seconds
    And I click the link "Review Proposal"
    Then I should see "Proposal Brief"


  Scenario: I can select a department
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    When I click the link "Governance"
    Then I should see "Curriculum Oversight"
    And I type in "Curriculum Oversight" with "Zoo"
    And I select "Department of Zoology" from an autocomplete list

  Scenario: I can fill in the active dates
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    When I click the link "Active Dates"
    Then I should see "Start Date"
    When I fill in Start Date and End Date

  Scenario: I can log in as different users
    Given I am logged out
    And I am on the home page
    When I log in with user "admin" and password "admin"
    And I wait for the page to load
    Then I should see "admin"
    When I am on logout
    When I log in with user "eric" and password "eric"
    And I wait for the page to load
    Then I should see "eric"

  Scenario: I can see action list
    Given I am logged out
    And I am on the home page
    When I log in with user "admin" and password "admin"
    And I wait for the page to load
    Then I should see "admin"
    And I select the Action List
    And I should see "Action List"
    And I should see "Action Requested"

  Scenario: select multiple items from action list
    Given I am logged out
    Given I am logged out
    And I am on the home page
    When I log in with user "edwardgrant" and password "edwardgrant"
    Then I should see "Home"
    And I click My Action List
    And I select the Action List
    Then I should see "Chemistry 201"
    When I follow "3012"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3013"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3014"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3015"
    And I switch to opened proposal
    Then I should see "New Course"


    




    
    