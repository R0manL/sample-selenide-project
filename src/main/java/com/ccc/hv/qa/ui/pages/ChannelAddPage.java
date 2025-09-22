package com.ccc.hv.qa.ui.pages;

import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 25/08/20.
 */

public class ChannelAddPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final SelenideElement activeServerGroup = $("[class='panel-collapse in']");
    private static final SelenideElement goToDistributionBtn = $("#gotoDistribution");

    private final BlackoutDatesComponent blackoutDatesComponent = new BlackoutDatesComponent();


    private ChannelAddPage() {
        // None
    }

    public static ChannelAddPage getChannelAddPage() {
        return new ChannelAddPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id='page-title' and normalize-space(text())='Add a Channel']");
    }

    public void createChannel(Channel channel) {
        if (channel instanceof ChannelPublic) { addPublicChannel((ChannelPublic) channel); return;}
        if (channel instanceof ChannelExclusive) { addExclusiveChannel((ChannelExclusive) channel); return;}

        throw new IllegalStateException("Channel is not public nor exclusive.");
    }

    /**
     * Create public channel.
     * Rule1: Both proprietarySchemeName and proprietarySalesOutletID can’t be duplicated for different channels
     * (at least one of these properties must be different).
     * Rule2: Channel name must be unique across all BUs.
     * Rule3: Public channel has shared across all accounts.
     * Note. If 'Exclusive Business Units' list is empty, method automatically select 'All Abrams Books Business Units'.
     * @param channel - channel bean.
     */
    public void addPublicChannel(ChannelPublic channel) {
        log.info("Creating '" + channel.getName() + "' public channel.");
        startChannelCreation();

        setChannelName(channel.getName());
        selectHrvViewMarketMapping(channel.getHrvViewMarketMapping());
        selectHeadquarter(channel.getHeadquarter());
        addChannelMarkets(channel.getChannelMarkets());
        selectTimeZone(channel.getTimeZone());
        selectRetryInterval(channel.getRetryInterval());
        selectThreshold(channel.getThreshold());
        gotoExclusiveSection();

        $("#exclusiveCheck").setSelected(channel.isMakeExclusive());
        if (channel.isMakeExclusive()) { selectExclusiveBU(channel.getExclusiveBusinessUnits()); }
        gotoDistributionSection();

        if (!channel.isMakeExclusive()) {
            $("#advancedKeywords").setSelected(channel.isDistributeByAdvancedKeyword());
            setOnixSaleOutletIDCode(channel.getOnixSalesOutletIDCode());
            setProprietarySchemeName(channel.getProprietarySchemeName());
            setProprietarySalesOutletID(channel.getProprietarySalesOutletID());
        } else {
            log.info("Make exclusive option was selected, skip Distribution section.");
        }
        gotoServerSection();

        addDistributionServers(channel.getDistributionServers());
        gotoBlackoutSection();

        blackoutDatesComponent.selectBlackoutDates(channel.getBlackoutDates());
        gotoContactSection();

        selectContactInformationCheckBox(channel.isAddContactInformation());
        if (channel.isAddContactInformation()) { setContactInformation(channel.getContactInformation()); }
        gotoCommentsSection();

        setComments(channel.getComment());
        gotoDoneSection();

        saveChannel();
    }

    public void addExclusiveChannel(ChannelExclusive channel) {
        log.info("Creating '" + channel.getName() + "' exclusive channel.");

        startChannelCreation();
        setChannelName(channel.getName());
        selectHeadquarter(channel.getHeadquarter());
        addChannelMarkets(channel.getChannelMarkets());
        selectTimeZone(channel.getTimeZone());
        selectRetryInterval(channel.getRetryInterval());
        selectThreshold(channel.getThreshold());
        gotoExclusiveSection();

        goToDistributionBtn.shouldBe(visible);
        SelenideElement makeExclusiveCheckBox = $("#exclusiveCheck");
        if(makeExclusiveCheckBox.isDisplayed()) {
            makeExclusiveCheckBox.setSelected(true);
        }

        selectExclusiveBU(channel.getBusinessUnits());
        gotoDistributionSection();

        setProprietarySchemeName(channel.getProprietarySchemeName());
        setProprietarySalesOutletID(channel.getProprietarySalesOutletID());
        gotoServerSection();

        addDistributionServers(channel.getDistributionServers());
        gotoBlackoutSection();

        blackoutDatesComponent.selectBlackoutDates(channel.getBlackoutDates());
        gotoContactSection();

        selectContactInformationCheckBox(channel.isAddContactInformation());
        if (channel.isAddContactInformation()) { setContactInformation(channel.getContactInformation()); }
        gotoCommentsSection();

        setComments(channel.getComment());
        gotoDoneSection();

        saveChannel();
        log.info("Exclusive channel '" + channel.getName() + "' has been saved.");
    }

    public ChannelAddPage startChannelCreation() {
        activePageContainer.$("#gotoGeneral")
                .shouldBe(visible, Duration.ofMillis(ENV_CONFIG.pageLoadTimeout()))
                .click();
        log.info("Enter Basic Channel Information");

        return this;
    }

    public ChannelAddPage setChannelName (String name) {
        $("#channel-name").val(name);

        return this;
    }

    public ChannelAddPage gotoExclusiveSection() {
        $("#gotoExclusive").click();
        log.info("Step: Make This Channel Exclusive");

        return this;
    }

    @NotNull
    public SelenideElement getChannelNameErrorElm() {
        return $("#channel-name-help");
    }

    @NotNull
    public SelenideElement getChooseBuErrorElm() {
        return $("#channel-exclusive-bus-help");
    }

    public SelenideElement getChannelNameIsTooLongErrorMsgElm() {
        return $x(".//*[@id='channel-name-help' and text()='size must be between 0 and 64']");
    }

    private void selectHrvViewMarketMapping(HrvViewMarketMapping market) {
        if (market != null) $("#marketMapping").selectOptionByValue(market.getOptionValue());
    }

    private void selectHeadquarter(Headquarter hq) {
        if (hq != null) $("#channel-headquarters").selectOption(hq.getOptionValue());
    }

    private void selectTimeZone(TimeZone tz) {
        if (tz != null) $("#channel-time-zone").selectOptionByValue(tz.getOptionValue());
    }

    private void selectRetryInterval(RetryInterval ri) {
        if (ri != null) $("#retry-interval").selectOptionByValue(ri.toString());
    }

    private void selectThreshold(ChannelThreshold threshold) {
        if (threshold != null) $("#threshold").selectOption(threshold.toString());
    }

    public ChannelAddPage selectExclusiveBU(List<BusinessUnit> bUs) {
        if(bUs.isEmpty()) {
            log.info("Select all Business Units.");
            $(".exclusiveDataClick").click();
        } else {
            for (BusinessUnit bu : bUs) {
                String buName = bu.getName();
                log.info("Add " + buName + "  business unit.");
                sleep(Configuration.timeout / 8); // Section should be displayed for selecting
                $("#exclusive-bus").selectOption(buName);
                $("#exclusiveBUSelector").click();
            }
        }

        return this;
    }

    public ChannelAddPage gotoDistributionSection() {
        log.info("Click gotoDistribution");
        sleep(Configuration.timeout / 8); // Button does not work until callback will be received. Wait on that.

        goToDistributionBtn.click();
        log.info("Step: Set Up Your Distribution Rules. If exclusive BU was selected, this section will be skipped.");

        return this;
    }

    private void setOnixSaleOutletIDCode(OnixSalesOutletIdCode id) {
        if (id != null) $("#salesOutlet").val(id.toString());
    }

    private void setProprietarySchemeName(String name) {
        if (name != null) $("#propSalesOutletType").val(name);
    }

    private void setProprietarySalesOutletID(String id) {
        if (id != null) $("#propSalesOutlet").val(id);
    }

    public ChannelAddPage gotoServerSection() {
        $("#gotoServers").click();
        log.info("Step: Set Up Your Distribution Servers.");

        return this;
    }

    public SelenideElement getServerNameErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-server-name-help", errorMsg);
    }

    public SelenideElement getServerPortNumberErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-ftp-port-help", errorMsg);
    }

    public SelenideElement getServerHostnameErrorMsgElm(InputFieldErrorMsg errorMsg) {
        return getErrorMsgElm("channel-ftp-hostname-help", errorMsg);
    }

    private SelenideElement getErrorMsgElm(@NotNull String errorTagId, InputFieldErrorMsg errorMsg) {
        return $x(".//*[@id='" + errorTagId + "' and text()='" + errorMsg.toString() + "']");
    }

    public ChannelAddPage gotoBlackoutSection() {
        $("#gotoBlackouts").click();
        log.info("Step: Add Blackout Dates.");

        return this;
    }

    public SelenideElement getBlackoutSectionTitleElm() {
        return $x(".//*[@data-wizard-section-title and text()='Add Blackout Dates']");
    }

    private void gotoContactSection() {
        $("#gotoContact").click();
        log.info("Step: Add a Channel Contact.");
    }

    private void selectContactInformationCheckBox(boolean setSelected) {
        $("#addContactToggle").setSelected(setSelected);
    }

    private void setContactInformation(Address addr) {
        log.info("Adding Channel Contact information.");
        $("#channel-first-name").val(addr.getFirstName());
        $("#channel-last-name").val(addr.getLastName());
        $("#channel-sm").val(addr.getPhoneNumber());
        $("#channel-email").val(addr.getEmail());
        $("#channel-title").val(addr.getContactTitle());
        $("#channel-address1").val(addr.getAddressOne());
        if(addr.getAddressTwo() != null) $("#channel-address2").val(addr.getAddressTwo());
        $("#channel-city").val(addr.getCity());
        $("#channel-state").val(addr.getState());
        $("#channel-zip").val(addr.getPostalCode());
    }

    private void gotoCommentsSection() {
        $("#gotoComments").click();
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        log.info("Step: Add Comments.");
    }

    private void setComments(String comments) {
        if (comments != null) $("#channelComments").val(comments);
    }

    private void gotoDoneSection() {
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
        $("#gotoDone").click();
    }

    private void saveChannel() {
        log.info("Click save channel.");
        $("#save").click();
        activePageContainer.$("#messageSpan.success").shouldBe(visible); // Without this check, tests will fail on adding second channel.
        log.info("Channel has been saved.");
    }

    private void addChannelMarkets(List<Headquarter> channelMarkets) {
        for (Headquarter headquarter : channelMarkets) {
            String hq = headquarter.getOptionValue();
            log.info("Add '" + hq + "' channel's market.");
            $("#available-footprint-table_filter input").val(hq);
            $("#add-to-footprint-btn").click();
        }
    }

    private void addDistributionServers(List<DistributionServer> distributionServers) {
        for (DistributionServer srv : distributionServers) {
            log.info("Add '" + srv.getName() + "' distribution server.");
            setServerName(srv.getName());

            activeServerGroup.$("select[name='protocol']").selectOptionByValue(srv.getProtocol().name());

            if (srv instanceof DistributionServerFTP) {
                selectServerMode(((DistributionServerFTP) srv).getMode());
                setServerPortNumber(Integer.toString(((DistributionServerFTP) srv).getPort()));
                setServerHostName(((DistributionServerFTP) srv).getHostName());
            }

            if (srv instanceof DistributionServerSFTP) {
                setServerPortNumber(Integer.toString(((DistributionServerSFTP) srv).getPort()));
                setServerHostName(((DistributionServerSFTP) srv).getHostName());
            }

            if (srv instanceof DistributionServerFTPS) {
                selectServerMode(((DistributionServerFTPS) srv).getMode());
                selectServerSecurity(((DistributionServerFTPS) srv).getSecurity());
                setServerPortNumber(Integer.toString(((DistributionServerFTPS) srv).getPort()));
                setServerHostName(((DistributionServerFTPS) srv).getHostName());
            }

            //Click add server (if we have more then 1 value)
            if (distributionServers.indexOf(srv) != distributionServers.size() - 1) {
                clickAddServer();
            }

            log.info("Server has been added.");
        }
    }

    public ChannelAddPage setServerName(@NotNull String name) {
        activeServerGroup.$("#serverName").val(name);

        return this;
    }

    private void selectServerMode(DistributionServerMode mode) {
        if (mode != null) activeServerGroup.$("#ftpModeSelector").selectOptionByValue(mode.getOptionValue());
    }

    private void selectServerSecurity(DistributionServerSecurity security) {
        if (security != null) activeServerGroup.$("#ftpSecuritySelector").selectOptionByValue(security.toString());
    }

    public ChannelAddPage setServerPortNumber(@NotNull String port) {
        activeServerGroup.$("#ftpPort").val(port);

        return this;
    }

    public ChannelAddPage setServerHostName(@NotNull String name) {
        activeServerGroup.$("#ftpHost").val(name);

        return this;
    }

    public ChannelAddPage clickAddServer() {
        $("#addServerGroup").click();

        return this;
    }
}
