package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;


/**
 * Created by R0manL.
 */

public class ChannelUpdatedPage extends PageBase {

    private ChannelUpdatedPage() {
        // None
    }

    public static ChannelUpdatedPage getChannelUpdatedPage() {
        return new ChannelUpdatedPage();
    }

    public SelenideElement getPageTitleElm() {
        return activePageContainer.$x(".//h1[@id='page-title' and text()='Channel Updated']");
    }

    public SelenideElement getSuccessfullyUpdateMsgElm() {
        return activePageContainer.$x(".//*[@id='messageSpan' and @class='alerts success']");
    }
}
