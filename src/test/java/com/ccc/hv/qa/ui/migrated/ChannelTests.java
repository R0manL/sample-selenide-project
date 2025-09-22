package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.ui.pages.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.data.PredBUs;
import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import com.ccc.hv.qa.ui.enums.DistributionServerSecurity;
import com.ccc.hv.qa.ui.enums.Headquarter;
import com.ccc.hv.qa.ui.enums.InputFieldErrorMsg;
import com.ccc.hv.qa.ui.pages.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.ChannelAndCATestService;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.ccc.hv.qa.db.services.ChannelDBService.hasChannelExistWith;
import static com.ccc.hv.qa.ui.data.BUs.createTagFromCreateCATest;
import static com.ccc.hv.qa.ui.data.BUs.createTagFromEditCATest;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.regressionCAFTPForChannelFlyoutSection;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.regressionCASFTPForCreateTagFromEditCAPage;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.CreateTagEntities.TAG_ENTITY_FROM_CA_ADD_MODE;
import static com.ccc.hv.qa.ui.data.CreateTagEntities.TAG_ENTITY_FROM_CA_EDIT_MODE;
import static com.ccc.hv.qa.ui.data.DistributionSrvs.smokeDistributionSrvSFTP;
import static com.ccc.hv.qa.ui.data.PredBUs.PRED_AUTOMATION_BU;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static org.assertj.core.api.Assertions.assertThat;


@Epic("Channel")
@Story("As logged in user I can create a new channel.")
@Test(groups = {"regression", "ui", "channel"})
public class ChannelTests extends UITestBase {
    private static final String EMPTY_FIELD_VALIDATION_ERROR_MSG = "must not be blank";
    private static final String UNIQUE_FIELD_VALIDATION_ERROR_MSG = "This must be unique";
    private static final String EMPTY_CHOOSE_BU_VALIDATION_ERROR_MSG = "At least one Business Unit must be selected";
    private static final ChannelExclusive PRED_EXCL_CHANNEL = ChannelExclusive.builder()
            .name("Regression exclusive chnl search " + generateUniqueStringBasedOnDate())
            .businessUnit(PredBUs.PRED_AUTOMATION_BU)
            .distributionServer(smokeDistributionSrvSFTP)
            .build();


    @TmsLink("AUT-561")
    @Test
    public void verifyChannelCreationValidationErrorOfEmptyChannelNameField() {
        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .clickAddChannelWithJS()
                .startChannelCreation()
                .gotoExclusiveSection()
                .getChannelNameErrorElm().shouldHave(Condition.text(EMPTY_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-561")
    @Test
    public void verifyChannelCreationValidationErrorOfExistChannelName() {
        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(regressionPublicChannelWithExistingChannelName);

        getTopMenu()
                .clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(regressionPublicChannelWithExistingChannelName.getName())
                .gotoExclusiveSection()
                .getChannelNameErrorElm().shouldHave(Condition.text(UNIQUE_FIELD_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-561")
    @Test
    public void verifyChannelCreationValidationErrorOfEmptyBuSelection() {
        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(regressionPublicChannelWithEmptyBuSection.getName())
                .gotoExclusiveSection()
                .gotoDistributionSection()
                .getChooseBuErrorElm().shouldHave(Condition.text(EMPTY_CHOOSE_BU_VALIDATION_ERROR_MSG));
    }

    @TmsLink("AUT-562")
    @Test
    public void verifyChannelServerValidationErrorMsgsForHostNamePortServerDisplayName() {
        final String CHANNEL_NAME = "Regression_pub_chnl_distr_srv_validation_" + generateUniqueStringBasedOnDate();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAddPage page = getTopMenu().clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(CHANNEL_NAME)
                .gotoExclusiveSection()
                .gotoDistributionSection()
                .gotoServerSection()
                .gotoBlackoutSection();

        page.getBlackoutSectionTitleElm().shouldNotBe(visible);
        page.getServerNameErrorMsgElm(InputFieldErrorMsg.NON_BLANK).shouldBe(visible);
        page.getServerPortNumberErrorMsgElm(InputFieldErrorMsg.INVALID).shouldBe(visible);
        page.getServerHostnameErrorMsgElm(InputFieldErrorMsg.NON_BLANK).shouldBe(visible);
    }

    @TmsLink("AUT-562")
    @Test
    public void verifyChannelServerValidationErrorMsgsWhenPortIsNotANumber() {
        final String UNIQUE_NUM = generateUniqueStringBasedOnDate();
        final String CHANNAL_NAME = "Regression_pub_chnl_distr_srv_validation_" + UNIQUE_NUM;
        final String SRV_NAME = "SRV_" + UNIQUE_NUM;
        final String SRV_INVALID_PORT = "PORT_" + UNIQUE_NUM;
        final String SRV_HOST_NAME = "SRV.HOST." + UNIQUE_NUM;

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAddPage page = getTopMenu().clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(CHANNAL_NAME)
                .gotoExclusiveSection()
                .gotoDistributionSection()
                .gotoServerSection()
                .setServerName(SRV_NAME)
                .setServerPortNumber(SRV_INVALID_PORT)
                .setServerHostName(SRV_HOST_NAME)
                .gotoBlackoutSection();

        page.getBlackoutSectionTitleElm().shouldNotBe(visible);
        page.getServerPortNumberErrorMsgElm(InputFieldErrorMsg.INVALID).shouldBe(visible);
    }

    @TmsLink("AUT-562")
    @Test
    public void verifyChannelServerCreationWithDuplicateNameValidationErrorMsg() {
        final String UNIQUE_NUM = generateUniqueStringBasedOnDate();
        final String CHANNEL_NAME = "Regression_pub_chnl_distr_srv_validation_" + UNIQUE_NUM;
        final String SRV_NAME = "SRV_" + UNIQUE_NUM;
        final String SRV_PORT = "2199";
        final String SRV_HOST_NAME = "SRV.HOST." + UNIQUE_NUM;
        final List<BusinessUnit> businessUnits = Arrays.asList(PRED_AUTOMATION_BU);

        getLoginPage().loginAs(ACC_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        ChannelAddPage page = getTopMenu().clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(CHANNEL_NAME)
                .gotoExclusiveSection()
                .selectExclusiveBU(businessUnits)
                .gotoDistributionSection()
                .gotoServerSection()

                //Add first server
                .setServerName(SRV_NAME)
                .setServerPortNumber(SRV_PORT)
                .setServerHostName(SRV_HOST_NAME)
                .clickAddServer()

                //Add second server
                .setServerName(SRV_NAME)
                .setServerPortNumber(SRV_PORT)
                .setServerHostName(SRV_HOST_NAME)
                .gotoBlackoutSection();

        page.getBlackoutSectionTitleElm().shouldNotBe(visible);
        page.getServerNameErrorMsgElm(InputFieldErrorMsg.DUPLICATE_SERVER_NAME).shouldBe(visible);
    }

    @TmsLink("AUT-562")
    @Test
    public void verifyChannelNameIsTooLongValidationMsg() {
        final String CHANNEL_NAME = "Regression_exclusive_channel_with_tooooooooooooooooo_long_name_" + generateUniqueStringBasedOnDate();

        getLoginPage().loginAs(ACC_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        getTopMenu().clickAddChannelWithJS()
                .startChannelCreation()
                .setChannelName(CHANNEL_NAME)
                .gotoExclusiveSection()
                .getChannelNameIsTooLongErrorMsgElm().shouldBe(visible);
    }

    @TmsLink("AUT-563")
    @Test
    public void verifyChannelActivationDeactivationBySuperAdmin() {
        final ChannelPublic pubChannel = regressionPubChannelSFTPForActivationDeactivationBySuperAdmin;
        final String CHANNEL_NAME = pubChannel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(pubChannel);

        ChannelsEditPage page = getTopMenu().clickEditChannels();

        page.getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.deactivateChannel(CHANNEL_NAME)
                .getInactiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.activateChannel(CHANNEL_NAME)
                .getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);
    }

    @TmsLink("AUT-563")
    @Test
    public void verifyChannelActivationDeactivationByAccountAdmin() {
        final ChannelExclusive exlChannel = regressionExclusiveChnlSFTPForActivationDeactivationByAccAdmin;
        final String CHANNEL_NAME = exlChannel.getName();

        getLoginPage().loginAs(ACC_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(exlChannel);

        ChannelsEditPage page = getTopMenu().clickEditChannels();

        page.getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.deactivateChannel(CHANNEL_NAME)
                .getInactiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.activateChannel(CHANNEL_NAME)
                .getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);
    }

    @TmsLink("AUT-563")
    @Test
    public void verifyChannelActivationDeactivationByMetaAdmin() {
        final ChannelExclusive exlChannel = regressionExclusiveChnlSFTPForActivationDeactivationByMetaAdmin;
        final String CHANNEL_NAME = exlChannel.getName();

        getLoginPage().loginAs(METADATA_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(exlChannel);

        ChannelsEditPage page = getTopMenu().clickEditChannels();

        page.getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.deactivateChannel(CHANNEL_NAME)
                .getInactiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.activateChannel(CHANNEL_NAME)
                .getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);
    }

    @DataProvider(name = "usersWithDifferentRoles")
    private Object[][] usersWithDifferentRoles() {
        return new Object[][]{
                {SUPER_ADMIN},
                {ACC_ADMIN},
                {METADATA_ADMIN}};
    }

    @TmsLink("AUT-563")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyChannelSearch(User user) {
        final String PRED_CHANNEL_NAME = PRED_EXCL_CHANNEL.getName();
        final String NON_EXISTING_CHANNEL_NAME = "!NON_EXISTING_CHANNEL_NAME";
        String firstThreeCharactersOfChannelName = PRED_CHANNEL_NAME.substring(0, 3);

        String channelNameWordsSpliterator = " ";
        String[] channelNameWords = PRED_CHANNEL_NAME.split(channelNameWordsSpliterator);
        String firstWordOfChannelName = Arrays.asList(channelNameWords).get(0);
        String secondWordOfChannelName = Arrays.asList(channelNameWords).get(1);

        getLoginPage().loginAs(user);

        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
        } else {
            getTopMenuTestService().selectDefaultBU();
        }

        if (!hasChannelExistWith(PRED_CHANNEL_NAME)) {
            getTopMenu()
                    .clickAddChannelWithJS()
                    .createChannel(PRED_EXCL_CHANNEL);
        }

        ChannelsEditPage page = getTopMenu().clickEditChannels();
        page.filterChannelsBy(firstThreeCharactersOfChannelName);
        List<String> filteredChannels = page.getListOfAllChannelNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelsBy(firstWordOfChannelName);
        filteredChannels = page.getListOfAllChannelNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelsBy(secondWordOfChannelName);
        filteredChannels = page.getListOfAllChannelNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelsBy(NON_EXISTING_CHANNEL_NAME);
        page.getNoRecordInTableElm().shouldBe(visible);
    }

    @TmsLink("AUT-563")
    @Test(dataProvider = "usersWithDifferentRoles")
    public void verifyChannelSorting(User user) {
        getLoginPage().loginAs(user);

        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
        } else {
            getTopMenuTestService().selectDefaultBU();
        }

        ChannelsEditPage page = getTopMenu().clickEditChannels();

        page.clickSortChannelAssociationsByName(true);
        List<String> actualAscendSortedCANames = page.getListOfAllChannelNames();
        assertThat(actualAscendSortedCANames)
                .as("Channel associations are not sorted well (ascending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        page.clickSortChannelAssociationsByName(false);
        List<String> actualDescendSortedCANames = page.getListOfAllChannelNames();
        assertThat(actualDescendSortedCANames)
                .as("Channel associations are not sorted well (descending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed());
    }

    @TmsLink("AUT-558")
    @Test
    public void verifyUserCanOpenManageTagsFromCreateCAPage() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(createTagFromCreateCATest)
                .getBUCreatedSuccessMessageElm().should(visible);

        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .selectBusinessUnitWithJSBy(createTagFromCreateCATest.getName())
                .clickAddChannelWithJS()
                .createChannel(regressionPublicChannelCreateTagFromCreateCAPage);

        getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(regressionPublicChannelCreateTagFromCreateCAPage.getName())
                .navigateToCreateTagPage()
                .createProductTag(TAG_ENTITY_FROM_CA_ADD_MODE)
                .getUploadedTagName(TAG_ENTITY_FROM_CA_ADD_MODE.getTagName())
                .shouldBe(Condition.visible);
    }


    @TmsLink("AUT-558")
    @Test
    public void verifyUserCanOpenManageTagsFromEditCAPage() {
        getLoginPage().loginAs(SUPER_ADMIN);

        getTopMenuTestService()
                .selectDefaultAccount()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(createTagFromEditCATest)
                .getBUCreatedSuccessMessageElm().should(visible);

        getTopMenu()
                .selectBusinessUnitWithJSBy(createTagFromEditCATest.getName());

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelCreateTagFromEditCAPage,
                regressionCASFTPForCreateTagFromEditCAPage)
                .createChannelWithCA();

        getLoginPage().loginAs(ACC_ADMIN);

        getTopMenu()
                .selectBusinessUnitWithJSBy(createTagFromEditCATest.getName())
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation()
                .navigateToCreateTagPage()
                .createProductTag(TAG_ENTITY_FROM_CA_EDIT_MODE)
                .getUploadedTagName(TAG_ENTITY_FROM_CA_EDIT_MODE.getTagName())
                .shouldBe(Condition.visible);
    }

    @TmsLink("AUT-590")
    @Test
    public void verifyEditChannelErrorMsgsForDifferentFields() {
        final ChannelPublic pubChannel1 = regressionPubChannelSFTPForChannelErrorMsgCheck;
        final ChannelPublic pubChannel2 = regressionPubChannelSFTPForChannelErrorMsgCheck2;
        final String CHANNEL1_NAME = pubChannel1.getName();
        final String CHANNEL2_NAME = pubChannel2.getName();
        final String PROPRIETARY_SCHEME_NAME = "AQA PROP SCHEME NAME";
        final String PROPRIETARY_SALES_OUTLET_ID = "AQA PROP SALES OUTLET ID";
        DistributionServerSFTP distributionServerSFTP = (DistributionServerSFTP) regressionPubChannelSFTPForChannelErrorMsgCheck.getDistributionServers().get(0);

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(pubChannel1);

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(pubChannel2);

        ChannelEditPage page = getTopMenu()
                .clickEditChannels()
                .clickOpenChannelWith(CHANNEL1_NAME)
                .clickEditChannel();

        page.updateChannelNameWith("").clickSave();
        page.getChannelNameErrorMsgElm(InputFieldErrorMsg.NON_BLANK).shouldBe(visible);

        page.updateChannelNameWith(CHANNEL2_NAME).clickSave();
        page.getChannelNameErrorMsgElm(InputFieldErrorMsg.UNIQUE).shouldBe(visible);

        page.updateChannelNameWith(CHANNEL1_NAME)
                .updateProprietarySchemeNameWith("")
                .updateProprietarySalesOutletIDWith(PROPRIETARY_SALES_OUTLET_ID)
                .clickSave();

        page.getProprietarySchemeNameErrorMsgElm(InputFieldErrorMsg.NON_NULL).shouldBe(visible);

        page.updateProprietarySchemeNameWith(PROPRIETARY_SCHEME_NAME)
                .updateProprietarySalesOutletIDWith("")
                .clickSave();

        page.getProprietarySalesOutletIDErrorMsgElm(InputFieldErrorMsg.NON_NULL).shouldBe(visible);

        page.updateProprietarySchemeNameWith("")
                .updateProprietarySalesOutletIDWith("")
                .expandServerSectionWith(distributionServerSFTP.getName())
                .updateServerNameWith("")
                .clickSave();

        page.getServerNameErrorMsgElm(InputFieldErrorMsg.NON_BLANK).shouldBe(visible);

        page.updateServerNameWith(regressionPubChannelSFTPForChannelErrorMsgCheck.getName())
                .updateFTPPortWith("0")
                .clickSave();

        page.getFTPPortErrorMsgElm(InputFieldErrorMsg.INVALID).shouldBe(visible);

        page.updateFTPPortWith(String.valueOf(distributionServerSFTP.getPort()))
                .updateFTPHostWith("")
                .clickSave();

        page.getFTPHostErrorMsgElm(InputFieldErrorMsg.NON_BLANK).shouldBe(visible);
    }

    @TmsLink("AUT-590")
    @Test
    public void verifyEditChannelCancelEditing() {
        final ChannelExclusive exclChannel1 = regressionExclChannelSFTPForChannelCancel;
        final String CHANNEL1_NAME = exclChannel1.getName();

        getLoginPage().loginAs(ACC_ADMIN);
        getTopMenuTestService().selectDefaultBU();

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(exclChannel1);

        getTopMenu()
                .clickEditChannels()
                .clickOpenChannelWith(CHANNEL1_NAME)
                .clickEditChannel()
                .clickCancel()
                .getPageTitleElm().shouldBe(visible);
    }

    @DataProvider(name = "usersThatShouldSeeExclusiveChannelsOnly")
    private Object[][] usersThatShouldSeeExclusiveChannelsOnly() {
        return new Object[][]{
                {ACC_ADMIN},
                {METADATA_ADMIN}};
    }

    @TmsLink("AUT-565")
    @Test(dataProvider = "usersThatShouldSeeExclusiveChannelsOnly")
    public void verifyUsersSeeAddEditExecutiveChannelButtons(User user) {
        getLoginPage().loginAs(user);
        getTopMenuTestService().selectDefaultBU();
        TopMenu topMenu = getTopMenu().expandChannelsDropdown();

        topMenu.getAddChannelButtonElm().shouldHave(exactText("Add an Exclusive Channel"));
        topMenu.getEditChannelsButtonElm().shouldHave(exactText("Edit Exclusive Channels"));
    }

    @TmsLink("AUT-565")
    public void verifySuperAdminCanSeeAddEditChannelButtons() {
        getLoginPage().loginAs(SUPER_ADMIN);

        TopMenu topMenu = getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .expandChannelsDropdown();

        topMenu.getAddChannelButtonElm().shouldHave(exactText("Add a Channel"));
        topMenu.getEditChannelsButtonElm().shouldHave(exactText("Edit Channels"));
    }

    @TmsLink("AUT-593")
    @Test
    public void verifyTenantUserCantSeeManageChannelsButton() {
        getLoginPage().loginAs(TENANT_USR);
        getTopMenuTestService().selectDefaultBU();

        getTopMenu()
                .expandChannelsDropdown()
                .getManageChannelsBtnElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-564")
    @Test
    public void verifyChannelViewPageBySysAdmin() {
        final ChannelExclusive pubChannel = regressionExclusiveChannelSFTPForChannelViewPageBySysAdmin;
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickAddChannelWithJS()
                .createChannel(pubChannel);

        ChannelViewPage channelViewPage = getTopMenu()
                .clickEditChannels()
                .clickOpenChannelWith(pubChannel.getName());

        channelViewPage.getPageTitleElm().shouldBe(visible);

        channelViewPage.getValueElm(ChannelViewPage.DropDown.CHANNEL_NAME)
                .shouldHave(exactText(pubChannel.getName()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.TIME_ZONE)
                .shouldHave(exactText(pubChannel.getTimeZone().getText()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.RETRY_INTERVAL)
                .shouldHave(exactText(pubChannel.getRetryInterval().toString()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.THRESHOLD)
                .shouldHave(exactText(pubChannel.getThreshold().toString()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.HEADQUARTERS)
                .shouldHave(exactText(pubChannel.getHeadquarter().getOptionValue()));
        assertThat(channelViewPage.getChannelMarkets()).isEqualTo(pubChannel.getChannelMarkets().stream()
                .map(Headquarter::getOptionValue).collect(Collectors.toList()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.PROPRIETARY_SCHEME_NAME)
                .shouldHave(exactText(pubChannel.getProprietarySchemeName()));
        channelViewPage.getValueElm(ChannelViewPage.DropDown.PROPRIETARY_SALES_OUTLET)
                .shouldHave(exactText(pubChannel.getProprietarySalesOutletID()));

        pubChannel.getDistributionServers().forEach(expectedServer -> {

            if (expectedServer instanceof DistributionServerSFTP) {
                DistributionServerSFTP serverSFTP = (DistributionServerSFTP) expectedServer;
                DistributionServerSFTP actualDistrServer = channelViewPage.getSFTPServer(serverSFTP.getName());
                assertThat(actualDistrServer).usingRecursiveComparison().isEqualTo(serverSFTP);
            }
            if (expectedServer instanceof DistributionServerFTP) {
                DistributionServerFTP serverFTP = (DistributionServerFTP) expectedServer;
                DistributionServerFTP actualDistrServer = channelViewPage.getFTPServer(serverFTP.getName());
                assertThat(actualDistrServer).usingRecursiveComparison().isEqualTo(serverFTP);

            }
            if (expectedServer instanceof DistributionServerFTPS) {
                DistributionServerFTPS serverFTPS = (DistributionServerFTPS) expectedServer;
                DistributionServerFTPS actualDistrServer = channelViewPage.getFTPSServer(serverFTPS.getName());
                assertThat(actualDistrServer).usingRecursiveComparison()
                        .ignoringFields("security", "mode").isEqualTo(serverFTPS);
                assertThat(actualDistrServer.getMode()).isEqualTo(DistributionServerMode.PASSIVE);
                assertThat(actualDistrServer.getSecurity()).isEqualTo(DistributionServerSecurity.IMPLICIT);
            }
        });

        assertThat(channelViewPage.getAddress()).usingRecursiveComparison()
                .ignoringFields("country")
                .isEqualTo(pubChannel.getContactInformation());
    }

    @TmsLink("AUT-564")
    @Test
    public void verifyChannelViewPageByMetadataAdminAndAccountAdmin() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final ChannelExclusive pubChannel = regressionExclusiveChannelSFTPForChannelViewPageByAccAndMetadataAdmin;
        getTopMenuTestService()
                .selectDefaultTestAccountAndBu()
                .clickAddChannelWithJS()
                .createChannel(pubChannel);

        Arrays.asList(METADATA_ADMIN, ACC_ADMIN).forEach(user -> {
            getLoginPage().loginAs(user);
            getTopMenuTestService()
                    .selectDefaultBU();

            ChannelViewPage channelViewPage = getTopMenu()
                    .clickEditChannels()
                    .clickOpenChannelWith(pubChannel.getName());

            channelViewPage.getValueElm(ChannelViewPage.DropDown.CHANNEL_NAME)
                    .shouldHave(exactText(pubChannel.getName()));
            channelViewPage.getValueElm(ChannelViewPage.DropDown.TIME_ZONE)
                    .shouldHave(exactText(pubChannel.getTimeZone().getText()));
            channelViewPage.getValueElm(ChannelViewPage.DropDown.PROPRIETARY_SCHEME_NAME)
                    .shouldHave(exactText(pubChannel.getProprietarySchemeName()));
            channelViewPage.getValueElm(ChannelViewPage.DropDown.PROPRIETARY_SALES_OUTLET)
                    .shouldHave(exactText(pubChannel.getProprietarySalesOutletID()));

            pubChannel.getDistributionServers().forEach(expectedServer -> {

                if (expectedServer instanceof DistributionServerSFTP) {
                    DistributionServerSFTP serverSFTP = (DistributionServerSFTP) expectedServer;
                    DistributionServerSFTP actualDistrServer = channelViewPage.getSFTPServer(serverSFTP.getName());
                    assertThat(actualDistrServer).usingRecursiveComparison().isEqualTo(serverSFTP);
                }
                if (expectedServer instanceof DistributionServerFTP) {
                    DistributionServerFTP serverFTP = (DistributionServerFTP) expectedServer;
                    DistributionServerFTP actualDistrServer = channelViewPage.getFTPServer(serverFTP.getName());
                    assertThat(actualDistrServer).usingRecursiveComparison().isEqualTo(serverFTP);

                }
                if (expectedServer instanceof DistributionServerFTPS) {
                    DistributionServerFTPS serverFTPS = (DistributionServerFTPS) expectedServer;
                    DistributionServerFTPS actualDistrServer = channelViewPage.getFTPSServer(serverFTPS.getName());
                    assertThat(actualDistrServer).usingRecursiveComparison()
                            .ignoringFields("security", "mode").isEqualTo(serverFTPS);
                    assertThat(actualDistrServer.getMode()).isEqualTo(DistributionServerMode.PASSIVE);
                    assertThat(actualDistrServer.getSecurity()).isEqualTo(DistributionServerSecurity.IMPLICIT);
                }
            });

            assertThat(channelViewPage.getAddress()).usingRecursiveComparison()
                    .ignoringFields("country")
                    .isEqualTo(pubChannel.getContactInformation());
        });

    }

    @TmsLink("AUT-779")
    @Test
    public void verifyChannelFlyoutSection() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPubChannelFTPForChannelFlyoutSection, regressionCAFTPForChannelFlyoutSection)
                .createChannelWithCA();

        final String channelName = channelAndCA.getChannelName();

        TopMenu topMenu = getTopMenu()
                .expandChannelsDropdown();

        topMenu.getChannelsAddedOrUpdatedForLast7DaysWith(channelName).shouldHave(CollectionCondition.size(1));

        topMenu.clickManageChannelWithJS().deactivateChannel(channelName);

        topMenu.expandChannelsDropdown().getChannelsDeactivatedForLast7DaysWith(channelName).shouldHave(CollectionCondition.size(1));
    }
}
