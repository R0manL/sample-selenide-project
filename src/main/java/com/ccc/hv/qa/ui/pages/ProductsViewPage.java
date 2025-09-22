package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by R0manL on 7/12/22.
 */

public class ProductsViewPage {
    private static final SelenideElement ROOT_ELM = $("#productsView");


    private ProductsViewPage() {
        // NONE
    }

    public static ProductsViewPage getProductsViewPage() {
        return new ProductsViewPage();
    }

    public SelenideElement getPageRootElm() {
        return ROOT_ELM;
    }

}
