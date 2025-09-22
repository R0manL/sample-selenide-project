package com.ccc.hv.qa.ui.migrated;

import com.ccc.hv.qa.ui.pojos.*;
import com.codeborne.selenide.Condition;
import com.ccc.hv.qa.db.pojo.ChannelAssociationDetails;
import com.ccc.hv.qa.ui.UITestBase;
import com.ccc.hv.qa.ui.enums.ChannelAssociationHeaders;
import com.ccc.hv.qa.ui.pages.AssociatePage;
import com.ccc.hv.qa.ui.pages.ChannelAssociatedListPage;
import com.ccc.hv.qa.ui.pages.ChannelAssociationEditPage;
import com.ccc.hv.qa.ui.pages.ChannelAssociationViewPage;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.services.ChannelAndCATestService;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getChannelAssociationDetailsFor;
import static com.ccc.hv.qa.db.services.ChannelDBService.getChannelIDBy;
import static com.ccc.hv.qa.db.services.ChannelDBService.hasChannelExistWith;
import static com.ccc.hv.qa.ui.data.AssociationSrvs.regressionAssociationSrvSFTPForWatermarkingNonAllowedProductTypes;
import static com.ccc.hv.qa.ui.data.AssociationSrvs.regressionAssociationSrvSFTPForWatermarkingOption;
import static com.ccc.hv.qa.ui.data.BUs.*;
import static com.ccc.hv.qa.ui.data.ChannelAssociations.*;
import static com.ccc.hv.qa.ui.data.Channels.*;
import static com.ccc.hv.qa.ui.data.PredUsers.*;
import static com.ccc.hv.qa.ui.enums.ChannelAssociationHeaders.*;
import static com.ccc.hv.qa.ui.pages.BusinessUnitEditPage.getBusinessUnitEditPage;
import static com.ccc.hv.qa.ui.pages.ChannelAssociationViewPage.DropDown.FIRST_NAME;
import static com.ccc.hv.qa.ui.pages.LoginPage.getLoginPage;
import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;
import static com.ccc.hv.qa.ui.services.TopMenuTestService.getTopMenuTestService;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;


@Epic("Channel association")
@Story("As logged in user I can create a new channel association.")
@Test(groups = {"regression", "ui", "ca"})
public class ChannelAssociationTests extends UITestBase {
    private static final String PUBLISHER_ID_WARNING_MSG = "Hrv has disabled asset watermarking until the Publisher ID field has been provided in the Business Unit";


    @DataProvider(name = "usersWithDifferentRolesAndChannelsAndCAs")
    private Object[][] usersWithDifferentRolesAndChannelsAndCAs() {
        return new Object[][]{
                {SUPER_ADMIN, regressionPublicChannelSFTPForCASimpleCreationSuperAdmin, regressionCASFTPForSimpleCACreation},
                {ACC_ADMIN, regressionExclusiveChannelSFTPForCASimpleCreationAccAdm, regressionCASFTPForSimpleCACreation},
                {METADATA_ADMIN, regressionExclusiveChannelSFTPForCASimpleCreationMetaAdm, regressionCASFTPForSimpleCACreation}};
    }

    @TmsLink("AUT-591")
    @Test(dataProvider = "usersWithDifferentRolesAndChannelsAndCAs")
    public void verifyChannelAssociationCreationWithOnlyUsernameAndPass(User user,
                                                                        Channel channel,
                                                                        ChannelAssociation ca) {
        getLoginPage().loginAs(user);

        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
        } else {
            getTopMenuTestService().selectDefaultBU();
        }

        new ChannelAndCATestService(channel, ca).createChannelWithCA();
    }


    @DataProvider(name = "usersWithDifferentRolesAndChannelsWtrmEnabled")
    private Object[][] usersWithDifferentRolesAndChannelsWtrmEnabled() {
        return new Object[][]{
                {SUPER_ADMIN, regressionPublicChannelSFTPForWatermarkingEnabledSuperAdmin},
                {ACC_ADMIN, regressionExclusiveChannelSFTPForWatermarkingEnabledAccAdm},
                {METADATA_ADMIN, regressionExclusiveChannelSFTPForWatermarkingEnabledMetaAdm}};
    }

    @TmsLink("AUT-591")
    @Test(dataProvider = "usersWithDifferentRolesAndChannelsWtrmEnabled")
    public void verifyChannelAssociationWatermarkingWhenOptionIsEnabled(User user, Channel channel) {
        getLoginPage().loginAs(user);

        if (user.equals(SUPER_ADMIN)) {
            getTopMenuTestService().selectDefaultTestAccountAndBu();
        } else {
            getTopMenuTestService().selectDefaultBU();
        }

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociationServer server = regressionAssociationSrvSFTPForWatermarkingOption;
        AssociatePage.ServerSection serverSection = getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(channel.getName())
                .expandServerSection(server);

        for (ChannelAssociationProductType caProductType : server.getCaProductTypes()) {
            AssociatePage.ServerSection.ProductTypeSection productTypeSection = serverSection.select(caProductType);

            for (ChannelAssociationContent content : caProductType.getContents()) {
                AssociatePage.ServerSection.ProductTypeSection.ContentSection contentSection = productTypeSection.select(content);
                AssociatePage.ServerSection.ProductTypeSection.ModalWindow modalWindow = contentSection.openModalWindow();

                modalWindow.getWatermarkCheckboxElm().shouldBe(visible);
                modalWindow.clickOk();
            }
        }
    }

    @TmsLink("AUT-591")
    @Test
    public void verifyChannelAssociationWatermarkingWhenOptionIsDisabled() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectSecondTestAccountAndBu();

        ChannelPublic channel = regressionPublicChannelSFTPForWatermarkingDisabledSuperAdmin;
        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociationServer server = regressionAssociationSrvSFTPForWatermarkingOption;
        AssociatePage.ServerSection serverSection = getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(channel.getName())
                .expandServerSection(server);

        for (ChannelAssociationProductType caProductType : server.getCaProductTypes()) {
            AssociatePage.ServerSection.ProductTypeSection productTypeSection = serverSection.select(caProductType);

            for (ChannelAssociationContent content : caProductType.getContents()) {
                AssociatePage.ServerSection.ProductTypeSection.ContentSection contentSection = productTypeSection.select(content);
                AssociatePage.ServerSection.ProductTypeSection.ModalWindow modalWindow = contentSection.openModalWindow();

                modalWindow.getWatermarkCheckboxElm().shouldNotBe(visible);
                modalWindow.clickOk();
            }
        }
    }

    @TmsLink("AUT-591")
    @Test
    public void verifyChannelAssociationWatermarkingForNonAllowedProductTypes() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelPublic channel = regressionPublicChannelSFTPForWatermarkingNonAllowedProductTypes;
        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociationServerSFTP server = regressionAssociationSrvSFTPForWatermarkingNonAllowedProductTypes;
        AssociatePage.ServerSection serverSection = getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(channel.getName())
                .expandServerSection(server);

        for (ChannelAssociationProductType caProductType : server.getCaProductTypes()) {
            AssociatePage.ServerSection.ProductTypeSection productTypeSection = serverSection.select(caProductType);

            for (ChannelAssociationContent content : caProductType.getContents()) {
                AssociatePage.ServerSection.ProductTypeSection.ContentSection contentSection = productTypeSection.select(content);
                AssociatePage.ServerSection.ProductTypeSection.ModalWindow modalWindow = contentSection.openModalWindow();

                modalWindow.getWatermarkCheckboxElm().shouldNotBe(visible);
                modalWindow.clickOk();
            }
        }
    }

    @TmsLink("AUT-591")
    @Test
    public void verifyChannelAssociationWhenPublisherPinIsEmpty() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        ChannelPublic channel = regressionPublicChannelSFTPForWatermarkingAndEmptyPublisherID;

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(buWithWtrmrkIsNotFulFilledCreateCa)
                .getBUCreatedSuccessMessageElm().shouldBe(Condition.visible);

        getTopMenu()
                .selectBusinessUnitWithJSBy(buWithWtrmrkIsNotFulFilledCreateCa.getName());

        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(channel);

        AssociatePage caAssociatePage = getTopMenu()
                .clickManageChannelWithJS()
                .openAddChannelAssociationsPageFor(channel.getName());

        caAssociatePage.getHrvDisableWatermarkingWarningMsgElm().shouldBe(visible);
        caAssociatePage.getHrvDisableWatermarkingWarningMsgBULinkElm().click();

        getBusinessUnitEditPage().getPageTitle().shouldBe(visible);

        getTopMenu()
                .clickManageChannelWithJS()
                .openAddChannelAssociationsPageFor(channel.getName());

        getTopMenu()
                .clickManageChannelWithJS()
                .openAddChannelAssociationsPageFor(channel.getName())
                .createChannelAssociation(regressionCASFTPForWatermarkAndPublisherID)
                .clickSave()
                .isSuccessMsgVisible()
                .returnToAssociatedChannelsList()
                .openChannelAssociationViewPage(channel.getName())
                .getHrvDisableWatermarkingWarningMsgElm().shouldBe(visible);

    }

    @DataProvider(name = "usersWithDifferentRolesAndChannels")
    private Object[][] usersWithDifferentRolesAndChannels() {
        return new Object[][]{
                {SUPER_ADMIN, regressionPublicChannelFTPSForCAEditModeSysAdmin},
                {ACC_ADMIN, regressionPublicChannelFTPSForCAEditModeAccountAdmin},
                {METADATA_ADMIN, regressionPublicChannelFTPSForCAEditModeMetadataAdmin}};
    }

    @TmsLink("AUT-592")
    @Test(dataProvider = "usersWithDifferentRolesAndChannels")
    public void verifyChannelAssociationEditMode(User user, Channel channel) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                channel,
                regressionChannelAssociationFTPSForCAEditModeInitial)
                .createChannelWithCA();

        if (!user.equals(SUPER_ADMIN)) {
            getLoginPage().loginAs(user);
            getTopMenuTestService().selectDefaultBU();
        }

        ChannelAssociationEditPage editPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation();

        Integer caId = getCAIDBy(Integer.parseInt(getChannelIDBy(channel.getName())));
        editPage.getPageTitleElm()
                .shouldHave(exactText("Edit " + channel.getName() + " ID - " + caId));

        editPage.editChannelAssociationWith(regressionChannelAssociationFTPSForCAEditModeUpdated)
                .clickSaveAndWaitOnSuccess();

        ChannelAssociationViewPage viewPage = getTopMenu().clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName());
        viewPage.getPageTitleElm().shouldBe(visible);

        assertThat(viewPage.isAutomatedDistributionRulesSelected()).isTrue();

        viewPage.getValueElm(FIRST_NAME).shouldBe(not(empty));

        assertThat(viewPage.getAddress()).usingRecursiveComparison()
                .ignoringFields("country")
                .isEqualTo(regressionChannelAssociationFTPSForCAEditModeUpdated.getContactInformation());
    }

    @TmsLink("AUT-592")
    @Test
    public void verifyChannelAssociationEditModeValidation() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                regressionPublicChannelFTPSForCAEditModeValidation,
                regressionChannelAssociationFTPSForCAEditModeInitialValidation)
                .createChannelWithCA();

        Arrays.asList(SUPER_ADMIN, ACC_ADMIN, METADATA_ADMIN).forEach(user -> {
            if (!user.equals(SUPER_ADMIN)) {
                getLoginPage().loginAs(user);
                getTopMenuTestService().selectDefaultBU();
            }

            ChannelAssociationEditPage editPage = getTopMenu()
                    .clickManageChannelWithJS()
                    .openChannelAssociationViewPage(channelAndCA.getChannelName())
                    .clickEditChannelAssociation()
                    .editChannelAssociationWith(regressionChannelAssociationFTPSForCAEditModeUpdatedValidation);

            editPage.clickSave();

            editPage.getCAPageErrorElm()
                    .shouldBe(Condition.visible)
                    .shouldHave(Condition.text("2 error(s) detected. Please correct & resubmit."));
        });
    }

    @TmsLink("AUT-592")
    @Test
    public void verifyChannelAssociationEditModeWatermarkingIsOff() {
        getLoginPage().loginAs(SUPER_ADMIN);
        final Channel CHANNEL = regressionPublicChannelFTPSForCAEditModeWatermarkingIsOff;
        final ChannelAssociation CA = regressionChannelAssociationFTPSForCAEditModeWatermarkingIsOff;

        getTopMenuTestService().selectSecondTestAccountAndBu();

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(CHANNEL, CA).createChannelWithCA();

        ChannelAssociationEditPage editPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation();
        editPage.getPageTitleElm()
                .shouldHave(exactText("Edit " + CHANNEL.getName()));

        CA.getAssociationServers().forEach(associationServer -> {
            editPage.expandServerSection(associationServer.getName());
            associationServer.getCaProductTypes().forEach(productType ->
                    productType.getContents().forEach(content ->
                            assertThat(editPage.isWatermarkOptionAvailableFor(content)).isFalse()));
        });
    }

    @DataProvider(name = "usersWithDifferentRolesAndBUsAndChannels")
    private Object[][] usersWithDifferentRolesAndBUsAndChannels() {
        return new Object[][]{
                {SUPER_ADMIN, buWithDWatermarkDisabledCAEditSuperAdmin, regressionPublicChannelFTPSForCAEditModeSysAdminWarningMsg},
                {ACC_ADMIN, buWithDWatermarkDisabledCAEditAccountAdmin, regressionPublicChannelFTPSForCAEditModeAccountAdminWarningMsg},
                {METADATA_ADMIN, buWithDWatermarkDisabledCAEditMetadataAdmin, regressionPublicChannelFTPSForCAEditModeMetadataAdminWarningMsg}};
    }

    @TmsLink("AUT-592")
    @Test(dataProvider = "usersWithDifferentRolesAndBUsAndChannels")
    public void verifyChannelAssociationEditModeWtmrkWarningMessage(User user, BusinessUnit bu, Channel channel) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(bu)
                .getBUCreatedSuccessMessageElm().shouldBe(Condition.visible);

        getTopMenu()
                .selectBusinessUnitWithJSBy(bu.getName());

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                channel,
                regressionChannelAssociationFTPSForCAEditBUWatermarkSettings)
                .createChannelWithCA();

        if (!user.equals(SUPER_ADMIN)) {
            getLoginPage().loginAs(user);
            getTopMenu().selectBusinessUnitWithJSBy(bu.getName());
        }

        ChannelAssociationEditPage editPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation();

        editPage.getPageTitleElm().shouldBe(visible);

        editPage
                .getPublisherPinIsNotProvidedWarningMsgElm()
                .shouldHave(exactText(PUBLISHER_ID_WARNING_MSG));

        editPage
                .navigateToTheBUFromWarningMsg()
                .setPublisherID(ENV_CONFIG.buPublisherId())
                .setPublisherPin(ENV_CONFIG.buPublisherPin())
                .clickContinueBtn()
                .clickSaveBtn();

        ChannelAssociationEditPage updatedEditPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation();

        updatedEditPage.getPageTitleElm().shouldBe(visible);
        updatedEditPage.getPublisherPinIsNotProvidedWarningMsgElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-595")
    @Test
    public void verifyChannelAssociationEditTagFilteringAndCAIdWithWatermarkEnabled() {
        ChannelPublic pubChannel = regressionPubChnlSFTPForCATagFilteringOptionsAndCAIdWithWatermarkEnabled;
        String pubChannelName = pubChannel.getName();

        if (!hasChannelExistWith(pubChannelName)) {
            getLoginPage().loginAs(SUPER_ADMIN);
            getTopMenuTestService().selectDefaultTestAccountAndBu();

            new ChannelAndCATestService(pubChannel, regressionChannelAssociationSFTPForCATagFilteringOptionAndCAId)
                    .createChannelWithCA();
        }

        Arrays.asList(SUPER_ADMIN, ACC_ADMIN, METADATA_ADMIN).forEach(user -> {
            if (!user.equals(SUPER_ADMIN)) {
                getLoginPage().loginAs(user);
                getTopMenuTestService().selectDefaultBU();
            }

            ChannelAssociationViewPage viewCaPage = getTopMenu()
                    .clickManageChannelWithJS()
                    .openChannelAssociationViewPage(pubChannelName);

            viewCaPage.getChannelAssociationIDElm(pubChannelName).shouldBe(visible);

            ChannelAssociationEditPage editCaPage = viewCaPage.clickEditChannelAssociation();

            editCaPage.expandTagsSection().getUseTagFiltersCheckBoxElm().shouldBe(visible);
            editCaPage.getChannelAssociationIDElm(pubChannelName).shouldBe(visible);
        });
    }

    @TmsLink("AUT-595")
    @Test
    public void verifyChannelAssociationEditTagFilteringAndCAIdWithWatermarkDisabled() {
        ChannelPublic pubChannel = regressionPubChnlSFTPForCATagFilteringOptionsAndCAIdWithWatermarkDisabled;
        String pubChannelName = pubChannel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectSecondTestAccountAndBu();

        new ChannelAndCATestService(pubChannel, regressionChannelAssociationSFTPForCATagFilteringOptionAndCAId)
                .createChannelWithCA();

        ChannelAssociationViewPage viewCaPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(pubChannelName);

        viewCaPage.getChannelAssociationIDElm(pubChannelName).shouldNotBe(visible);

        ChannelAssociationEditPage editCaPage = viewCaPage.clickEditChannelAssociation();

        editCaPage.expandTagsSection().getUseTagFiltersCheckBoxElm().shouldBe(visible);
        editCaPage.getChannelAssociationIDElm(pubChannelName).shouldNotBe(visible);
    }

    @DataProvider(name = "usersWithDifferentRolesAndBUsAndChannels2")
    private Object[][] usersWithDifferentRolesAndBUsAndChannels2() {
        return new Object[][]{
                {SUPER_ADMIN, buWithDWatermarkDisabledCAEditSuperAdmin2, regressionPublicChannelFTPSForCAEditModeSysAdmin2},
                {ACC_ADMIN, buWithDWatermarkDisabledCAEditAccountAdmin2, regressionPublicChannelFTPSForCAEditModeAccountAdmin2},
                {METADATA_ADMIN, buWithDWatermarkDisabledCAEditMetadataAdmin2, regressionPublicChannelFTPSForCAEditModeMetadataAdmin2}};
    }

    @TmsLink("AUT-592")
    @Test(dataProvider = "usersWithDifferentRolesAndBUsAndChannels2")
    public void verifyChannelAssociationViewModeWtmrkWarningMessage(User user, BusinessUnit bu, Channel channel) {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultAccount();

        getTopMenu()
                .clickAddBusinessUnitWithJS()
                .createBusinessUnit(bu)
                .getBUCreatedSuccessMessageElm().shouldBe(Condition.visible);

        getTopMenu().selectBusinessUnitWithJSBy(bu.getName());

        ChannelAndCATestService channelAndCA = new ChannelAndCATestService(
                channel,
                regressionChannelAssociationFTPSForCAViewBUWatermarkSettings)
                .createChannelWithCA();

        if (!user.equals(SUPER_ADMIN)) {
            getLoginPage().loginAs(user);
            getTopMenu().selectBusinessUnitWithJSBy(bu.getName());
        }

        ChannelAssociationViewPage viewPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName());

        viewPage.getPageTitleElm().shouldBe(visible);

        viewPage
                .getPublisherPinIsNotProvidedWarningMsgElm()
                .shouldHave(exactText(PUBLISHER_ID_WARNING_MSG));

        viewPage
                .navigateToTheBUFromWarningMsg()
                .setPublisherID(ENV_CONFIG.buPublisherId())
                .setPublisherPin(ENV_CONFIG.buPublisherPin())
                .clickContinueBtn()
                .clickSaveBtn();

        ChannelAssociationEditPage updatedEditPage = getTopMenu()
                .clickManageChannelWithJS()
                .openChannelAssociationViewPage(channelAndCA.getChannelName())
                .clickEditChannelAssociation();

        updatedEditPage.getPageTitleElm().shouldBe(visible);
        updatedEditPage.getPublisherPinIsNotProvidedWarningMsgElm().shouldNotBe(visible);
    }

    @TmsLink("AUT-624")
    @Test
    public void verifyChannelAssociationActivationDeactivationBySuperAdmin() {
        final ChannelPublic pubChannel = regressionPubChannelSFTPForCAActivationDeactivation;
        final String CHANNEL_NAME = pubChannel.getName();

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        new ChannelAndCATestService(
                pubChannel,
                regressionChannelAssociationSFTPForCADeactivationAndSorting)
                .createChannelWithCA();

        ChannelAssociatedListPage page = getTopMenu().clickManageChannelWithJS();

        page.getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        page.deactivateChannel(CHANNEL_NAME)
                .getInactiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        long MAX_TIMEOUT = ENV_CONFIG.awaitilityTimeout();
        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    ChannelAssociationDetails caDetails = getChannelAssociationDetailsFor(CHANNEL_NAME);

                    Assertions.assertThat(caDetails.isActiveYn())
                            .as("Invalid CA active state.")
                            .isFalse();
                });

        page.activateChannelAssociationFor(CHANNEL_NAME)
                .getActiveBtnFor(CHANNEL_NAME).shouldBe(visible);

        await().atMost(MAX_TIMEOUT, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    ChannelAssociationDetails caDetails = getChannelAssociationDetailsFor(CHANNEL_NAME);

                    Assertions.assertThat(caDetails.isActiveYn())
                            .as("Invalid CA active state.")
                            .isTrue();
                });
    }

    @TmsLink("AUT-624")
    @Test
    public void verifyChannelAssociationSearch() {
        final ChannelPublic pubChannel = regressionPubChannelSFTPForCASearch;
        final String PRED_CHANNEL_NAME = pubChannel.getName();
        final String NON_EXISTING_CHANNEL_NAME = "!NON_EXISTING_CHANNEL_NAME";
        String firstThreeCharactersOfChannelName = PRED_CHANNEL_NAME.substring(0, 3);

        String channelNameWordsSpliterator = " ";
        String[] channelNameWords = PRED_CHANNEL_NAME.split(channelNameWordsSpliterator);
        String firstWordOfChannelName = Arrays.asList(channelNameWords).get(0);
        String secondWordOfChannelName = Arrays.asList(channelNameWords).get(1);

        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        new ChannelAndCATestService(
                pubChannel,
                regressionChannelAssociationSFTPForCAFiltering)
                .createChannelWithCA();

        ChannelAssociatedListPage page = getTopMenu().clickManageChannelWithJS();

        page.filterChannelAssociationsBy(firstThreeCharactersOfChannelName);
        List<String> filteredChannels = page.getListOfAllChannelAssociationNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelAssociationsBy(firstWordOfChannelName);
        filteredChannels = page.getListOfAllChannelAssociationNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelAssociationsBy(secondWordOfChannelName);
        filteredChannels = page.getListOfAllChannelAssociationNames();
        assertThat(filteredChannels).as("Can't find channel after filtering").contains(PRED_CHANNEL_NAME);
        page.clearFiltering();

        page.filterChannelAssociationsBy(NON_EXISTING_CHANNEL_NAME);
        page.getNoRecordInTableElm().shouldBe(visible);
    }

    @TmsLink("AUT-624")
    @Test
    public void verifyChannelSorting() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final ChannelPublic pubChannel = regressionPubChannelSFTPForCAFiltering;
        new ChannelAndCATestService(
                pubChannel,
                regressionChannelAssociationSFTPForCASorting)
                .createChannelWithCA();

        ChannelAssociatedListPage page = getTopMenu().clickManageChannelWithJS();

        page.clickSortChannelAssociationsByName(true);
        List<String> actualAscendSortedCANames = page.getListOfAllChannelAssociationNames();
        assertThat(actualAscendSortedCANames)
                .as("Channel associations are not sorted well (ascending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

        page.clickSortChannelAssociationsByName(false);
        List<String> actualDescendSortedCANames = page.getListOfAllChannelAssociationNames();
        assertThat(actualDescendSortedCANames)
                .as("Channel associations are not sorted well (descending).")
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed());
    }

    @TmsLink("AUT-625")
    @Test
    public void verifyManageCAWatermarkIsOn() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectDefaultTestAccountAndBu();

        final List<String> expectedHeadersList = Arrays.stream(ChannelAssociationHeaders.values())
                .map(ChannelAssociationHeaders::getText)
                .collect(Collectors.toList());

        Arrays.asList(SUPER_ADMIN, ACC_ADMIN, METADATA_ADMIN).forEach(user -> {
            if (!user.equals(SUPER_ADMIN)) {
                getLoginPage().loginAs(user);
                getTopMenuTestService().selectDefaultBU();
            }

            ChannelAssociatedListPage viewPage = getTopMenu().clickManageChannelWithJS();
            viewPage.getChannelAssociationHeaderElms().shouldHave(exactTexts(expectedHeadersList));

            viewPage.clickSortCAByID(true);
            List<Integer> actualAscSortedCaIds = viewPage.getListOfIntegersNotEmptyIds();

            assertThat(actualAscSortedCaIds)
                    .as("CAs are not sorted by ID ASC.")
                    .isSortedAccordingTo(Comparator.naturalOrder());

            viewPage.clickSortCAByID(false);
            List<Integer> actualDescendSortedCaIds = viewPage.getListOfIntegersNotEmptyIds();
            assertThat(actualDescendSortedCaIds)
                    .as("CAs are not sorted by ID DESC.")
                    .isSortedAccordingTo(Comparator.reverseOrder());

        });
    }

    @TmsLink("AUT-625")
    @Test
    public void verifyManageCAWatermarkIsOff() {
        getLoginPage().loginAs(SUPER_ADMIN);
        getTopMenuTestService().selectSecondTestAccountAndBu();

        final List<String> expectedHeadersListWithoutId = Arrays.asList(
                CHANNEL_ASSOCIATIONS.getText(),
                OPTIMIZATIONS.getText(),
                AUTOMATED_DISTRIBUTIONS.getText(),
                DISTRIBUTION_STATUS.getText(),
                MARKETPLACE_MONITORING.getText()
        );
        ChannelAssociatedListPage viewPage = getTopMenu().clickManageChannelWithJS();
        viewPage.getChannelAssociationHeaderElms().shouldHave(exactTexts(expectedHeadersListWithoutId));
    }
}
