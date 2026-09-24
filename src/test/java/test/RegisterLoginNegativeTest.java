package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import myp.maven.pages.RegisterPage;
import org.testng.annotations.Test;

@Epic("Демонстраційний проєкт")
@Feature("Негативний сценарій логіну")
public class RegisterLoginNegativeTest {

    @Test(description = "Негативна перевірка логіну")
    @Story("Спроба логіну з невірним username і password")
    @Description("Тест перевіряє, що система показує помилку для невалідних облікових даних")
    public void loginWithInvalidCredentials() {
        new RegisterPage()
                .openLoginPage()
                .verifyLoginPage()
                .login("WrongUserName", "WrongPassword123!")
                .verifyInvalidLoginMessage();
    }
}
