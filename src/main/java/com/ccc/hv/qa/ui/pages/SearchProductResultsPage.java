package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.codeborne.selenide.Selenide.$;
import static com.ccc.hv.qa.ui.pages.LinkEventToProductPage.getLinkEventToProductPage;
import static com.ccc.hv.qa.ui.pages.ProductDetailsPage.getProductDetailsPage;

public class SearchProductResultsPage extends PageBase {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final String title;
    private final SelenideElement productContainerElm;

    public SearchProductResultsPage(@NotNull String title) {
        this.title = title;
        this.productContainerElm = activePageContainer.$x(".//*[contains(@class,'listingResultBox container') " +
                "and .//a[normalize-space(text())=\"" + title + "\"]]");
    }

    public SearchProductResultsPage(@NotNull String title, @NotNull String recordReference) {
        this.title = title;
        this.productContainerElm = activePageContainer.$$x(".//*[contains(@class,'listingResultBox container') " +
                "and .//a[normalize-space(text())=\"" + title + "\"]]").filter(Condition.text(recordReference))
                .shouldHave(CollectionCondition.size(1))
                .first();
    }

    public SelenideElement getPageTitleElm() {
        return $(".title a");
    }

    public SelenideElement getNoSearchResultsMessageElm(){
        return $("#noresults");
    }

    public SearchProductResultsPage activateBulkSelection() {
        log.info("Activate bulk selection.");
        $("#activateBulkSelect").click();
        return this;
    }

    public SearchProductResultsPage clickTrackProductsAndConfirm() {
        log.info("Click track product and confirm.");
        $("#btnTrackProducts").click();
        $("#productTrackModalAction").click();

        return this;
    }

    public SelenideElement productWithTitle() {
        return productContainerElm;
    }

    public SelenideElement productWithHoldDistribution(boolean enabled) {
        return enabled ? this.productContainerElm.$(".statusbar .hold.indicator") : this.productContainerElm.$(".statusbar .hold.indicator.disabled");
    }

    public SelenideElement productWithLockIndicator(boolean enabled) {
        return enabled ? this.productContainerElm.$(".statusbar .lock.indicator") : this.productContainerElm.$(".statusbar .lock.indicator.disabled");
    }

    public SelenideElement productWithAuthor(@NotNull String value) {
        return getPropElmWith(value);
    }

    public SelenideElement productWithIsbn(@NotNull String value) {
        return getPropElmWith(value);
    }

    public SelenideElement productWithProductType(@NotNull String value) {
        return getPropElmWith(value);
    }

    public SelenideElement productWithPublisher(@NotNull String value) {
        return getPropElmWith("Publisher: " + value);
    }

    public SelenideElement productWithRecordReference(@NotNull String value) {
        return getPropElmWith("Record Reference: " + value);
    }

    public SelenideElement productWithBusinessUnit(@NotNull String value) {
        return getPropElmWith("Business Unit: " + value);
    }

    public SelenideElement productWithMetadataBadge(boolean enabled) {
        return enabled ? this.productContainerElm.$x(".//*[@class='ok']/*[text()='Metadata']") : this.productContainerElm.$x(".//*[not(@class)]/*[text()='Metadata']");
    }

    public SelenideElement productWithContentBadge(boolean enabled) {
        return enabled ? this.productContainerElm.$x(".//*[@class='ok']/*[text()='Content']") : this.productContainerElm.$x(".//*[not(@class)]/*[text()='Content']");
    }

    public SelenideElement productWithCollateralBadge(boolean enabled) {
        return enabled ? this.productContainerElm.$x(".//*[@class='ok']/*[text()='Collateral']") : this.productContainerElm.$x(".//*[not(@class)]/*[text()='Collateral']");
    }

    public SelenideElement productWithTrackedBadge(boolean enabled) {
        return enabled ? this.productContainerElm.$(".beingtracked") : this.productContainerElm.$(".notBeingTracked");
    }

    public ProductDetailsPage openProductDetailsPage() {
        log.info("Open '" + this.title + "' product details page.");
        this.productContainerElm.$(".title a").click();
        waitOnLoadingMsgDisappeared();

        return getProductDetailsPage();
    }

    @Step("Click Actions")
    public SearchProductResultsPage clickActions() {
        log.info("Click actions for product with title: '" + this.title);
        this.productContainerElm.$(".action-meatballs").click();
        waitOnLoadingMsgDisappeared();

        return this;
    }

    @Step("Click Link Event")
    public LinkEventToProductPage clickLinkEvent() {
        log.info("Click link events for product with title: '" + this.title);
        this.productContainerElm.$("#linkEvents").click();
        waitOnLoadingMsgDisappeared();

        return getLinkEventToProductPage();
    }

    public SearchProductResultsPage selectProduct() {
        log.info("Select product with title: '" + this.title);
        this.productContainerElm.$(".control__indicator").click();

        return this;
    }

    public SelenideElement getDeleteProductButton() {
        return $("[data-target='#deleteProductModal']");
    }

    public void confirmProductDeletion() {
        $("button#productDeleteModalAction").click();
    }

    public SearchProductResultsPage clickDelete() {
        log.info("Delete '" + this.title + "' product.");
        getDeleteProductButton().click();

        return this;
    }

    private SelenideElement getPropElmWith(@NotNull String value) {
        log.debug("Get property by '" + value + "' value");
        return this.productContainerElm.$x(".//p[text()='" + value + "']");
    }
}


