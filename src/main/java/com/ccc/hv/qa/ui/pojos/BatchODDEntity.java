package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;

@Getter
@Builder
public class BatchODDEntity {
    private final boolean selectEntireCatalog;
    @NotNull
    private final String channelName;
    @Singular
    private final Set<BatchODDProductTypesEntity> batchODDProductTypeEntities;
    private final boolean isApplyRule;
    private final boolean isSendOnHoldProducts;
    private final boolean isSendLockedProducts;
    private final Path pathToXlsxFile;
}
