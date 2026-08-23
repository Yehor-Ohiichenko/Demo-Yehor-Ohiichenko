package myp.maven.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormPage {

    // Локатори заголовків та лейблів
    private final SelenideElement mainTitle = $x("//h1[@class='text-center']");
    private final SelenideElement subTitle = $x("//h5");
    
    private final SelenideElement nameLabel = $("#userName-label");
    private final SelenideElement emailLabel = $("#userEmail-label");
    private final SelenideElement mobileLabel = $("#userNumber-label");
    private final SelenideElement dobLabel = $("#dateOfBirth-label");
    private final SelenideElement subjectsLabel = $("#subjects-label");
    private final SelenideElement hobbiesLabel = $x("//label[text()='Hobbies']");
    private final SelenideElement pictureLabel = $("#subjects-label"); // примітка: на сторінці дублюється id, але знайдемо
    private final SelenideElement stateCityLabel = $("#stateCity-label");

    // Поля введення та кнопки
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement maleRadioButton = $x("//label[@for='gender-radio-1']");
    private final SelenideElement submitButton = $("#submit");
    private final SelenideElement mobileInput = $("#userNumber");
    // Поля телефон та календар
    private final SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
    private final SelenideElement prevMonthButton = $(".react-datepicker__navigation--previous");
    private final SelenideElement targetDay = $(".react-datepicker__day--001");
    // Предмети, хобі та адреса
    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement hobbiesReadingCheckbox = $x("//label[@for='hobbies-checkbox-2']"); // Кликаємо по лейблу чекбокса для надійності
    private final SelenideElement currentAddressInput = $("#currentAddress");
    // State та City
    private final SelenideElement stateInput = $("#react-select-3-input");
    private final SelenideElement cityInput = $("#react-select-4-input");
    // Modal та результати
    private final SelenideElement modalTitle = $("#example-modal-sizes-title-lg");
    private final SelenideElement resultsTable = $(".table-dark");
    private final SelenideElement closeButton = $("#closeLargeModal");

    @Step("Відкриття сторінки Practice Form")
    public PracticeFormPage openPage() {
        open("https://demoqa.com/automation-practice-form");
        return this;
    }

    @Step("Перевірка заголовоків та лейблів на сторінці")
    public PracticeFormPage verifyFormLabels() {
        mainTitle.shouldBe(Condition.visible).shouldHave(Condition.text("Practice Form"));
        subTitle.shouldBe(Condition.visible).shouldHave(Condition.text("Student Registration Form"));
        
        nameLabel.shouldHave(Condition.text("Name"));
        emailLabel.shouldHave(Condition.text("Email"));
        mobileLabel.shouldHave(Condition.text("Mobile"));
        dobLabel.shouldHave(Condition.text("Date of Birth"));
        subjectsLabel.shouldHave(Condition.text("Subjects"));
        hobbiesLabel.shouldHave(Condition.text("Hobbies"));
        stateCityLabel.shouldHave(Condition.text("State and City"));
        return this;
    }

    @Step("Заповнення базових полів: ім'я = {firstName}, прізвище = {lastName}, email = {email}")
    public PracticeFormPage fillBasicFields(String firstName, String lastName, String email) {
        firstNameInput.shouldBe(Condition.visible).setValue(firstName);
        lastNameInput.setValue(lastName);
        emailInput.setValue(email);
        return this;
    }

    @Step("Вибір гендеру: Male")
    public PracticeFormPage selectGenderMale() {
        maleRadioButton.shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Натискання кнопки Submit")
    public PracticeFormPage clickSubmit() {
        submitButton.scrollTo();
        executeJavaScript("arguments[0].click();", submitButton);
        return this;
    }

    @Step("Перевірка, що поле мобільного телефону є невалідним (обов'язкове поле)")
    public void verifyMobileFieldError() {
        Boolean isValid = executeJavaScript("return arguments[0].validity.valid;", mobileInput);
        assert Boolean.FALSE.equals(isValid) : "Поле мобільного телефону мало б бути невалідним, але воно валідне!";
    }

        @Step("Введення мобільного телефону: {number}")
    public PracticeFormPage fillMobileNumber(String number) {
        mobileInput.shouldBe(Condition.visible).setValue(number);
        return this;
    }

    @Step("Вибір дати народження через календар (переключення назад 4 рази)")
    public PracticeFormPage selectDateOfBirth() {
        dateOfBirthInput.click();
        
        // Натискаємо стрілочку "Previous Month" 4 рази з паузою в 1 секунду
        for (int i = 0; i < 4; i++) {
            prevMonthButton.click();
            sleep(1000); // 1 секунда паузи за твоїм ТЗ
        }
        
        targetDay.click();
        return this;
    }

    @Step("Введення предметів: {subjects}")
    public PracticeFormPage fillSubjects(String... subjects) {
    for (String subject : subjects) {
        subjectsInput.shouldBe(Condition.visible).setValue(subject);
        subjectsInput.pressEnter(); // Натискаємо Enter, щоб випадаючий список обрав предмет
    }
    return this;
    }

    @Step("Вибір хобі: Reading")
    public PracticeFormPage selectHobbiesReading() {
        hobbiesReadingCheckbox.shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Введення поточної адреси: {address}")
    public PracticeFormPage fillCurrentAddress(String address) {
        currentAddressInput.shouldBe(Condition.visible).setValue(address);
        return this;
    }

    @Step("Вибір штату: {state}")
    public PracticeFormPage selectState(String state) {
        stateInput.scrollTo().setValue(state).pressEnter();
        return this;
    }

    @Step("Вибір міста: {city}")
    public PracticeFormPage selectCity(String city) {
        cityInput.scrollTo().setValue(city).pressEnter();
        return this;
    }

    @Step("Натискання кнопки Submit та очікування 3 секунди")
    public void clickSubmitAndWait() {
        submitButton.scrollTo();
        executeJavaScript("arguments[0].click();", submitButton);
        sleep(3000);
    }

    @Step("Перевірка появи модального вікна з успішною відправкою")
    public PracticeFormPage verifySuccessModalTitle() {
        modalTitle.shouldBe(Condition.visible).shouldHave(Condition.text("Thanks for submitting the form"));
        return this;
    }

    @Step("Перевірка наявності введених даних у таблиці результатів: {email}, {mobile}, {subject}")
    public PracticeFormPage verifySubmittedData(String email, String mobile, String subject) {
        resultsTable.shouldBe(Condition.visible)
                .shouldHave(Condition.text(email), Condition.text(mobile), Condition.text(subject));

        executeJavaScript("arguments[0].style.border='3px solid green'", resultsTable);
        sleep(3000);
        
        return this;
    }

    @Step("Закриття модального вікна кнопкою Close")
    public void closeModal() {
        closeButton.scrollTo();
        executeJavaScript("arguments[0].click();", closeButton);
    }
}