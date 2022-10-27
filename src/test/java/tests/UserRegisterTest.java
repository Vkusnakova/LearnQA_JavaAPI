package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("This test checks creating a new user with existing email")
    @DisplayName("Test creating user with existing email")
    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Description("This test create a new user")
    @DisplayName("Test positive creating user")
    @Test
    public void testCreateUserSuccessfully() {
        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Description("This test checks creating a new user with incorrect email")
    @DisplayName("Test creating user with incorrect email")
    @Test
    public void testCreateUserWithIncorrectEmail() {
        String email = "vinkotovexample.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @ParameterizedTest
    @CsvSource({"email", "password", "username", "firstName", "lastName"})
    @Description("This test create a new user without one of required parameters")
    @DisplayName("Test creating user without one of required parameters")
    public void testCreateUserWithOutOneParameter(String condition) {
        if (condition.equals("email")) {
            Map<String, String> userData = DataGenerator.getRegistrationDataWithOutOneParameter("email");
            Response responseForCheck = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user", userData);
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: email");
        } else if (condition.equals("password")) {
            Map<String, String> userData = DataGenerator.getRegistrationDataWithOutOneParameter("password");
            Response responseForCheck = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user", userData);
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: password");
        } else if (condition.equals("username")) {
            Map<String, String> userData = DataGenerator.getRegistrationDataWithOutOneParameter("username");
            Response responseForCheck = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user", userData);
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: username");
        } else if (condition.equals("firstName")) {
            Map<String, String> userData = DataGenerator.getRegistrationDataWithOutOneParameter("firstName");
            Response responseForCheck = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user", userData);
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: firstName");
        } else if (condition.equals("lastName")) {
            Map<String, String> userData = DataGenerator.getRegistrationDataWithOutOneParameter("lastName");
            Response responseForCheck = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user", userData);
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: lastName");
        }
    }

    @Description("This test checks creating a new user with short name")
    @DisplayName("Test creating user with short name")
    @Test
    public void testCreateUserWithShortName() {
        String firsName = "a";
        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firsName);
        userData = DataGenerator.getRegistrationData(userData);
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }

    @Description("This test checks creating a new user with long name")
    @DisplayName("Test creating user with long name")
    @Test
    public void testCreateUserWithLongName() {
        String firsName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firsName);
        userData = DataGenerator.getRegistrationData(userData);
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long");
    }
}
