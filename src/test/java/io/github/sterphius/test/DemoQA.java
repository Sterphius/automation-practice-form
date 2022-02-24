package io.github.sterphius.test;
import com.codeborne.selenide.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DemoQA {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "firefox";
    }

    @Test
    void fillFormTest(){
        String
                baseUrl = "https://demoqa.com/automation-practice-form",
                firstName = "FirstName",
                lastName = "LastName",
                mail = "1@mail.ru",
                genderRadioPick = "Male",
                mobilePhone = "7123123456",
                birthdayYear = "1993",
                birthdayMonth = "September",
                subject1 = "Physics",
                subject2 = "Commerce",
                hobbiesSport = "Sports",
                currentAddress = "sample address",
                fileName = "img/10_reasons.png",
                state = "NCR",
                city = "Delhi";

        Selenide.open(baseUrl);
        SelenideElement hideAdsButton = $(byId("close-fixedban"));

        //general info
        $(byId("firstName")).sendKeys(firstName);
        $(byId("lastName")).sendKeys(lastName);
        $(byId("userEmail")).sendKeys(mail);
        $(byText(genderRadioPick)).click();
        $(byId("userNumber")).sendKeys(mobilePhone);

        //calendar
        $(byId("dateOfBirthInput")).click();
        $(byCssSelector(".react-datepicker__year-select")).selectOptionByValue(birthdayYear);
        $(byCssSelector(".react-datepicker__month-select")).selectOption(birthdayMonth);
        $(byCssSelector(".react-datepicker__day.react-datepicker__day--014")).click();

        //subjects
        $(byId("subjectsInput")).sendKeys("P");
        $(byText(subject1)).click();
        $(byId("subjectsInput")).sendKeys(subject2);
        $(byId("subjectsInput")).pressEnter();

        //hobbies
        $(byText(hobbiesSport)).click();

        $(byId("uploadPicture")).uploadFromClasspath(fileName);

        $(byId("currentAddress")).sendKeys(currentAddress);

        //hide ads
        if (hideAdsButton.isDisplayed()){
            hideAdsButton.click();
        }

        $(byId("state")).scrollTo().click();
        $(byText(state)).click();
        $(byId("city")).click();
        $(byId("stateCity-wrapper")).$(byText(city)).click();
        $(byId("submit")).click();

        HashMap<String, String> formSubmit = new HashMap<>(){{
            put("Student Name", firstName + " " + lastName);
            put("Student Email", mail);
            put("Gender", genderRadioPick);
            put("Mobile", mobilePhone);
            put("Date of Birth", "14 September,1993");
            put("Subjects", subject1 + ", " + subject2);
            put("Hobbies", hobbiesSport);
            put("Picture", Paths.get(fileName).getFileName().toString());
            put("Address", currentAddress);
            put("State and City", state + " " + city);
        }};

        for (HashMap.Entry<String, String> entry : formSubmit.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            $(byCssSelector(".table-responsive")).scrollTo().
                    $(byText(key))
                    .parent().shouldHave(text(key + " " + value));
        }
    }
}
