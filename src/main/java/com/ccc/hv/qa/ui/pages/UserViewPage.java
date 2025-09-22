package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.ccc.hv.qa.ui.pages.EditUserPage.getUserEditPage;

public class UserViewPage {
    private UserViewPage() {
        // None
    }

    public static UserViewPage getUserViewPage() {
        return new UserViewPage();
    }

    public EditUserPage editUser() {
        $("a#user-edit-action").click();
        return getUserEditPage();
    }

    public SelenideElement getRoleValue(){
        return $x(".//label[contains(text(),'Role')]/following-sibling::div");
    }

    public ElementsCollection getBUValueElms(){
        return $x(".//label[contains(text(),'Business Units User Has Access To')]/following-sibling::div")
                .$$x(".//li");
    }

    public SelenideElement getFirstNameValueElm(){
        return $x(".//label[contains(text(),'First Name')]/following-sibling::div");
    }

    public SelenideElement getLastNameValueElm(){
        return $x(".//label[contains(text(),'Last Name')]/following-sibling::div");
    }

    public SelenideElement getEmailValueElm(){
        return $x(".//label[contains(text(),'Email Address')]/following-sibling::div");
    }

    public SelenideElement getPhoneNumberValueElm(){
        return $x(".//label[contains(text(),'Phone Number')]/following-sibling::div");
    }
}