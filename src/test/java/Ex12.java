import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex12 {

    @Test
    public void testHeaders() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();


        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"),
                "Header with name 'x-secret-homework-header' doesn't exist");
        Assertions.assertEquals("Some secret value", headers.getValue("x-secret-homework-header"),
                "Header is different");




    }

}
