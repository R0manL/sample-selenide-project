package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;


@Getter
@Builder
public class Product {
    private final ProductInfo productInfo;
    @Singular private final List<Asset> assets;
}
