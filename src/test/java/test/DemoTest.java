package test;

import myp.maven.pages.MainPage;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Демонстраційний проєкт")
@Feature("Форма Text Box")
public class DemoTest {

    @Test(description = "Успішна відправка форми Text Box")
    @Story("Відправка форми Text Box з перевіркою введених даних")
    @Description ("Тест відкриває сторінку, переходить до Text Box, заповнює форму та перевіряє появу даних після відправки")
    public void testCompleteTextBox() {
        MainPage mainPage = new MainPage();

        mainPage.openPage()
                .goToTextBox()
                .fillFullForm(
                        "Yehor Ohiichenko",
                        "testQA@example.com",
                        "Dnipro, Ukraine",
                        "Represents - demo test ."
                )
                .submitForm()
                .verifyAllData("Yehor Ohiichenko", "testQA@example.com");
    }
}