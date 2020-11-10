package github.com.vangreat;

import github.com.vangreat.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class RestTemplate314Application {

    static final String URL_API = "http://91.241.64.178:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        SpringApplication.run(RestTemplate314Application.class, args);
        String cookie = getUser();
        result.append(createUser(cookie));
        result.append(updateUser(cookie));
        result.append(deleteUser(cookie));
        System.out.println(result);
    }

    public static String getUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_API, HttpMethod.GET,
                httpEntity, String.class);
        String cookieGET = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(cookieGET);
        return cookieGET;
    }

    public static String createUser(String cookie) {
        User user = new User((long) 3, "James", "Brown", (byte) 25);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.COOKIE, cookie);

        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_API, HttpMethod.POST,
                httpEntity, String.class);
        String cookiePOST = responseEntity.getBody();
        System.out.println(cookiePOST);
        return cookiePOST;
    }

    public static String updateUser(String cookie) {
        User user = new User((long) 3, "Thomas", "Shelby", (byte) 30);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.COOKIE, cookie);


        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_API, HttpMethod.PUT,
                httpEntity, String.class);
        String cookiePUT = responseEntity.getBody();
        System.out.println(cookiePUT);
        return cookiePUT;
    }

    public static String deleteUser(String cookie) {
        String URL = URL_API + "/3";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, cookie);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.DELETE,
                httpEntity, String.class);
        String cookieDELETE = responseEntity.getBody();
        System.out.println(cookieDELETE);
        return cookieDELETE;
    }
}
