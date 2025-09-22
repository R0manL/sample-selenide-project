package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.Keyword;
import com.ccc.hv.qa.ui.pojos.AdvancedKeywordSetting;

public class AdvancedKeywordSettings {
    public static final AdvancedKeywordSetting settingsForPublisherTheNavigators = AdvancedKeywordSetting.builder()
            .keyword(Keyword.PUBLISHER_NAME)
            .sourceFieldValue("The Navigators")
            .replacementValue("P633WC1")
            .build();

    public static final AdvancedKeywordSetting settingsForPublisherTyndaleHousePublishersInc = AdvancedKeywordSetting.builder()
            .keyword(Keyword.PUBLISHER_NAME)
            .sourceFieldValue("Tyndale House Publishers, Inc.")
            .replacementValue("XDXLT8S")
            .build();

    public static final AdvancedKeywordSetting settingsForPublisherFocusOnTheFamily = AdvancedKeywordSetting.builder()
            .keyword(Keyword.PUBLISHER_NAME)
            .sourceFieldValue("Focus on the Family")
            .replacementValue("0GRP8FL")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintNovellPress = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Novell Press")
            .replacementValue("JET5U91")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintPearsonFTPress = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Pearson FT Press")
            .replacementValue("CG8H0G7")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintMySQLPress = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("MySQL Press")
            .replacementValue("JET5U91")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintAddisonWesleyProfessional = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Addison-Wesley Professional")
            .replacementValue("9XS97RS")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintPearson = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Pearson")
            .replacementValue("626N35R")
            .makeDefault(true)
            .build();

    public static final AdvancedKeywordSetting settingsForImprintPearsonPrenticeHall = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Pearson Prentice Hall")
            .replacementValue("626N35R")
            .build();

    public static final AdvancedKeywordSetting settingsForImprintPrenticeHall = AdvancedKeywordSetting.builder()
            .keyword(Keyword.IMPRINT)
            .sourceFieldValue("Prentice Hall")
            .replacementValue("626N35R")
            .build();

}
