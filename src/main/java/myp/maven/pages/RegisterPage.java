package myp.maven.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class RegisterPage {

    private final SelenideElement pageTitle = $x("//h1[@class='text-center']");
    private final SelenideElement firstNameInput = $x("//input[@id='firstname']");
    private final SelenideElement lastNameInput = $x("//input[@id='lastname']");
    private final SelenideElement userNameInput = $x("//input[@id='userName']");
    private final SelenideElement passwordInput = $x("//input[@id='password']");
    private final SelenideElement registerButton = $x("//button[@id='register']");
    private final SelenideElement backToLoginButton = $x("//button[@id='gotologin']");
    private final SelenideElement loginButton = $x("//button[@id='login']");
    private final SelenideElement invalidLoginMessage = $x("//p[contains(text(),'Invalid username or password!')]");
    private final SelenideElement userNameLabelOnProfile = $x("//label[@id='userName-label']");
    private final SelenideElement userNameValueOnProfile = $x("//label[@id='userName-value']");

    @Step("Відкриття сторінки реєстрації")
    public RegisterPage openPage() {
        open("https://demoqa.com/register");
        clearBrowserCookies();
        clearBrowserLocalStorage();
        refresh();
        sleep(1000);
        return this;
    }

    @Step("Перевірка, що відкрилась сторінка реєстрації")
    public RegisterPage verifyRegisterPage() {
        pageTitle.shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldHave(Condition.text("Register"));
        return this;
    }

    @Step("Заповнення форми реєстрації користувача")
    public RegisterPage fillRegistrationForm(String firstName, String lastName, String userName, String password) {
        firstNameInput.shouldBe(Condition.visible).setValue(firstName);
        lastNameInput.shouldBe(Condition.visible).setValue(lastName);
        userNameInput.shouldBe(Condition.visible).setValue(userName);
        passwordInput.shouldBe(Condition.visible).setValue(password);
        return this;
    }

    @Step("Натискання кнопки Register")
    public RegisterPage clickRegister() {
        registerButton.shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Перевірка alert про успішну реєстрацію")
    public RegisterPage verifyRegistrationAlertContains(String expectedText) {
        String text = switchTo().alert().getText();
        if (!text.contains(expectedText)) {
            throw new AssertionError("Alert не містить текст '" + expectedText + "'. Фактичний текст: '" + text + "'");
        }
        switchTo().alert().accept();
        return this;
    }

    @Step("Натискання кнопки Back to Login")
    public RegisterPage clickBackToLogin() {
        backToLoginButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", backToLoginButton);
        sleep(500);
        backToLoginButton.shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Відкриття сторінки логіну")
    public RegisterPage openLoginPage() {
        open("https://demoqa.com/login");
        clearBrowserCookies();
        clearBrowserLocalStorage();
        refresh();
        sleep(1000);
        return this;
    }

    @Step("Перевірка, що відкрилась сторінка логіну")
    public RegisterPage verifyLoginPage() {
        userNameInput.shouldBe(Condition.visible, Duration.ofSeconds(10));
        passwordInput.shouldBe(Condition.visible, Duration.ofSeconds(10));
        loginButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return this;
    }

    @Step("Логін користувача")
    public RegisterPage login(String userName, String password) {
        userNameInput.shouldBe(Condition.visible).setValue(userName);
        passwordInput.shouldBe(Condition.visible).setValue(password);
        loginButton.shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Перевірка повідомлення про невалідні логін/пароль")
    public RegisterPage verifyInvalidLoginMessage() {
        invalidLoginMessage.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Invalid username or password!"));
        return this;
    }

    @Step("Перевірка успішного логіну")
    public void verifySuccessfulLogin(String expectedUserName) {
        userNameLabelOnProfile.shouldBe(Condition.visible).shouldHave(Condition.text("Books :"));
        userNameValueOnProfile.shouldBe(Condition.visible).shouldHave(Condition.text(expectedUserName));
    }
}
