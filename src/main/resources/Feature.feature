Feature: User Controller APIs

  Scenario Outline: User Controller - API to create new user
    Given Step "1. Create New User"
    When Set endpoint <createUser> method "<post_method>" and with user payload to create user
    Then Verify status code <status_201>

    Examples:
      | createUser                                 | post_method | status_201 |
      | "/gemEcosystemUser/userManagement/v1/user" | post        |   201      |