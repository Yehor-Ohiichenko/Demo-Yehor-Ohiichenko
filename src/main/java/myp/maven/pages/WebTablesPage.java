package myp.maven.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class WebTablesPage {

    // Локатори
    private final SelenideElement pageTitle = $x("//h1[@class='text-center']");
    private final SelenideElement addButton = $("#addNewRecordButton");
    private final SelenideElement modalTitle = $("#registration-form-modal");
    
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement ageInput = $("#age");
    private final SelenideElement salaryInput = $("#salary");
    private final SelenideElement departmentInput = $("#department");
    private final SelenideElement submitButton = $("#submit");
    
    // Локатор HTML-таблиці
    private final SelenideElement table = $("table.table-striped");

    // --- Методи для підсвітки через JS ---
    
    private void highlightAndClick(SelenideElement element) {
        element.shouldBe(Condition.visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].style.border='3px solid red'", element);
        sleep(500);
        element.click();
    }

    private void highlightSuccess(SelenideElement element) {
        element.scrollTo();
        executeJavaScript("arguments[0].style.border='3px solid green'", element);
        sleep(1500);
        executeJavaScript("arguments[0].style.border='none'", element);
    }

    // --- Методи сторінки ---

    @Step("Відкриття сторінки Web Tables")
    public WebTablesPage openPage() {
        open("https://demoqa.com/webtables");
        return this;
    }

    @Step("Перевірка заголовку сторінки Web Tables")
    public WebTablesPage verifyPageTitle() {
        pageTitle.shouldHave(Condition.text("Web Tables"));
        return this;
    }

    @Step("Натискання кнопки Add")
    public WebTablesPage clickAddButton() {
        highlightAndClick(addButton);
        return this;
    }

    @Step("Перевірка появи модального вікна 'Registration Form'")
    public WebTablesPage verifyModalTitle() {
        modalTitle.shouldBe(Condition.visible, Duration.ofSeconds(10))
                  .shouldHave(Condition.text("Registration Form"));
        return this;
    }

    @Step("Заповнення форми нового користувача: firstName='{firstName}', lastName='{lastName}', email='{email}'")
    public WebTablesPage fillForm(String firstName, String lastName, String email, String age, String salary, String department) {
        firstNameInput.shouldBe(Condition.visible).setValue(firstName);
        lastNameInput.setValue(lastName);
        emailInput.setValue(email);
        ageInput.setValue(age);
        salaryInput.setValue(salary);
        departmentInput.setValue(department);
        return this;
    }

    @Step("Очищення полів та введення оновлених даних: firstName='{firstName}', lastName='{lastName}', email='{email}'")
    public WebTablesPage clearAndFillForm(String firstName, String lastName, String email, String age, String salary, String department) {
        clearInput(firstNameInput).setValue(firstName);
        clearInput(lastNameInput).setValue(lastName);
        clearInput(emailInput).setValue(email);
        clearInput(ageInput).setValue(age);
        clearInput(salaryInput).setValue(salary);
        clearInput(departmentInput).setValue(department);
        return this;
    }

    private SelenideElement clearInput(SelenideElement element) {
        element.shouldBe(Condition.visible).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        return element;
    }

    @Step("Натискання кнопки Submit у модальному вікні")
    public WebTablesPage submitForm() {
        // Чекаємо видимість кнопки у модальному вікні та клікаємо
        submitButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        highlightAndClick(submitButton);
        return this;
    }

    @Step("Перевірка наявності даних у таблиці: firstName='{firstName}', email='{email}'")
    public WebTablesPage verifyRecordInTable(String firstName, String email) {
        table.shouldHave(Condition.text(firstName), Condition.text(email));

        SelenideElement targetRow = $x(String.format("//td[text()='%s']/parent::tr", email));
        highlightSuccess(targetRow);

        return this;
    }

    @Step("Редагування запису в таблиці для користувача з email: '{email}'")
    public WebTablesPage editRecord(String email) {
        SelenideElement editIcon = $x(String.format("//td[text()='%s']/parent::tr//span[@title='Edit']", email));
        highlightAndClick(editIcon);
        return this;
    }
}