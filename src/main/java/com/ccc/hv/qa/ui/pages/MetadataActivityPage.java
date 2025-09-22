package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.ui.enums.MetadataActivityPhase;
import org.jetbrains.annotations.NotNull;

import static com.codeborne.selenide.Selenide.$x;

public class MetadataActivityPage {

    private MetadataActivityPage() {
        // None
    }

    public static MetadataActivityPage getMetadataActivityPage() {
        return new MetadataActivityPage();
    }

    public SelenideElement getFeedEntryElm(@NotNull String filename) {
        return $x(".//div[@class='feed-entry' and .//child::div[normalize-space(text())='" + filename + "']]");
    }

    public SelenideElement getNumberOfProductsElm(@NotNull String filename) {
        return getFeedEntryElm(filename).$(".meta-badge-count");
    }

    public String getNumberOfFailuresOfPhase(@NotNull String filename, @NotNull MetadataActivityPhase phase) {
        return getPhaseElm(filename, phase).getAttribute("data-failures");
    }

    public SelenideElement getStatusIconOfPhaseElm(@NotNull String filename, @NotNull MetadataActivityPhase phase) {
        return getPhaseElm(filename, phase).$(".glyphicon-ok");
    }

    public SelenideElement getCompletionSummaryElm(@NotNull String filename) {
        return getFeedEntryElm(filename).$(".mm-summary span");
    }

    public SelenideElement getNumberOfIncomingProductsElm(@NotNull String filename) {
        return getFeedEntryElm(filename).$(".mm-summary .meta-incoming");
    }

    public SelenideElement getAllAssetsProceededElm(@NotNull String filename) {
        return getFeedEntryElm(filename).$(".mm-distributed");
    }

    private SelenideElement getPhaseElm(@NotNull String filename, @NotNull MetadataActivityPhase phase) {
        return getFeedEntryElm(filename).$("div[data-phase-type='" + phase.getText() + "']");
    }
}
