import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Ex8 {

    @Test
    public void testWithParam() throws InterruptedException {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = response.get("token");

        JsonPath beforeTaskIsReady = RestAssured
                .given()
                .with()
                .param("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = beforeTaskIsReady.get("status");

        Assertions.assertEquals("Job is NOT ready", status);

        if (status.equals("Job is NOT ready")) {
            int time = response.get("seconds");
            Thread.sleep(time * 1000);}

            JsonPath afterTaskIsReady = RestAssured
                    .given()
                    .with()
                    .param("token", token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            status = afterTaskIsReady.get("status");

            Assertions.assertEquals("Job is ready", status);

            String result = afterTaskIsReady.get("result");

            Assertions.assertTrue(!result.equals(null));

    }
}
