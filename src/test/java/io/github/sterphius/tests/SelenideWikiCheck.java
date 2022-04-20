package io.github.sterphius.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

/*
    Вопрос:
    Есть ли разница между $("h1 div"); и $("h1").$("div"); - может ли привести к тому что, поиск найдёт разные элементы?
    Если может - приведите пример, когда.
    Ответ:
    $("h1 div") - будет искать первый элемент h1 с div внутри
    $("h1").$("div") - будет искать первый элемент h1 и внутри этого элемента произойдёт попытка найти первый div
*/

public class SelenideWikiCheck {
    //Разработайте следующий автотест:
    //- Откройте страницу Selenide в Github
    //- Перейдите в раздел Wiki проекта
    //- Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions
    //- Откройте страницу SoftAssertions, проверьте что внутри есть пример кода для JUnit5

    String selenideGitHubUrl = "https://github.com/selenide/selenide";

    @Test
    void expectJUnitExampleAtSelenideWiki() {
        Selenide.open(selenideGitHubUrl);
        $(byId("wiki-tab")).click();
        $(byId("wiki-pages-filter")).sendKeys("SoftAssertions");
        $(byAttribute("data-filterable-for", "wiki-pages-filter")).$(byText("SoftAssertions")).click();
        $$("h4").findBy(Condition.text("Using JUnit5 extend test class")).sibling(0)
                .shouldHave(Condition.text("@ExtendWith({SoftAssertsExtension.class})"));
    }
}