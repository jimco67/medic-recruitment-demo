Feature: Create a job offer

  Scenario: Successfully creating a job offer
    Given a recruiter with ID 123
    When they submit a job offer titled "Nurse" located in "Paris" requiring "Pediatrics"
    Then the job offer should be created and returned with the same title


  Scenario: Error when creating a job offer with missing information
    Given a recruiter with ID 123
    When they submit a job offer titled "" located in "Paris" requiring "Pediatrics"
    Then the job offer shouldn't be created and returned "missing information" exception