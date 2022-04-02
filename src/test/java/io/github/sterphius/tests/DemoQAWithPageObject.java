package io.github.sterphius.tests;

import com.codeborne.selenide.Configuration;
import io.github.sterphius.pages.RegistrationPage;
import io.github.sterphius.pages.components.CalendarComponent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class DemoQAWithPageObject {

    RegistrationPage registrationPage = new RegistrationPage();
    CalendarComponent calendarComponent = new CalendarComponent();

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "firefox";
    }

    @AfterAll
    static void afterAll() {
        closeWebDriver();
    }

    private final String
            firstName = "FirstName",
            lastName = "LastName",
            mail = "1@mail.ru",
            genderRadioPick = "Male",
            mobilePhone = "7123123456",
            birthdayYear = "1993",
            birthdayMonth = "September",
            birthdayDay = "14",
            subject = "Physics",
            hobbiesSport = "Sports",
            currentAddress = "sample address",
            fileName = "img/10_reasons.png",
            state = "NCR",
            city = "Delhi";

    private final HashMap<String, String> formSubmit = new HashMap<>() {{
        put("Student Name", firstName + " " + lastName);
        put("Student Email", mail);
        put("Gender", genderRadioPick);
        put("Mobile", mobilePhone);
        put("Date of Birth", "14 September,1993");
        put("Subjects", subject);
        put("Hobbies", hobbiesSport);
        put("Picture", Paths.get(fileName).getFileName().toString());
        put("Address", currentAddress);
        put("State and City", state + " " + city);
    }};

    @Test
    void fillFormTest() {
        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(mail)
                .chooseGenderInWrapper(genderRadioPick)
                .setMobilePhone(mobilePhone)
                .setBirthDate(birthdayYear, birthdayMonth, birthdayDay)
                .setSubjectBySendKeys(subject)
                .setHobby(hobbiesSport)
                .uploadPicture(fileName)
                .setCurrentAddress(currentAddress)
                .selectStateFromDropDownList(state)
                .selectCityFromDropDownList(city)
                .clickSubmitButton()
                .checkForm(formSubmit);
    }
}