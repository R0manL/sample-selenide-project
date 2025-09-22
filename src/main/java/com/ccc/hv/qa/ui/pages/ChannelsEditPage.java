package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$$;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.ChannelViewPage.getChannelViewPage;

/**
 * Created by R0manL on 25/08/20.
 */

public class ChannelsEditPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement TABLE_HEADER_NAME_ELM = activePageContainer.$x(".//th[contains(@aria-label,'Channel Name') and @aria-controls='channel-table']");


    private ChannelsEditPage() {
        // None
    }

    public static ChannelsEditPage getChannelEditPagePage() {
        return new ChannelsEditPage();
    }

    public ChannelsEditPage filterChannelsBy(@NotNull String text) {
        log.info("Filter channels by '" + text + "' value.");
        activePageContainer.$("#channel-table_filter input").val(text).pressEnter();

        return this;
    }

    public ChannelsEditPage clearFiltering() {
        log.info("Clear filtering.");
        filterChannelsBy("");

        return this;
    }

    public ChannelsEditPage activateChannel(@NotNull String channelName) {
        log.info("Activate channel: " + channelName + "' ");
        getInactiveBtnFor(channelName).click();
        confirmActivationOrDeactivation();

        return this;
    }

    public ChannelsEditPage deactivateChannel(@NotNull String channelName) {
        log.info("Deactivate channel: " + channelName + "' .");
        getActiveBtnFor(channelName).click();
        confirmActivationOrDeactivation();

        return this;
    }

    public SelenideElement getActiveBtnFor(@NotNull String channelName) {
        return activePageContainer.$x(".//td/*[@data-channel-name='" + channelName + "' " +
                "and ./button[contains(@class,'btn-success')]]");
    }

    public SelenideElement getInactiveBtnFor(@NotNull String channelName) {
        return activePageContainer.$x(".//td/*[@data-channel-name='" + channelName + "' " +
                "and ./button[contains(@class,'btn-danger')]]");
    }

    public SelenideElement getNoRecordInTableElm() {
        return activePageContainer.$(".dataTables_empty");
    }

    public ChannelsEditPage clickSortChannelAssociationsByName(boolean ascending) {
        if (ascending ^ isListOfChannelsSortedByNameAscending()) {
            TABLE_HEADER_NAME_ELM.click();
        }

        return this;
    }

    public List<String> getListOfAllChannelNames() {
        ElementsCollection channels = $$(".channel-row a");
        List<String> result = new ArrayList<>();

        // Wait till all Channels will load (names will not be empty).
        for (SelenideElement channel : channels) {
            result.add(channel.shouldNotBe(Condition.empty).getOwnText().trim());
        }

        return result;
    }

    public ChannelViewPage clickOpenChannelWith(@NotNull String channelName) {
        log.info("Open '" + channelName + "' channel.");

        activePageContainer.$x(".//td/a[normalize-space(text())='" + channelName + "']")
                .shouldBe(Condition.enabled)
                .click();

        return getChannelViewPage();
    }

    private boolean isListOfChannelsSortedByNameAscending() {
        String sorted = TABLE_HEADER_NAME_ELM.getAttribute("aria-sort");
        Objects.requireNonNull(sorted, "Can't identify if BU sorted by name. No 'aria-sort' attribute.");

        return "ascending".equals(sorted);
    }

    private void confirmActivationOrDeactivation() {
        activePageContainer.$("button#channel-status-modal-action").click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        activePageContainer.$x(".//*[@id='channel-status-modal']//button[@data-dismiss='modal' and normalize-space(text())='Close']").click();
    }
}
