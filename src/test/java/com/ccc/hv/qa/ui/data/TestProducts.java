package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.TestProduct;

import static com.ccc.hv.qa.ui.data.AdvancedKeywordSettings.*;

public class TestProducts {
    public static final TestProduct theNavigatorsPubTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistrByAdvKeywordUsingPubName/9781631468476.xml")
            .contentFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781631468476.epub")
            .collateralFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781631468476_marketingimage.jpg")
            .pubReplacementValue(settingsForPublisherTheNavigators.getReplacementValue())
            .build();

    public static final TestProduct tyndaleHousePublishersIncProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistrByAdvKeywordUsingPubName/9781496427274.xml")
            .contentFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781496427274.epub")
            .collateralFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781496427274_marketingimage.jpg")
            .pubReplacementValue(settingsForPublisherTyndaleHousePublishersInc.getReplacementValue())
            .build();

    public static final TestProduct focusOnTheFamilyTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistrByAdvKeywordUsingPubName/9781684282128.xml")
            .contentFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781684282128.epub")
            .collateralFilePath("regression/verifyDistrByAdvKeywordUsingPubName/assets/9781684282128_marketingimage.jpg")
            .pubReplacementValue(settingsForPublisherFocusOnTheFamily.getReplacementValue())
            .build();

    public static final TestProduct theAddisonWesleyProfessionalTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/9780132874748.xml")
            .contentFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780132874748.epub")
            .collateralFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780132874748_marketingimage.jpg")
            .imprintReplacementValue("9XS97RS")
            .build();

    public static final TestProduct adobePressTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/9780133432558.xml")
            .collateralFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780133432558_marketingimage.jpg")
            .imprintReplacementValue("626N35R")
            .build();

    public static final TestProduct pearsonTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/9780135094037.xml")
            .contentFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780135094037.epub")
            .collateralFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780135094037_marketingimage.jpg")
            .imprintReplacementValue("626N35R")
            .build();

    public static final TestProduct prenticeHallTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/9780137044665.xml")
            .contentFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780137044665.epub")
            .collateralFilePath("regression/verifyDistributionByAdvancedKeywordUsingImprintName/assets/9780137044665_marketingimage.jpg")
            .imprintReplacementValue("626N35R")
            .build();

    public static final TestProduct epubTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/9781424549582.xml")
            .collateralFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/assets/9781424549582_marketingimage.jpg")
            .rootDistributionFolder("epub/")
            .build();

    public static final TestProduct printTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/9780830842869.xml")
            .collateralFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/assets/9780830842869_marketingimage.jpg")
            .rootDistributionFolder("print/")
            .build();

    public static final TestProduct pdfTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/9780273723950.xml")
            .collateralFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/assets/9780273723950_marketingimage.jpg")
            .rootDistributionFolder("pdf/")
            .build();
    public static final TestProduct mobiTestProduct = TestProduct.builder()
            .onixFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/9781635861314.xml")
            .collateralFilePath("regression/verifyMultipleProductTypesAndAssetsDistributionByPaths/assets/9781635861314_marketingimage.jpg")
            .rootDistributionFolder("mobi/")
            .build();

    private TestProducts() {
        // None
    }
}
