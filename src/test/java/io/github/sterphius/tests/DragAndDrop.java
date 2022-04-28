package io.github.sterphius.tests;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class DragAndDrop {

    @Test
    void dragAndDrop() {
        Selenide.open("https://the-internet.herokuapp.com/drag_and_drop");

        $(byId("column-a")).shouldHave(Condition.text("a"));
        $(byId("column-b")).shouldHave(Condition.text("b"));

        $(byId("column-a")).dragAndDropTo($(byId("column-b")));

        $(byId("column-a")).shouldHave(Condition.text("b"));
        $(byId("column-b")).shouldHave(Condition.text("a"));
    }
}