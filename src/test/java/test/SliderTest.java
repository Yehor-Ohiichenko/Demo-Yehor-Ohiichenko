package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import myp.maven.pages.SliderPage;
import org.testng.annotations.Test;

@Feature("Сторінка Slider")
public class SliderTest {

    @Test
    @Story("Переміщення слайдера до значення 88 та перевірка значення")
    @Description("Відкриває сторінку Slider, перевіряє заголовок, переміщує повзунок до 88 і підтверджує, що значення оновилось")
    public void testSliderMovesToExpectedValue() {
        SliderPage sliderPage = new SliderPage();

        sliderPage.openPage()
                .verifyPageTitle()
                .moveSliderTo(88)
                .verifySliderValue(88);
    }
}
