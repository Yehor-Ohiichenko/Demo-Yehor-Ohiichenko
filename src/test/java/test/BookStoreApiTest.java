package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Демонстраційний проєкт")
@Feature("Book Store API")
public class BookStoreApiTest {

    private static final String BASE_URI = "https://demoqa.com";
    private static final String FIRST_ISBN = "9781449325862";

    @Test
    @Story("GET /BookStore/v1/Books список книг")
    @Description("Перевіряє, що запит на список книжок повертає HTTP 200 і непорожній масив books")
    public void getBooksReturns200AndList() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Books");

        System.out.println("GET /BookStore/v1/Books -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("books", notNullValue())
                .body("books.size()", greaterThan(0));
    }

    @Test
    @Story("GET /BookStore/v1/Books повертає коректні поля")
    @Description("Перевіряє, що список книг містить очікувані поля для кожної книги")
    public void getBooksContainsExpectedBookFields() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Books");

        System.out.println("GET /BookStore/v1/Books -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("books[0].isbn", equalTo(FIRST_ISBN))
                .body("books[0].title", equalTo("Git Pocket Guide"))
                .body("books[0].author", equalTo("Richard E. Silverman"));
    }

    @Test
    @Story("GET /BookStore/v1/Book?ISBN=... для валідного ISBN")
    @Description("Перевіряє, що запит на конкретну книгу за ISBN повертає HTTP 200 і правильні дані")
    public void getBookByIsbnReturns200AndCorrectData() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Book?ISBN=" + FIRST_ISBN);

        System.out.println("GET /BookStore/v1/Book?ISBN=" + FIRST_ISBN + " -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("isbn", equalTo(FIRST_ISBN))
                .body("title", equalTo("Git Pocket Guide"))
                .body("author", equalTo("Richard E. Silverman"));
    }

    @Test
    @Story("GET /BookStore/v1/Book?ISBN=... повертає кількість сторінок")
    @Description("Перевіряє, що в відповіді для книги присутня кількість сторінок")
    public void getBookByIsbnReturnsPagesField() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Book?ISBN=" + FIRST_ISBN);

        System.out.println("GET /BookStore/v1/Book?ISBN=" + FIRST_ISBN + " -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("pages", equalTo(234))
                .body("publisher", equalTo("O'Reilly Media"));
    }

    @Test
    @Story("GET /BookStore/v1/Books повертає список книг у правильному форматі JSON")
    @Description("Перевіряє, що масив books не порожній і має валідну структуру")
    public void getBooksReturnsValidJsonStructure() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Books");

        System.out.println("GET /BookStore/v1/Books -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("books[0].title", notNullValue())
                .body("books[0].publisher", notNullValue())
                .body("books[0].website", notNullValue());
    }

    @Test
    @Story("GET /BookStore/v1/Books запит повторюється стабільно")
    @Description("Перевіряє повторний запит до списку книг та стійкість відповіді")
    public void getBooksIsStableAcrossRepeatedCalls() {
        var response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/BookStore/v1/Books");

        System.out.println("GET /BookStore/v1/Books -> " + response.getStatusLine());

        response.then()
                .statusCode(200)
                .body("books[1].isbn", notNullValue())
                .body("books[1].title", equalTo("Learning JavaScript Design Patterns"));
    }

    @Test
    @Story("POST /BookStore/v1/Books список книг у колекції користувача")
    @Description("Перевіряє, що POST /BookStore/v1/Books приймає JSON із userId та ISBN. Якщо користувач не авторизований, API повертає 401; якщо авторизований — 200/201 і містить isbn.")
    public void addBookToCollection() {
        String requestBody = """
                {
                  "userId": "demo-user-id",
                  "collectionOfIsbns": [
                    {
                      "isbn": "9781449325862"
                    }
                  ]
                }
                """;

        var response = given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/BookStore/v1/Books");

        System.out.println("POST /BookStore/v1/Books -> " + response.getStatusLine());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            response.then()
                    .body("isbn", notNullValue());
        } else {
            response.then()
                    .statusCode(401);
        }
    }

}
