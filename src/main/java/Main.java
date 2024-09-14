import models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {
    static final String url = "http://94.198.50.185:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        HttpHeaders requestHeaders = new HttpHeaders();

        HttpEntity request = new HttpEntity(null, requestHeaders);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];

        System.out.println("Response: " + response.toString() + "\n");
        System.out.println("Set-Cookie: " + set_cookie + "\n");

        addUser(requestHeaders, set_cookie);
        editUser(requestHeaders, set_cookie);
        deleteUser(requestHeaders, set_cookie);
    }

    private static void addUser(HttpHeaders requestHeaders, String cookie) {
        requestHeaders.add("Cookie", cookie);

        User user = new User(3L, "James", "Brown", (byte) 24);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.print(response.getBody());
    }

    private static void editUser(HttpHeaders requestHeaders, String cookie) {

        requestHeaders.add("Cookie", cookie);

        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 22);
        HttpEntity<User> requestEntity = new HttpEntity<>(updatedUser, requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        System.out.print(response.getBody());
    }

    private static void deleteUser(HttpHeaders requestHeaders, String cookie) {

        requestHeaders.add("Cookie", cookie);

        HttpEntity<User> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url + "/3", HttpMethod.DELETE, requestEntity, String.class);
        System.out.print(response.getBody());
    }
}
