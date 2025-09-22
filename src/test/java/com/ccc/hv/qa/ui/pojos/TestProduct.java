package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

@Builder
@Value
public class TestProduct {
    @NotNull String onixFilePath;
    String contentFilePath;
    String collateralFilePath;
    String pubReplacementValue;
    String imprintReplacementValue;
    String newRecordReference;
    String rootDistributionFolder;

}
