package test;

import myp.maven.pages.WebTablesPage;
import org.testng.annotations.Test;

public class WebTablesTest {

    @Test(description = "Тест перевіряє відкриття сторінки Web Tables, заповнення модальної форми та перевірку появі запису в таблиці")
    public void testAddANewRecord() {
        WebTablesPage tablesPage = new WebTablesPage();

        tablesPage.openPage()
                .verifyPageTitle()
                .clickAddButton()
                .verifyModalTitle()
                .fillForm(
                        "Test user",
                        "Test-user2",
                        "testQA1@example.com",
                        "21",
                        "1999",
                        "Department of Testing"
                )
                .submitForm()
                .verifyRecordInTable("Test user", "testQA1@example.com");
    }

    @Test(description = "Тест перевіряє редагування вже існуючого в таблиці запису")
    public void testEditRecord() {
        WebTablesPage tablesPage = new WebTablesPage();

        // 1. Відкриваємо сторінку
        tablesPage.openPage()
                .verifyPageTitle();

        // 2. Одразу шукаємо дефолтного користувача Cierra за її email і тиснемо Edit
        tablesPage.editRecord("cierra@example.com")
                .verifyModalTitle();

        // 3. Змінюємо дані
        tablesPage.clearAndFillForm(
                        "Test use3",
                        "Test-user4",
                        "testQA2@example.com",
                        "23",
                        "1998",
                        "Department of Testing2"
                )
                .submitForm();

        // 4. Перевіряємо, що в таблиці з'явилися нові дані
        tablesPage.verifyRecordInTable("Test use3", "testQA2@example.com");
    }
}