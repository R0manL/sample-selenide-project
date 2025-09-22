package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.file.pojo.CsvFeedFile;
import com.ccc.hv.qa.file.pojo.OnixProductFromCsv;
import com.ccc.hv.qa.file.services.CsvFeedFileService;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.file.pojo.ServerCredentials;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.ccc.hv.qa.db.services.ProductDBService.waitOnProductInDbBy;
import static com.ccc.hv.qa.ui.services.FileTestService.getFileTestService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


public class CsvFeedTestService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Path csvFeedFilePath;
    private List<OnixProductFromCsv> products;


    public CsvFeedTestService(Path csvFeedFilePath) {
        this.csvFeedFilePath = csvFeedFilePath;
        this.products = CsvFeedFileService.readCsvFile(csvFeedFilePath).getProducts();
    }

    public CsvFeedTestService(@NotNull String csvFeedFilePath) {
        this(Paths.get(csvFeedFilePath));
    }


    public CsvFeedUpdate readCsvFile() {
        return new CsvFeedUpdate(this.csvFeedFilePath);
    }

    public CsvFeedTestService uploadToCrushFTPNormalizeDir() {
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(this.csvFeedFilePath);

        return this;
    }

    public CsvFeedTestService uploadToCrushFTPNormalizeDir(ServerCredentials creds) {
        getFileTestService().uploadFileToCrushFTPNormalizationFolder(this.csvFeedFilePath, creds);

        return this;
    }

    public CsvFeedTestService waitOnProductsInDB() {
        products.forEach(p -> waitOnProductInDbBy(p.getTitle()));

        return this;
    }

    public List<OnixProductFromCsv> getProducts() {
        return this.products;
    }

    public Path getCsvFeedFilePath() {
        return this.csvFeedFilePath;
    }

    @NotNull
    public String getCsvFeedFileName() {
        return this.csvFeedFilePath.getFileName().toString();
    }

    public Path getParentDir() {
        return this.csvFeedFilePath.getParent();
    }

    public class CsvFeedUpdate {
        private CsvFeedFileService csvFeedFileService;


        public CsvFeedUpdate(Path csvFeedFilePath) {
            this.csvFeedFileService = CsvFeedFileService.readCsvFile(csvFeedFilePath);
        }

        public CsvFeedUpdate updateTitles() {
            this.csvFeedFileService.updateProductTitles();

            return this;
        }

        public CsvFeedUpdate updateRecordReferences() {
            this.csvFeedFileService.updateProductsRecordReferences();

            return this;
        }

        public CsvFeedTestService saveAsNewFile() {
            CsvFeedFile updatedCsvFeed = this.csvFeedFileService.saveAsNewFile();
            CsvFeedTestService.this.products = updatedCsvFeed.getProducts();
            CsvFeedTestService.this.csvFeedFilePath = updatedCsvFeed.getPath();

            return CsvFeedTestService.this;
        }
    }
}