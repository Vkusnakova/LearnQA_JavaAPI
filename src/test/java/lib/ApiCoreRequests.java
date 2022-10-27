package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make GET-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make POST-request with token and auth cookie")
    public Response makePostRequestForAuth(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make POST-request")
    public Response makePostRequest(String url, Map<String, String> userData){
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make GET Request for user data")
    public Response makeGetRequestForUserData(String url, String token, String cookie, String userId){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url+userId)
                .andReturn();
    }
    @Step("Make GET Request for user data w/o token and cookie")
    public Response makeGetRequestForUserData(String url, String userId){
        return given()
                .filter(new AllureRestAssured())
                .get(url+userId)
                .andReturn();
    }

    @Step("Make PUT-request with token and auth cookie")
    public Response makePutRequest(String url, String token, String cookie, Map<String,String> editData, String userId){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(url+userId)
                .andReturn();
    }

    @Step("Make PUT-request without token and auth cookie")
    public Response makePutRequest(String url,Map<String,String> editData, String userId){
        return given()
                .filter(new AllureRestAssured())
                .body(editData)
                .put(url+userId)
                .andReturn();
    }

    @Step("Make DELETE-request without token and auth cookie")
    public Response makeDeleteRequest(String url, String token, String cookie, String userId){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .delete(url+userId)
                .andReturn();
    }
}
