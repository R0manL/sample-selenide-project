package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Created by R0manL on 03/09/20.
 */

public class BlackoutDatesComponent {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public void selectBlackoutDates(List<LocalDate> dates) {
        for (LocalDate date : dates) {
           selectBlackoutDate(date);
        }
    }

    private void selectBlackoutDate(@NotNull LocalDate date) {
        String dateText = date.toString();
        log.info("Select blackout date: '" + dateText + "'.");
        $("#blackout-date-picker").shouldBe(Condition.visible);
        executeJavaScript("$('#blackout-date-picker').datepicker('setDate', new Date(arguments[0], arguments[1], arguments[2]));",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth());
        $("#channel-blackout-date-selector").click();
    }
}
