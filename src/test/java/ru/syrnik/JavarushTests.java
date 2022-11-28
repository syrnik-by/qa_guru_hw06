package ru.syrnik;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class JavarushTests {

    @BeforeEach
    void beforeEach() {
        Configuration.browserSize = "1920x1080";
        open("https://javarush.com/");
    }

    static Stream<Arguments> javaRushButtonsTest() {
        return Stream.of(
                Arguments.of("QUESTS", List.of("Карта квестов", "Лекции", "CS50", "Android")),
                Arguments.of("TASKS", List.of("Все задачи")),
                Arguments.of("QUIZZES", List.of("Все опросы")),
                Arguments.of("PROJECTS", List.of("Задачи-игры", "Опубликованные игры"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка списка кнопок {1} в меню {0}")
    void javaRushButtonsTest(String dataSiteTourTarget, List<String> buttons) {
        $("[data-site-tour-target=" + dataSiteTourTarget + "]").click();
        $$(".tabs__list li").shouldHave(CollectionCondition.texts(buttons));
    }

    @ValueSource(strings = {"Акция", "Условия акции", "Premium", "Premium Pro", "Отзывы"})
    @ParameterizedTest(name = "Проверка наличия кнопки {0} в меню осенней акции")
    void javaRushAutumnSellButtonsTest(String button) {
        $(".sidebar-group-head__link").click();
        $(".site-header__nav").shouldHave(text(button));
    }

    @CsvSource({
            "О JavaRush, О JavaRush",
            "Как пользоваться курсом, Как пользоваться JavaRush",
            "Отзывы, Отзывы",
            "FAQ, FAQ",
            "Контакты, Свяжитесь с нами",
            "Оферта, Пользовательское соглашение"
    })
    @ParameterizedTest(name = "Проверка что кнопка {0} открывает страницу с текстом {1}")
    void javaRushAboutTest(String button, String expectedText) {
        $(byText("О нас")).click();
        $(".tabs__list").$(byText(button)).parent().click();
        $(".article-head__title").shouldHave(text(expectedText));
    }
}
