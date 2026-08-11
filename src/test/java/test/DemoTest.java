package test;

import io.qameta.allure.testng.AllureTestNg;
import myp.maven.pages.MainPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})

public class DemoTest {

    @Test ( description = "Успішна відправка форми" )
    public void testCompleteTextBox() {
        MainPage mainPage = new MainPage();

        mainPage.openPage()
                .goToTextBox()
                .fillFullForm(
                        "Yehor Ohiichenko",
                        "test@example.com",
                        "Dnipro, Ukraine",
                        "Represents - demo test ."
                )
                .submitForm()
                .verifyAllData("Yehor Ohiichenko", "test@example.com");
    }
}