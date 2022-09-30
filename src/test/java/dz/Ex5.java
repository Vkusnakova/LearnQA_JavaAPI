package dz;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Ex5 {

    @Test
    public void testSecondMessage(){
        JsonPath response = RestAssured
               .get("https://playground.learnqa.ru/api/get_json_homework")
               .jsonPath();
        ArrayList<LinkedHashMap<String,String>> answer = response.get("messages");

        System.out.println(answer.get(1).get("message"));
    }
}
