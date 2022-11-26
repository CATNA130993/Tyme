Feature: Verify smoke cases on demo system

  @demo
  Scenario: [DEMO] QA Automation Test
    Given I can open Chrome
    And I access this page: https://automationteststore.com/
    Then I can see the home page of the Automation test Store
    And I can hover my mouse to APPAREL & ACCESSORIES menu
    Then I can see categories in APPAREL & ACCESSORIES menu
      | CATEGORIES |
      | Shoes      |
      | T-shirts   |
    And I can click on T-shirts categories
    Then I can see T-Shirts page
    And I can select Sort by Price Low > High on T-Shirt page
    And I can verify that all items were sorted
      | SortType | SortName   |
      | Price    | Low > High |
    Then I add to Card an Item on T-shirts
      | ProductID |
      | 121       |
    And I can see the item detailed information


