package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Демонстраційний проєкт")
@Feature("Account API")
public class AccountApiTest {

    private static final String BASE_URI = "https://demoqa.com";

    @Test
    @Story("Створення нового користувача, отримання даних користувача та видалення його")
    @Description("Створює нового користувача через API, отримує дані користувача за UUID, отримує токен і видаляє цього ж користувача")
    public void getCreatedUserAndDeleteIt() {
        String userName = "Delete" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String password = "Delete)y@" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String credentialsBody = String.format("{\"userName\":\"%s\",\"password\":\"%s\"}", userName, password);

        var createUserResponse = given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(credentialsBody)
                .when()
                .post("/Account/v1/User");

        System.out.println("POST /Account/v1/User -> " + createUserResponse.getStatusLine());
        createUserResponse.then().statusCode(201);

        String userId = createUserResponse.jsonPath().getString("userID");
        if (userId == null || userId.isBlank()) {
            userId = createUserResponse.jsonPath().getString("userId");
        }

        var tokenResponse = given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(credentialsBody)
                .when()
                .post("/Account/v1/GenerateToken");

        System.out.println("POST /Account/v1/GenerateToken -> " + tokenResponse.getStatusLine());
        tokenResponse.then()
                .statusCode(200)
                .body("token", notNullValue());

        String token = tokenResponse.jsonPath().getString("token");

        var getUserResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .get("/Account/v1/User/{UUID}", userId);

        System.out.println("GET /Account/v1/User/{UUID} -> " + getUserResponse.getStatusLine());
        getUserResponse.then()
                .statusCode(200)
                .body("username", equalTo(userName));

        var deleteResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/User/{UUID}", userId);

        System.out.println("DELETE /Account/v1/User/{UUID} -> " + deleteResponse.getStatusLine());
        deleteResponse.then().statusCode(204);
    }
}
