package com.ccc.hv.qa.file.pojo;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Created by R0manL on 10/19/20.
 */

@Getter
@Builder
@ToString
public class OnixProduct {
    @NonNull
    private final String recordReference;
    @NonNull
    private final String isbn13;
    private final String title;
    private final String titlePrefix;
    private final String titleWithoutPrefix;
    @NonNull
    private final String type;
    @NonNull
    private final String subject;
    @Singular
    private final List<String> imprints;
    private final List<String> idTypeName;
    private final List<String> idTypeValue;
    private final String publishingStatusCode;
    private final String publisher;
    private final String author;
    private final String isbn10;
    private final String recordSourceName;
    private final LocalDate onSaleDate;
    private final LocalDate publicationDate;
    private final String notificationType;
    private final String fromCompany;
    private final String senderName;

    @NotNull
    public String getPublishingStatusCode() {
        return checkIfNotNullAndReturn(this.publishingStatusCode);
    }

    @NotNull
    public String getPublisher() {
        return checkIfNotNullAndReturn(this.publisher);
    }

    @NotNull
    public String getAuthor() {
        return checkIfNotNullAndReturn(this.author);
    }

    @NotNull
    public String getIsbn10() {
        return checkIfNotNullAndReturn(this.isbn10);
    }

    @NotNull
    public String getFromCompany() {
        return checkIfNotNullAndReturn(this.fromCompany);
    }

    @NotNull
    public String getSenderName() {
        return checkIfNotNullAndReturn(this.senderName);
    }

    @NotNull
    public String getRecordSourceName() {
        return checkIfNotNullAndReturn(this.recordSourceName);
    }

    @NotNull
    public LocalDate getOnSaleDate() {
        return checkIfNotNullAndReturn(this.onSaleDate);
    }

    @NotNull
    public LocalDate getPublicationDate() {
        return checkIfNotNullAndReturn(getPublicationDateNullable());
    }

    @Nullable
    public LocalDate getPublicationDateNullable() {
        return this.publicationDate;
    }

    @NotNull
    private <T> T checkIfNotNullAndReturn(T value) {
        Objects.requireNonNull(value, "Value has missed in Onix file.");
        return value;
    }
}