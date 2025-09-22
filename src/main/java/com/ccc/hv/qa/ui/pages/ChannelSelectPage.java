package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.DistributionConfirmPage.getDistributionConfirmPage;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;

/**
 * Created by R0manL on 25/08/20.
 */

public class ChannelSelectPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ChannelSelectPage() {
        // None
    }

    public static ChannelSelectPage getChannelSelectPage() {
        return new ChannelSelectPage();
    }

    public ChannelSelectPage selectChannelsBy(@NotNull String ... channelNames) {
        Arrays.stream(channelNames).forEach(channelName -> {
            log.info("Select '" + channelName + "' channel for distribution.");
            SelenideElement checkbox = $x(".//*[normalize-space(.)='" + channelName + "']/input[@type='checkbox']");
            checkbox.scrollIntoView(false);

            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout()); //Note. When we quickly scroll to the checkbox,
            // footer (banner) intercept the checkbox, require to wait when page will render properly.

            checkbox.setSelected(true);
        });

        return this;
    }

    public ChannelSelectPage selectAllChannels() {
        log.info("Selecet all channels for distribution.");
        $("#distribute_to_all").setSelected(true);

        return this;
    }

    public DistributionConfirmPage clickSendFiles() {
        log.info("Click send files.");
        $("#next").click();

        return getDistributionConfirmPage();
    }

    public ProductDetailsPage clickCancel() {
        log.info("Click cancel.");
        $("#cancel").click();

        return getProductDetailsPage();
    }

    public ElementsCollection getAllChannels() {
        log.info("Get list of selected channels.");
        return $$("input[data-distribute-to-this]:not([disabled])");
    }

    public SelenideElement getPageTitle(){
        return $x(".//h1[normalize-space(text())='Select Channels']");
    }
}
