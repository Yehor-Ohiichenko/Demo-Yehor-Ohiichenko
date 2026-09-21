package myp.maven.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class SliderPage {

    private final SelenideElement pageTitle = $x("//h1[@class='text-center']");
    private final SelenideElement slider = $("#slider");
    private final SelenideElement sliderValue = $("#sliderValue");

    @Step("Відкриття сторінки Slider")
    public SliderPage openPage() {
        open("https://demoqa.com/slider");
        return this;
    }

    @Step("Перевірка, що відкрилась сторінка Slider")
    public SliderPage verifyPageTitle() {
        pageTitle.shouldBe(Condition.visible).shouldHave(Condition.text("Slider"));
        return this;
    }

    @Step("Переміщення слайдера до значення: {targetValue}")
    public SliderPage moveSliderTo(int targetValue) {
        slider.shouldBe(Condition.visible);

        executeJavaScript(
                "const slider = document.getElementById('slider'); const valueBox = document.getElementById('sliderValue'); slider.value = arguments[0]; slider.setAttribute('value', String(arguments[0])); slider.setAttribute('aria-valuenow', String(arguments[0])); valueBox.value = String(arguments[0]); valueBox.setAttribute('value', String(arguments[0])); slider.dispatchEvent(new Event('input', { bubbles: true })); slider.dispatchEvent(new Event('change', { bubbles: true }));",
                targetValue
        );

        slider.shouldHave(Condition.value(String.valueOf(targetValue)));
        return this;
    }

    @Step("Перевірка, що значення слайдера дорівнює: {expectedValue}")
    public SliderPage verifySliderValue(int expectedValue) {
        slider.shouldHave(Condition.value(String.valueOf(expectedValue)));
        sliderValue.shouldBe(Condition.visible).shouldHave(Condition.value(String.valueOf(expectedValue)));
        return this;
    }
}
