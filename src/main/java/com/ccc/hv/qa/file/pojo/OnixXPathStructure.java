package com.ccc.hv.qa.file.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 10/19/20.
 */

@Getter
@Builder
public class OnixXPathStructure {
    @NonNull
    private final String messageFormat;
    @NonNull
    private final String messageCreationDate;
    @NonNull
    private final String product;
    @NonNull
    private final String productRecordReference;
    @NonNull
    private final String productRecordSourceName;
    private final String productTitle;
    private final String productTitlePrefix;
    private final String productTitleWithoutPrefix;
    private final String productIsbn10;
    @NonNull
    private final String productIsbn13;
    @NonNull
    private final String productForm;
    private final String productFormDetail;
    private final String productFormDescription;
    private final String productEpubType;
    private final String productEpubTypeDescription;
    private final String productEditionTypeCode;
    private final String productEditionType;
    private final String productAuthor;
    private final String productPublisher;
    private final String productOnSaleDate;
    private final String productSubject;
    private final String productImprint;
    private final String productPublishingStatusCode;
    private final String productPublicationDate;
    private final String productIdTypeName;
    private final String productIdTypeValue;
    private final String productNotificationType;
    private final String productFromCompany;
    private final String productSenderName;
}