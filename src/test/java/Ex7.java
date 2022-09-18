import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class Ex7 {

    @Test
    public void testLongRedirect(){
        String url = "https://playground.learnqa.ru/api/long_redirect";
        System.out.println(url);
        int responseCode = 0;
        int numberOfRedirescts = 0;

        while(responseCode!= 200) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();
            url = response.getHeaders().getValue("Location");
            responseCode = response.getStatusCode();
            if (responseCode == 200){
                break;
            }
            else {
                System.out.println(url);
                numberOfRedirescts += 1;
            }
        }
        System.out.println(numberOfRedirescts);

    }
}
