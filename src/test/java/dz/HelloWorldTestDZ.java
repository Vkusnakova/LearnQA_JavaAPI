package dz;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HelloWorldTestDZ {

    @Test
    public void testHelloWorldDZ(){
       Response response = RestAssured.
               get("https://playground.learnqa.ru/api/get_text")
               .andReturn();
        System.out.println(response.asString());
    }
}
