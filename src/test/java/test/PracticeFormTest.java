package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import myp.maven.pages.PracticeFormPage;
import org.testng.annotations.Test;

@Epic("Демонстраційний проєкт")
@Feature("Форма реєстрації студента (Practice Form)")
public class PracticeFormTest {

    @Test
    @Story("Перевірка валідації обов'язкового поля Mobile при відправці форми")
    @Description("Тест відкриває форму, заповнює базові поля, обирає гендер, пропускає обов'язковий телефон та перевіряє помилку валідації")
    public void testPracticeFormFullFilling() {
        PracticeFormPage formPage = new PracticeFormPage();

        formPage.openPage()
                .verifyFormLabels()
                .fillBasicFields(
                        "Test-Student",
                        "Test-Student-F",
                        "Test-Student@example.com"
                )
                .selectGenderMale()
                .fillMobileNumber("0888888889")
                .selectDateOfBirth()
                .fillSubjects("Maths", "Physics")
                .selectHobbiesReading()
                .fillCurrentAddress("Student test address: building 141a, 5th floor, apartment 511")
                .selectState("Uttar Pradesh")
                .selectCity("Merrut")
                .clickSubmit()
                .verifySuccessModalTitle()
                .verifySubmittedData("Test-Student@example.com", "0888888889", "Maths")
                .closeModal();
    }
}