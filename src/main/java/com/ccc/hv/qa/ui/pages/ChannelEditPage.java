package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.InputFieldErrorMsg;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.ui.pages.ChannelUpdatedPage.getChannelUpdatedPage;
import static com.ccc.hv.qa.ui.pages.ChannelViewPage.getChannelViewPage;

/**
 * Created by R0manL.
 */

public class ChannelEditPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private ChannelEditPage() {
        // None
    }

    public static ChannelEditPage getChannelEditPage() {
        return new ChannelEditPage();
    }

    public ChannelEditPage updateChannelNameWith(@NotNull String newValue) {
        return updateInputValueWith("channel-name", newValue);
    }

    public ChannelEditPage updateProprietarySchemeNameWith(@NotNull String newValue) {
        return updateInputValueWith("propSalesOutletType", newValue);
    }

    public ChannelEditPage updateSalesOutletWith(@NotNull String newValue) {
        return updateInputValueWith("salesOutlet", newValue);
    }

    public ChannelEditPage updateProprietarySalesOutletIDWith(@NotNull String newValue) {
        return updateInputValueWith("propSalesOutlet", newValue);
    }

    public ChannelEditPage expandServerSectionWith(@NotNull String serverName) {
        activePageContainer.$x(".//a[@data-parent='#channelAccordion' and normalize-space(text())='" + serverName + "']").click();

        return this;
    }

    public ChannelEditPage updateServerNameWith(@NotNull String newValue) {
        updateInputValueWith("serverName", newValue);

        return this;
    }

    public ChannelEditPage updateFTPPortWith(@NotNull String newValue) {
        updateInputValueWith("ftpPort", newValue);

        return this;
    }

    public ChannelEditPage updateFTPHostWith(@NotNull String newValue) {
        updateInputValueWith("ftpHost", newValue);

        return this;
    }

    public ChannelUpdatedPage clickSave() {
        log.info("Click save channel.");
        activePageContainer.$("button#saveBtn").click();

        return getChannelUpdatedPage();
    }

    public SelenideElement getChannelNameErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-name-help", errorMsg);
    }

    public SelenideElement getProprietarySchemeNameErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("propSalesOutletTypeHelp", errorMsg);
    }

    public SelenideElement getProprietarySalesOutletIDErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("propSalesOutletHelp", errorMsg);
    }

    public SelenideElement getServerNameErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-server-name-help", errorMsg);
    }

    public SelenideElement getFTPPortErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-ftp-port-help", errorMsg);
    }

    public SelenideElement getFTPHostErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-ftp-hostname-help", errorMsg);
    }

    public ChannelViewPage clickCancel() {
        activePageContainer.$("a#cancelEdit").click();

        return getChannelViewPage();
    }

    private SelenideElement getErrorMsgElm(@NotNull String errorTagId, InputFieldErrorMsg errorMsg) {
        return $x(".//*[@id='" + errorTagId + "' and text()='" + errorMsg.toString() + "']");
    }

    private ChannelEditPage updateInputValueWith(@NotNull String inputIdValue, @NotNull String newValue) {
        log.info("Update '" + inputIdValue + "' with '" + newValue + "' value.");
        activePageContainer.$("input#" + inputIdValue).val(newValue);

        return this;
    }
}
