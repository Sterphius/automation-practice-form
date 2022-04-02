package io.github.sterphius.pages.components;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    public void setDate(String year, String month, String day) {
        $(byCssSelector(".react-datepicker__year-select")).selectOptionByValue(year);
        $(byCssSelector(".react-datepicker__month-select")).selectOption(month);
        $(byCssSelector(".react-datepicker__day.react-datepicker__day--0" + day)).click();
    }
}