package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import myp.maven.pages.RegisterPage;
import org.testng.annotations.Test;

import java.util.UUID;

@Epic("Демонстраційний проєкт")
@Feature("Реєстрація та логін користувача")
public class RegisterLoginTest {

    @Test(description = "Реєстрація та логін нового користувача")
    @Story("Створення нового користувача та успішний вхід до системи")
    @Description("Тест генерує випадкові креденшиали, реєструє нового користувача, перевіряє alert, входить в систему і перевіряє відображення username")
    public void registerNewUserAndLogin() {
        String firstName = "Test" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String lastName = "Test" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String userName = "Test" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String password = "Test)y@" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        new RegisterPage()
                .openPage()
                .verifyRegisterPage()
                .fillRegistrationForm(firstName, lastName, userName, password)
                .clickRegister()
                .verifyRegistrationAlertContains("User Registered Successfully.")
                .clickBackToLogin()
                .verifyLoginPage()
                .login(userName, password)
                .verifySuccessfulLogin(userName);
    }
}
