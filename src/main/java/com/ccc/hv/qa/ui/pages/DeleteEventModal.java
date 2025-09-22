package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.DeleteEventsPage.getDeleteEventsPage;


public class DeleteEventModal {
    private static final SelenideElement ROOT_ELM = $("#eventDeleteModal .modal-content");


    private DeleteEventModal() {
        // NONE
    }

    public static DeleteEventModal getDeleteEventModal() {
        return new DeleteEventModal();
    }

    @Step("Click delete button")
    public DeleteEventsPage clickDelete() {
        ROOT_ELM.$("button#deleteEventModalAction").click();

        return getDeleteEventsPage();
    }
}
