package com.ccc.hv.qa.ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ccc.hv.qa.logging.AllureLogger;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$$x;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

public class ProductAppleMetadataPage {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String DOWNLOAD_LINK = ENV_CONFIG.hrvReaperHostUrl() +  "/reaper/rest/private/product/%s/assetStatusDetail/%s/download";


    private ProductAppleMetadataPage() {
        // NONE
    }

    public static ProductAppleMetadataPage getProductAppleMetadataPage() {
        return new ProductAppleMetadataPage();
    }

    public Path downloadAppleMetadataFile() {
        log.info("Download apple metadata file.");

        SelenideElement downloadLinksElm = $$x(".//li[.//@class='icon-download']//a")
                .shouldHave(CollectionCondition.size(1))
                .first();

        String dataAssetId = downloadLinksElm.getAttribute("data-asset-id");
        String dataProductId = downloadLinksElm.getAttribute("data-product-id");
        String link = String.format(DOWNLOAD_LINK, dataProductId, dataAssetId);

        try {
            File htmlWithCropdusterLink = Selenide.download(link);
            String url = new String(Files.readAllBytes(htmlWithCropdusterLink.toPath()));
            File appleMetadataFile = Selenide.download(url);

            return Paths.get(appleMetadataFile.getAbsolutePath());

        } catch (IOException | URISyntaxException e) {
           throw new IllegalStateException("Can't get file by link: " + link + "'.\nError:" + e.toString());
        }
    }
}
