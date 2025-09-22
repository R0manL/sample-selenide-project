package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;
import static com.ccc.hv.qa.ui.pages.ChannelSelectPage.getChannelSelectPage;

public class DistributionOnHoldOrLockedModal {
    private SelenideElement modalContainerElm = $("#beginODDConfirmationModal");


    private DistributionOnHoldOrLockedModal() {
        // NONE
    }

    public static DistributionOnHoldOrLockedModal getDistributionOnHoldOrLockedModal() {
        return new DistributionOnHoldOrLockedModal();
    }

    public SelenideElement getContent() {
        return modalContainerElm.$(".modal-content");
    }

    public SelenideElement getHeader() {
        return modalContainerElm.$(".modal-header");
    }

    public SelenideElement getBody() {
        return modalContainerElm.$(".modal-body");
    }

    public ChannelSelectPage clickContinueButton() {
        modalContainerElm.$("#oddContinue").click();
        return getChannelSelectPage();
    }

    public ProductDetailsPage clickCancelButton() {
        modalContainerElm.$(".modal-footer").$x("button[text()='Cancel']").click();
        return getProductDetailsPage();
    }
}
