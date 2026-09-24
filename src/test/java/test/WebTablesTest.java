package test;

import myp.maven.pages.WebTablesPage;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Демонстраційний проєкт")
@Feature("Перевірка та додавання даних у таблиці")
public class WebTablesTest {

    @Test(description = "Перевірка збереження запису в таблиці")
    @Story("Додавання нового запису до таблиці")
    @Description ("Тест відкриває сторінку Web Tables, перевіряє заголовок, тисне Add, заповнює модальну форму та перевіряє появу нового запису в таблиці")
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
    @Story("Редагування існуючого запису в таблиці")
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