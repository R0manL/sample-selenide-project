package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import com.ccc.hv.qa.ui.enums.DistributionServerSecurity;
import com.ccc.hv.qa.ui.enums.Protocol;
import com.ccc.hv.qa.ui.pojos.Address;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTP;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTPS;
import com.ccc.hv.qa.ui.pojos.DistributionServerSFTP;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.ChannelEditPage.getChannelEditPage;

/**
 * Created by R0manL on 25/08/20.
 */

public class ChannelViewPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private ChannelViewPage() {
        // None
    }

    public static ChannelViewPage getChannelViewPage() {
        return new ChannelViewPage();
    }

    public SelenideElement getPageTitleElm() {
        return $x(".//h1[@id='page-title' and contains(text(),'View ')]");
    }

    public ChannelEditPage clickEditChannel() {
        log.info("Click edit channel.");
        activePageContainer.$("a#business-unit-edit-action").click();

        return getChannelEditPage();
    }

    public SelenideElement getChannelNameValueElm() {
        return $("#channel-name-wrapper div");
    }

    public SelenideElement getValueElm(DropDown dropDown) {
        return $("#" + dropDown.getId() + "-wrapper div");
    }

    public List<String> getChannelMarkets() {
        return Arrays.stream(getValueElm(DropDown.CHANNEL_MARKETS)
                .getText()
                .split("\n"))
                .collect(Collectors.toList());
    }

    public boolean isDistributeByAdvancedKeyword() {
        return getValueElm(DropDown.CHANNEL_KEYWORDS).getText().equals("yes");
    }

    public Address getAddress() {
        return Address.builder()
                .firstName(getValueElm(DropDown.FIRST_NAME).getText())
                .lastName(getValueElm(DropDown.LAST_NAME).getText())
                .phoneNumber(getValueElm(DropDown.PHONE_NUMBER).getText())
                .email(getValueElm(DropDown.EMAIL_ADDRESS).getText())
                .contactTitle(getValueElm(DropDown.TITLE).getText())
                .addressOne(getValueElm(DropDown.ADDRESS1).getText())
                .addressTwo(getValueElm(DropDown.ADDRESS2).getText())
                .city(getValueElm(DropDown.CITY).getText())
                .state(getValueElm(DropDown.STATE).getText())
                .postalCode(getValueElm(DropDown.ZIP).getText())
                .build();
    }

    public void expandServerSection(@NotNull String serverName) {
        SelenideElement serverLinkElm = $x(".//a[contains(@class,'panel-toggle') and normalize-space(text())='" + serverName + "']");
        if (serverLinkElm.has(Condition.cssClass("collapsed"))) {
            log.info("Server " + serverName + " is collapsed, should be expanded.");
            serverLinkElm.click();
        } else {
            log.info("Server " + serverName + " is already expanded. Skip this step.");
        }
        // wait for section to be expanded
        Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout() * 4);
    }

    public SelenideElement getDistributionServerElm(@NotNull String serverName) {
        return $x(".//a[@class='panel-toggle' and normalize-space(text())='" + serverName + "']" +
                "/ancestor::div[@class='row' and @id]");
    }

    public DistributionServerSFTP getSFTPServer(@NotNull String serverName) {
        expandServerSection(serverName);
        SelenideElement serverElm = getDistributionServerElm(serverName);
        return DistributionServerSFTP.builder()
                .name(serverName)
                .protocol(Protocol.valueOf(getProtocolElmOf(serverElm).getText()))
                .port(Integer.parseInt(getPortNumberElmOf(serverElm).getText()))
                .hostName(getHostnameElmOf(serverElm).getText())
                .build();
    }

    public DistributionServerFTP getFTPServer(@NotNull String serverName) {
        expandServerSection(serverName);
        SelenideElement serverElm = getDistributionServerElm(serverName);
        return DistributionServerFTP.builder()
                .name(serverName)
                .mode(DistributionServerMode.fromText(getModeElmOf(serverElm).getText()))
                .protocol(Protocol.valueOf(getProtocolElmOf(serverElm).getText()))
                .port(Integer.parseInt(getPortNumberElmOf(serverElm).getText()))
                .hostName(getHostnameElmOf(serverElm).getText())
                .build();
    }

    public DistributionServerFTPS getFTPSServer(@NotNull String serverName) {
        expandServerSection(serverName);
        SelenideElement serverElm = getDistributionServerElm(serverName);
        return DistributionServerFTPS.builder()
                .name(serverName)
                .security(DistributionServerSecurity.fromText(getSecurityElmOf(serverElm).getText()))
                .mode(DistributionServerMode.fromText(getModeElmOf(serverElm).getText()))
                .protocol(Protocol.valueOf(getProtocolElmOf(serverElm).getText()))
                .port(Integer.parseInt(getPortNumberElmOf(serverElm).getText()))
                .hostName(getHostnameElmOf(serverElm).getText())
                .build();
    }

    public SelenideElement getProtocolElmOf(@NotNull SelenideElement distributionServer) {
        return getDistributionServerAttributeElm(distributionServer, DistributionServerAttribute.PROTOCOL);
    }

    public SelenideElement getPortNumberElmOf(@NotNull SelenideElement distributionServer) {
        return getDistributionServerAttributeElm(distributionServer, DistributionServerAttribute.PORT);
    }

    public SelenideElement getHostnameElmOf(@NotNull SelenideElement distributionServer) {
        return getDistributionServerAttributeElm(distributionServer, DistributionServerAttribute.HOSTNAME);
    }

    public SelenideElement getModeElmOf(@NotNull SelenideElement distributionServer) {
        return getDistributionServerAttributeElm(distributionServer, DistributionServerAttribute.MODE);
    }

    public SelenideElement getSecurityElmOf(@NotNull SelenideElement distributionServer) {
        return getDistributionServerAttributeElm(distributionServer, DistributionServerAttribute.SECURITY);
    }

    public SelenideElement getDistributionServerAttributeElm(@NotNull SelenideElement distributionServer,
                                                             @NotNull DistributionServerAttribute attribute) {
        return distributionServer
                .$x(".//label[contains(@class, 'control-label') and normalize-space(text())='" + attribute + "']" +
                        "/following-sibling::div[contains(@class, 'controls-view')]");
    }


    public enum DropDown {
        CHANNEL_NAME("channel-name"),
        TIME_ZONE("channel-time-zone"),
        RETRY_INTERVAL("retry-interval"),
        THRESHOLD("threshold"),
        MARKET_MAPPING("marketmapping"),
        HEADQUARTERS("headquarters"),
        CHANNEL_MARKETS("sales-territorie"),
        CHANNEL_KEYWORDS("channel-keywords"),
        SALES_OUTLET("channel-sales-outlet"),
        PROPRIETARY_SCHEME_NAME("channel-prop-sales-outlet-type"),
        PROPRIETARY_SALES_OUTLET("channel-prop-sales-outlet"),
        //ADDRESS
        FIRST_NAME("channel-first-name"),
        LAST_NAME("channel-last-name"),
        PHONE_NUMBER("channel-sm"),
        EMAIL_ADDRESS("channel-email"),
        TITLE("channel-title"),
        ADDRESS1("channel-address1"),
        ADDRESS2("channel-address2"),
        CITY("channel-city"),
        STATE("channel-state"),
        ZIP("channel-zip");


        private final String id;

        DropDown(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String toString() {
            return name();
        }
    }

    public enum DistributionServerAttribute {
        PROTOCOL("Protocol"),
        PORT("Port Number"),
        MODE("Mode"),
        SECURITY("Security"),
        HOSTNAME("Hostname");

        private final String attribute;

        DistributionServerAttribute(String attribute) {
            this.attribute = attribute;
        }

        @Override
        public String toString() {
            return this.attribute;
        }
    }


}
