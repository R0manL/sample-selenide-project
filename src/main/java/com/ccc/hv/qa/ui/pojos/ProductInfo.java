package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;


@Getter
@Builder
public class ProductInfo {
    private final String productTitle;
    private final String authorName;
    private final String isbn;
    private final String publisherName;
    private final ProductType productType;
    private final String onSaleDate;
    private final String recordReference;
    private final String productSubject;
    @Singular private List<String> productImprints;
}
