package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {
    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @DisplayName("This test checks that it is impossible do delete user with id = 2")
    @Test
    public void testDeleteUserID2() {
        //login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response authResponse = apiCoreRequests
                .makePostRequestForAuth("https://playground.learnqa.ru/api/user/login", authData);

        String token = authResponse.getHeader("x-csrf-token");
        String cookie = authResponse.getCookie("auth_sid");

        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/", token, cookie, "2");
        Assertions.assertResponseTextEquals(responseDeleteUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);
        Response responseGetUser = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", token, cookie, "2");
        Assertions.assertResponseCodeEquals(responseGetUser, 200);
    }

    @DisplayName("This test checks delete user")
    @Test
    public void testDeleteUser(){
       //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseGenerateUser= RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseGenerateUser.getString("id");
        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response authResponse = apiCoreRequests
                .makePostRequestForAuth("https://playground.learnqa.ru/api/user/login", authData);

        String token = authResponse.getHeader("x-csrf-token");
        String cookie = authResponse.getCookie("auth_sid");
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/", token, cookie, userId);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);


        Response responseGetUser = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", token, cookie, userId);
        Assertions.assertResponseCodeEquals(responseGetUser, 404);
        Assertions.assertResponseTextEquals(responseGetUser, "User not found");

    }
    @DisplayName("This test checks delete user by other user")
    @Test
    public void testDeleteUserByOtherUser(){
        //Generate user 1
        Map<String, String> userData1 = new HashMap<>();
        userData1.put("username", "user1");
        userData1 = DataGenerator.getRegistrationData(userData1);
        JsonPath responseGenerateUser1= RestAssured
                .given()
                .body(userData1)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId1 = responseGenerateUser1.getString("id");

        //Generate user 2
        Map<String, String> userData2 = new HashMap<>();
        userData2.put("username", "user2");
        userData2 = DataGenerator.getRegistrationData(userData2);
        JsonPath responseGenerateUser2= RestAssured
                .given()
                .body(userData2)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId2 = responseGenerateUser2.getString("id");

        //Login as user 2
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData2.get("email"));
        authData.put("password", userData2.get("password"));
        Response authResponse = apiCoreRequests
                .makePostRequestForAuth("https://playground.learnqa.ru/api/user/login", authData);

        String token = authResponse.getHeader("x-csrf-token");
        String cookie = authResponse.getCookie("auth_sid");
        Response responseDeleteUser1 = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/", token, cookie, userId1);
        Assertions.assertResponseCodeEquals(responseDeleteUser1, 400);


        Response responseGetUser1 = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", userId1);
        Assertions.assertResponseCodeEquals(responseGetUser1, 200);
        Assertions.assertJsonByName(responseGetUser1, "username", userData1.get("username"));

        responseGetUser1.prettyPrint();

        Response responseGetUser2 = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", userId2);
        Assertions.assertResponseCodeEquals(responseGetUser2, 200);
        Assertions.assertJsonByName(responseGetUser2, "username", userData2.get("username"));

        responseGetUser2.prettyPrint();

    }

}
