package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonNotHasField(responseUserData, "firstName");
        Assertions.assertJsonNotHasField(responseUserData, "lastName");
        Assertions.assertJsonNotHasField(responseUserData, "email");
    }
    @Test
    public void testGetUserDetailsAuthAsSameUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        String header = responseGetAuth.getHeader("x-csrf-token");
        String cookie = responseGetAuth.getCookie("auth_sid");

        Response responseGetUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        String[] expectedFields = {"username", "firstName", "lastName", "email"};
        Assertions.assertJsonHasFields(responseGetUserData, expectedFields);
    }

    @Description("This test checks that it is only username available if logged as other user")
    @DisplayName("Test checks that only username available for other user")
    @Test
    public void testGetUserDataAuthOtherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequestForAuth("https://playground.learnqa.ru/api/user/login", authData);
        String header = responseGetAuth.getHeader("x-csrf-token");
        String cookie = responseGetAuth.getCookie("auth_sid");

        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateNewUser = apiCoreRequests
                .makePostRequestNewUser("https://playground.learnqa.ru/api/user", userData);
        int userId = this.getIntFromJson(responseCreateNewUser, "id");

        Response responseGetUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", header, cookie, userId);

        Assertions.assertJsonHasField(responseGetUserData, "username");
        Assertions.assertJsonNotHasField(responseGetUserData, "firstName");
        Assertions.assertJsonNotHasField(responseGetUserData, "lastName");
        Assertions.assertJsonNotHasField(responseGetUserData, "email");
    }


    }

