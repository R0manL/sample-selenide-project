package com.ccc.hv.qa.file.pojo;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by R0manL on 10/19/20.
 */

@Getter
@Builder
@ToString
public class OnixProductFromCsv {
    @NonNull
    private final String recordReference;
    @NonNull
    private final String isbn13;
    @NonNull
    private final String title;
    private final String imprint;
    private final String publishingStatusCode;
    private final String publisher;
    private final String recordSourceName;
    private final LocalDate onSaleDate;
    private final String notificationType;

    @NotNull
    public String getPublishingStatusCode() {
        return checkIfNotNullAndReturn(this.publishingStatusCode);
    }

    @NotNull
    public String getPublisher() {
        return checkIfNotNullAndReturn(this.publisher);
    }

    @NotNull
    public String getRecordSourceName() {
        return checkIfNotNullAndReturn(this.recordSourceName);
    }

    @NotNull
    public LocalDate getOnSaleDate() {
        return checkIfNotNullAndReturn(this.onSaleDate);
    }

    private <T> T checkIfNotNullAndReturn(T value) {
        Objects.requireNonNull(value, "Value has missed in CSV feed file.");
        return value;
    }
}