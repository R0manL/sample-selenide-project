package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.file.pojo.OnixFile;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.file.services.OnixFileService;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.file.pojo.ServerCredentials;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.ccc.hv.qa.db.services.ProductDBService.waitOnProductInDbBy;
import static com.ccc.hv.qa.file.services.OnixFileService.ONIX_FILE_EXTENSION;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static com.ccc.hv.qa.utils.FileOpsUtils.cloneFile;

/**
 * Created by R0manL on 4/29/21.
 */

public class OnixTestService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Path onixFilePath;
    private OnixFileService onix;
    private List<OnixProduct> products;


    public OnixTestService(Path onixFilePath) {
        this.onixFilePath = onixFilePath;
        this.onix = OnixFileService.readOnixFile(onixFilePath);
        this.products = onix.getProducts();
    }

    public OnixTestService(@NotNull String onixFilePath) {
        this(Paths.get(onixFilePath));
    }


    public OnixUpdate readOnixFile() {
        return new OnixUpdate(this.onixFilePath);
    }

    public OnixTestService uploadToCrushFTP(ServerCredentials creds) {
        getFileTestService().uploadFileToCrushFTPRootFolder(this.onixFilePath, creds);
        return this;
    }

    public OnixTestService waitOnProductsInDB() {
        products.forEach(p -> {
            String title = p.getTitle();

            if(title == null) {
                log.debug("Product's title has missed, try to build title based on 'prefix' and 'titleWithoutPrefix' values.");
                String prefix = p.getTitlePrefix() != null ? p.getTitlePrefix() + " " : "";
                String titleWithoutPrefix = p.getTitleWithoutPrefix() != null ? p.getTitleWithoutPrefix() : "";
                title = prefix + titleWithoutPrefix;
            }

            waitOnProductInDbBy(title);
        });

        return this;
    }

    public List<OnixProduct> getProducts() {
        return this.products;
    }

    public OnixProduct getSingleProduct() {
        if (this.products.size() == 1) {
            return this.products.get(0);
        } else {
            throw new IllegalStateException("Expect single product, but found '" + this.products.size() + "'.");
        }
    }

    @NotNull
    public String getSingleProductTitle() {
        return getSingleProduct().getTitle();
    }

    @NotNull
    public String getSingleProductTitlePrefix() {
        return getSingleProduct().getTitlePrefix();
    }

    @NotNull
    public String getSingleProductTitleWithPrefix() {
        OnixProduct p = getSingleProduct();
        String prefix = p.getTitlePrefix() != null ? p.getTitlePrefix() + " " : "";

        return prefix + p.getTitleWithoutPrefix();
    }

    public String getSingleProductRecordReference() {
        return getSingleProduct().getRecordReference();
    }

    public String getNotificationType() {
        return getSingleProduct().getNotificationType();
    }

    public List<String> getNotificationTypes() {
        return this.products.stream()
                .map(OnixProduct::getNotificationType)
                .collect(Collectors.toList());
    }

    public List<String> getProductsPublishers() {
        return this.products.stream()
                .map(OnixProduct::getPublisher)
                .collect(Collectors.toList());
    }

    public List<String> getProductsImprints() {
        return this.products.stream()
                .flatMap(product -> product.getImprints().stream())
                .collect(Collectors.toList());
    }

    public List<String> getProductsIDTypeNames() {
        return this.products.stream()
                .flatMap(product -> product.getIdTypeName().stream())
                .collect(Collectors.toList());
    }

    public List<String> getProductsIDTypeValues() {
        return this.products.stream()
                .flatMap(product -> product.getIdTypeValue().stream())
                .collect(Collectors.toList());
    }

    public List<String> getProductsRecordReferences(){
        return this.products.stream()
                .map(OnixProduct::getRecordReference)
                .collect(Collectors.toList());
    }

    public Path getFilePath() {
        return this.onixFilePath;
    }

    public Path getParentDir() {
        return this.onixFilePath.getParent();
    }

    public String getFileNameWithRecRef() {
        return getSingleProductRecordReference() + ONIX_FILE_EXTENSION;
    }

    public List<String> getFileNamesWithRecRef() {
        return getProductsRecordReferences()
                .stream()
                .map(recref -> recref + ONIX_FILE_EXTENSION)
                .collect(Collectors.toList());
    }

    public List<MetadataTestService> toAssets() {
        return getFileNamesWithRecRef().stream()
                .map(this::cloneMetadataAssetWith)
                .collect(Collectors.toList());
    }

    public MetadataTestService toAsset() {
        return cloneMetadataAssetWith(getFileNameWithRecRef());
    }

    public boolean isLongFormat() {
        return  this.onix.identifyOnixFormat().getProduct().contains("Product");
    }

    public boolean isOnix30() {
        return this.onix.identifyOnixFormat().getMessageFormat().contains("3.0");
    }

    private MetadataTestService cloneMetadataAssetWith(@NotNull String fileNameWithRecRef) {
        log.debug("Create '" + fileNameWithRecRef + "' metadata asset from onix's product.");
        return new MetadataTestService(cloneFile(fileNameWithRecRef, getFilePath().toString()).toString());
    }

    public class OnixUpdate {
        private OnixFileService onixFileService;

        public OnixUpdate(Path onixFilePath) {
            this.onixFileService = OnixFileService.readOnixFile(onixFilePath);
        }

        public OnixUpdate updateTitles() {
            this.onixFileService.updateProductsTitle();

            return this;
        }

        public OnixUpdate updateTitlesWithoutPrefix() {
            this.onixFileService.updateProductsTitleWithoutPrefix();

            return this;
        }

        public OnixUpdate setRecordSourceName(@NotNull String value, int productNumInFeed) {
            this.onixFileService.setProductRecordSourceName(value, productNumInFeed);

            return this;
        }

        public OnixUpdate updateRecordReferences() {
            this.onixFileService.updateProductsRecordReferences();

            return this;
        }

        public OnixUpdate updateRecordReferencesWithSuffix(@NotNull String suffix) {
            this.onixFileService.updateProductsRecordReferences(suffix);

            return this;
        }

        public OnixUpdate updateMessageCreationDateAsToday() {
            this.onixFileService.updateMessageCreationDateAsToday();

            return this;
        }

        public OnixUpdate updatePublicationDates(LocalDate withDate) {
            this.onixFileService.updatePublicationDateWith(withDate);

            return this;
        }

        public OnixUpdate updateIsbn10() {
            this.onixFileService.updateIsbn10();

            return this;
        }

        public OnixUpdate updateIsbn13() {
            this.onixFileService.updateIsbn13();

            return this;
        }

        public OnixTestService saveAsNewFile() {
            OnixFile updatedOnix = this.onixFileService.saveAsNewFile();

            OnixTestService.this.products = updatedOnix.getProducts();
            OnixTestService.this.onixFilePath = updatedOnix.getPath();

            return OnixTestService.this;
        }
    }
}