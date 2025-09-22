package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.DeleteEventModal.getDeleteEventModal;


public class DeleteEventsPage {

    private DeleteEventsPage() {
        // NONE
    }

    @Step("Delete event: {0}")
    public DeleteEventsPage deleteEvent(@NotNull String eventTitle) {
        getRowWith(eventTitle).shouldBe(Condition.visible).$(".delete").click();
        return getDeleteEventModal().clickDelete();
    }

    public static DeleteEventsPage getDeleteEventsPage() {
        return new DeleteEventsPage();
    }

    public SelenideElement getRowWith(@NotNull String eventTitle) {
        return $(".delete-listing")
                .$x(".//div[contains(@class,'event') and .//div[contains(@class,'title') and normalize-space(text())='" + eventTitle + "']]");
    }
}
