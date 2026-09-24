package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import myp.maven.pages.SliderPage;
import org.testng.annotations.Test;

@Epic("Демонстраційний проєкт")
@Feature("Елемент Slider")
public class SliderTest {

    @Test(description = "Переміщення повзунка до очікуваного значення")
    @Story("Переміщення слайдера до потрібного значення")
    @Description("Відкриває сторінку Slider, перевіряє заголовок, переміщує повзунок до 88 і підтверджує, що значення оновилось")
    public void testSliderMovesToExpectedValue() {
        SliderPage sliderPage = new SliderPage();

        sliderPage.openPage()
                .verifyPageTitle()
                .moveSliderTo(88)
                .verifySliderValue(88);
    }
}
