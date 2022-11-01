package tests;

import io.qameta.allure.Link;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Link("https://playground.learnqa.ru/api/map")
    @Test
    public void testEditJustCreatedTest() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Edit
        String newName = "ChangedName";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), editData, userId);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), userId);
        Assertions.assertJsonByName(responseUserData, "firstName", newName);
    }

    @DisplayName("This test checks that it is impossible to changed data if user is not authorized")
    @Test
    public void testEditNotAuthUserTest() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Edit
        String newName = "ChangedName";
        Map<String, String> editData = new HashMap<>();
        editData.put("lastName", newName);
        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/", editData, userId);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), userId);
        Assertions.assertJsonByName(responseUserData, "lastName", userData.get("lastName"));
    }
    //Generate User

    @DisplayName("This test checks that it is impossible to change data being authorized by other user")
    @Test
    public void testEditByOtherUserTest() {
        //Generate
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //Login as Other User
        Map<String, String> authDataOther = new HashMap<>();
        authDataOther.put("email", "vinkotov@example.com");
        authDataOther.put("password", "1234");
        Response responseGetAuthOtherUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authDataOther);

        //Login as User
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //Edit
        String newName = "ChangedName";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuthOtherUser, "x-csrf-token"),
                        this.getCookie(responseGetAuthOtherUser, "auth_sid"), editData, userId);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), userId);
        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
    }

    @DisplayName("This test checks edit email for email w/o @")
    @Test
    public void testEditEmailForIncorrect() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Edit
        String newEmail = "dffexample.com";
        Map<String, String> editData = new HashMap<>();
        editData.put("newEmail", newEmail);
        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), editData, userId);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), userId);
        Assertions.assertJsonByName(responseUserData, "email", userData.get("email"));

    }

    @DisplayName("This test checks that it is impossible to change name to short name")
    @Test
    public void testEditNameForShortName() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Edit
        String newName = "a";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), editData, userId);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestForUserData("https://playground.learnqa.ru/api/user/", this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"), userId);
        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
    }


}
