package com.ccc.hv.qa.file.services;

import com.ccc.hv.qa.file.pojo.CsvFeedFile;
import com.ccc.hv.qa.file.pojo.OnixProductFromCsv;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.utils.enums.DatePattern;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ccc.hv.qa.utils.DateTimeUtils.getLocalDateBy;
import static com.ccc.hv.qa.utils.FileOpsUtils.*;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;
import static com.ccc.hv.qa.utils.StringUtils.updateTextRevisionNumber;


public class CsvFeedFileService {
    private static final int FIRST_ROW_INDEX = 1;
    private static final int TITLE_COLUMN_INDEX = 1;
    private static final int ON_SALE_DATE_COLUMN_INDEX = 2;
    private static final int ISBT_13_COLUMN_INDEX = 26;
    private static final int PUBLISHER_COLUMN_INDEX = 33;
    private static final int IMPRINT_COLUMN_INDEX = 34;
    private static final int PUBLISHING_STATUS_CODE_COLUMN_INDEX = 38;
    private static final int NOTIFICATION_TYPE_COLUMN_INDEX = 44;
    private static final int RECORD_SOURCE_NAME_COLUMN_INDEX = 45;
    private static final int RECORD_REFERENCE_COLUMN_INDEX = 49;

    private final Path absFilePath;
    private final List<String[]> csvBody;

    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private CsvFeedFileService(Path absFilePath, @NotNull List<String[]> csvBody) {
        this.absFilePath = absFilePath;
        this.csvBody = csvBody;
    }

    public static CsvFeedFileService readCsvFile(@NotNull Path filePath) {
        log.debug("Reading CSV file: " + filePath.toString() + "'.");
        Path absFilePath = getAbsoluteResourceFilePath(filePath);
        List<String[]> csvBody = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(absFilePath.toString()))) {
            csvBody = reader.readAll();
        } catch (IOException | CsvException e) {
            log.warn(e.getMessage());
        }

        return new CsvFeedFileService(absFilePath, csvBody);
    }

    public CsvFeedFileService updateProductTitles() {
        log.debug("Update all product titles in csv feed file.");
        for (int i = FIRST_ROW_INDEX; i < csvBody.size(); i++) {
            csvBody.get(i)[TITLE_COLUMN_INDEX] = updateTextRevisionNumber(csvBody.get(i)[TITLE_COLUMN_INDEX]);
        }

        return this;
    }

    public CsvFeedFileService updateProductsRecordReferences() {
        log.debug("Update all product record references in csv feed file.");
        for (int i = FIRST_ROW_INDEX; i < csvBody.size(); i++) {
            String oldRecRef = csvBody.get(i)[RECORD_REFERENCE_COLUMN_INDEX];
            String newRecRef = generateUniqueStringBasedOnDate(oldRecRef + getRevisionDelimiter());
            log.debug("Update product's record reference with: '" + newRecRef + "'.");
            csvBody.get(i)[RECORD_REFERENCE_COLUMN_INDEX] = newRecRef;
        }

        return this;
    }

    public List<OnixProductFromCsv> getProducts() {
        log.debug("Export products to external list of onix products.");

        List<OnixProductFromCsv> result = new ArrayList<>();
        for (int i = FIRST_ROW_INDEX; i < csvBody.size(); i++) {
            final OnixProductFromCsv product = OnixProductFromCsv.builder()
                    .title(csvBody.get(i)[TITLE_COLUMN_INDEX])
                    .isbn13(csvBody.get(i)[ISBT_13_COLUMN_INDEX])
                    .recordReference(csvBody.get(i)[RECORD_REFERENCE_COLUMN_INDEX])
                    .recordSourceName(csvBody.get(i)[RECORD_SOURCE_NAME_COLUMN_INDEX])
                    .publisher(csvBody.get(i)[PUBLISHER_COLUMN_INDEX])
                    .onSaleDate(convertOnixDateToLocalDate(csvBody.get(i)[ON_SALE_DATE_COLUMN_INDEX]))
                    .imprint(csvBody.get(i)[IMPRINT_COLUMN_INDEX])
                    .publishingStatusCode(csvBody.get(i)[PUBLISHING_STATUS_CODE_COLUMN_INDEX])
                    .notificationType(csvBody.get(i)[NOTIFICATION_TYPE_COLUMN_INDEX])
                    .build();

            result.add(product);
        }

        log.debug("'" + result.size() + "' product(s) was(were) gotten from csv feed file.");
        return result;
    }

    public CsvFeedFile saveAsNewFile() {
        Path outputFilePath = addUniqueSuffixTo(this.absFilePath);

        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath.toString()))) {
            writer.writeAll(csvBody);
            writer.flush();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        return CsvFeedFile.builder()
                .products(getProducts())
                .path(outputFilePath)
                .build();
    }

    private LocalDate convertOnixDateToLocalDate(@Nullable String onixDate) {
        log.debug("Convert '" + onixDate + "' onix date to LocalDateTime.");
        if (onixDate == null) {
            return null;
        }

        //Get date part from Onix date.
        if (onixDate.contains("T")) {
            onixDate = onixDate.substring(0, onixDate.indexOf("T"));
        }

        return getLocalDateBy(DatePattern.ONIX.toString(), onixDate);
    }
}
