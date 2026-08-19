package myp.maven.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    // Локатори навігації
    private final SelenideElement textBoxMenu = $x("//span[text()='Text Box']");

    // Локатори полів форми
    private final SelenideElement fullNameInput = $("#userName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement permanentAddressInput = $("#permanentAddress");
    private final SelenideElement submitButton = $("#submit");

    // Локатор результату
    private final SelenideElement outputBlock = $("#output");

    private void highlightAndClick(SelenideElement element) {
        element.scrollTo();
        // Малюємо яскраву рамку через JavaScript
        executeJavaScript("arguments[0].style.border='3px solid red'", element);
        sleep(1000); // Пауза для візуалізації
        element.click();
        executeJavaScript("arguments[0].style.border='none'", element);
    }

    @Step("Відкриття сторінки Text Box")
    public MainPage openPage() {
        open("https://demoqa.com/text-box");
        return this;
    }

    @Step("Перехід до розділу Text Box")
    public MainPage goToTextBox() {
        textBoxMenu.shouldBe(Condition.visible, Duration.ofSeconds(10));
        highlightAndClick(textBoxMenu);
        return this;
    }

    @Step("Заповнення форми: ім'я = {name}, email = {email}")
    public MainPage fillFullForm(String name, String email, String curAddr, String permAddr) {

        fullNameInput.shouldBe(Condition.visible).setValue(name);
        sleep(300);

        emailInput.setValue(email);
        sleep(300);

        currentAddressInput.setValue(curAddr);
        sleep(300);

        permanentAddressInput.setValue(permAddr);
        sleep(300);

        // Знімаємо фокус з останнього поля
        permanentAddressInput.pressTab();

        return this;
    }

    @Step("Натискання кнопки Submit")
    public MainPage submitForm() {

        highlightAndClick(submitButton);
        return this;
    }

    @Step("Перевірка результатів: ім'я = {name}, email = {email}")
    public void verifyAllData(String name, String email) {
        // Перевіряємо конкретні поля результату, які з'являються після сабміту
        $("#name").shouldBe(Condition.visible, Duration.ofSeconds(10)).shouldHave(Condition.text(name));
        $("#email").shouldBe(Condition.visible).shouldHave(Condition.text(email));

        executeJavaScript("arguments[0].style.border='3px solid green'", outputBlock);
        sleep(2000);
    }
}