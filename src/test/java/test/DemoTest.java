package test;

import myp.maven.pages.MainPage;
import org.testng.annotations.Test;

public class DemoTest {

    @Test(description = "Успішна відправка форми")
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