package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.pages.AccountEditPage.getAccountEditPage;

/**
 * Created by R0manL on 31/08/20.
 */

public class ManageAccountsPage extends PageBase {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ManageAccountsPage() {
        // None
    }

    public static ManageAccountsPage getManageAccountPage() {
        return new ManageAccountsPage();
    }

    public void activateAccount(@NotNull String accountName) {
        log.info("Activate account: " + accountName);

        SelenideElement statusCell = activePageContainer
                .$x(".//td[normalize-space(text())='" + accountName + "']")
                .sibling(0);

        statusCell.shouldBe(visible);
        SelenideElement activateBtn = statusCell.$x(".//*[./button[@class='btn btn-danger']]/button[@class='btn btn-default']");
        SelenideElement deactivateBtn = statusCell.$x(".//button[@class='btn btn-success']");
        if (activateBtn.exists()) {
            activateBtn.click();
            $("button#status-modal-action").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());
            $(".modal-content button.btn-default").click();
        } else if (deactivateBtn.exists()) {
            log.warn("Account has already activated. Skip this step.");
        } else {
            throw new IllegalStateException("Can't detect account state (active/inactive).");
        }
    }

    public void deactivateAccount(@NotNull String accountName) {
        log.info("Deactivate '" + accountName + "' account.");

        SelenideElement statusCell = activePageContainer
                .$x(".//td[contains(@class, 'accounts-row') and normalize-space(text())='" + accountName + "']")
                .sibling(0);

        statusCell.shouldBe(visible);

        SelenideElement isAccountDeactivatedFlag = statusCell.$x(".//*[./button[@class='btn btn-danger']]/button[@class='btn btn-default']");
        SelenideElement changeStatus = statusCell.$x(".//button[@class='btn btn-default btn-success']");

        if (!isAccountDeactivatedFlag.exists()) {
            changeStatus.click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

            $("button#status-modal-action").click();
            Selenide.sleep(ENV_CONFIG.webElmRefreshTimeout());

            $(".modal-content button.btn-default").click();
        } else if (isAccountDeactivatedFlag.exists()) {
            log.warn("Account has already deactivated. Skip this step.");
        } else {
            throw new IllegalStateException("Can't detect account state (active/inactive).");
        }
    }

    public AccountEditPage editAccount(@NotNull String accountName) {
        log.info("Edit '" + accountName + "' account.");

        activePageContainer
                .$x(".//td[normalize-space(text())='" + accountName + "']//a[normalize-space(text())='Edit']")
                .click();

        getAccountEditPage().checkEnteredAccountName(accountName);

        return getAccountEditPage();
    }
}
