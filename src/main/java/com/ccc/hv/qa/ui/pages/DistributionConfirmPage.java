package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.*;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.DistributionSuccessPage.getDistributionSuccessPage;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;
import static com.ccc.hv.qa.ui.pages.ChannelSelectPage.getChannelSelectPage;

public class DistributionConfirmPage extends PageBase {
    public static final String DISTRIBUTION_IS_NOT_ALLOWED_MESSAGE = "File type cannot be distributed to this channel. " +
            "Please review the distribution rules defined for your Channel Association. " +
            "Distribution may be blocked for a product that is Mismatched or of an Unknown type.";

    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private DistributionConfirmPage() {
        // None
    }

    public static DistributionConfirmPage getDistributionConfirmPage() {
        return new DistributionConfirmPage();
    }

    public DistributionSuccessPage confirmDistribution() {
        log.info("Click ok (confirm distribution).");
        getOkButton().click();

        return getDistributionSuccessPage();
    }

    public DistributionSuccessPage completeSuccessfulDistribution() {
        DistributionSuccessPage result = confirmDistribution();
        result.distributionSuccessMsgElm().shouldBe(Condition.visible);

        return result;
    }

    public SelenideElement getOkButton() {
        return $x(".//a[text()[normalize-space() = 'OK']]");
    }

    public ProductDetailsPage clickCancel(){
        log.debug("Click Cancel");
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        activePageContainer.$("#cancel").click();

        return getProductDetailsPage();
    }

    public ChannelSelectPage clickBack(){
        log.debug("Click Back");
        $("#back").click();
        return getChannelSelectPage();
    }

    public boolean isOkButtonEnabled() {
        return !Objects.requireNonNull(getOkButton().getAttribute("class"), "Can't find OK button with class attribute.")
                .contains("disabled");
    }

    public ElementsCollection getAllDistributionChannelTables() {
        getOkButton().shouldBe(visible, Duration.ofMillis(Configuration.pageLoadTimeout));
        return $$("table#confirm-distributions-table");
    }

    public boolean isDistributionAllowedTo(@NotNull String channelName, @NotNull String assetName) {
        log.info("Check if 'File type cannot be distributed' has shown for '" + channelName + "' channel, " +
                "'" + assetName + "' asset.");

        String msg = $x(".//table[@id='confirm-distributions-table' " +
                "and .//th[normalize-space(text())='" + channelName + "']]//" +
                "td[normalize-space(text())='" + assetName + "']/following-sibling::td").getOwnText().trim();

        switch(msg) {
            case DISTRIBUTION_IS_NOT_ALLOWED_MESSAGE : return false;
            case "" : return true;
            default : throw new IllegalStateException("Unknown message has been shown for '" + assetName +
                    "' on distribution to '" + channelName + "' channel.");
        }
    }
}